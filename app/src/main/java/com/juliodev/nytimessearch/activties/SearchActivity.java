package com.juliodev.nytimessearch.activties;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.juliodev.nytimessearch.Article;
import com.juliodev.nytimessearch.ArticleArrayAdapter;
import com.juliodev.nytimessearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    EditText etQuery;
    GridView gvResults;
    Button bntSearch;
    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;

    // init mes variable de put extra

    String sortOrder;
    String myDate;
    String art;
    String fashion;
    String sport;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("New York Times Article");
        // set the top title
        String title = actionBar.getTitle().toString(); // get the title

        //Display a action bar icon

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //on fait apel a notre fonction setUpview;
        setUpViews();

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
    }

    //creer une fonctin s pour vue
    public void setUpViews(){
        gvResults = (GridView) findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position , long id) {
                Intent i = new Intent(getApplicationContext(),articleActivity.class);
                //recuperer the article
                Article article =  articles.get(position);
                // Launch the activity
                i.putExtra("article", article);
                //launch the activity
                startActivity(i);
            }
        });
    }

    // je vais creer ma method qui recuper mes valeurs de mes put Extra


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                // Do something with result
                sortOrder = data.getStringExtra("sortOrder");
                myDate = data.getStringExtra("beginDate");

                //gestion des check box

                art = data.getStringExtra("art");
                fashion = data.getStringExtra("fashion");
                sport =  data.getStringExtra("sport");

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
              //  Toast.makeText(getApplicationContext(),"Waiting for " + query.toString(),Toast.LENGTH_LONG).show();
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599

                AsyncHttpClient client = new AsyncHttpClient();
                String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
                RequestParams params = new RequestParams();
                params.put("api-key","af9fd8cdb6ee4fc6afed463590da1a88");
                params.put("page" ,0);
                params.put("q",query);

                if(sortOrder!= null){
                    params.put("sort", sortOrder);
                    params.put("", sortOrder);
                }

                if(myDate!= null){
                    params.put("begin_date", myDate);
                }

                if(art != null || fashion != null || sport != null)
                {
                    String luceneString = String.format("news_desk:(\"%s\" \"%s\" \"%s\")", art, fashion, sport);
                    //params.put("fq", "news_desk:(\"Arts\" \"Fashion & Style\" \"Sports\")" );
                    params.put("fq", luceneString );
                }

                client.get(url,params, new JsonHttpResponseHandler(){
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        JSONArray articleJsonResults = null;
                        try {
                            articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                            spinner.setVisibility(View.GONE);
                            adapter.clear();
                            adapter.addAll(Article.fromJSONArray(articleJsonResults));
                            //adapter.notifyDataSetChanged();

                        }catch (JSONException e){
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        //super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(getApplicationContext(), "je ne trouve pas de json internet ", Toast.LENGTH_LONG).show();

                    }
                });
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                AsyncHttpClient client = new AsyncHttpClient();
                String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
                RequestParams params = new RequestParams();
                params.put("api-key","af9fd8cdb6ee4fc6afed463590da1a88");
                params.put("page" ,0);
                params.put("q",newText);
                if(sortOrder!= null){
                    params.put("sort", sortOrder);
                    params.put("", sortOrder);
                }

                if(myDate!= null){
                    params.put("begin_date", myDate);
                }

                if(art != null || fashion != null || sport != null)
                {
                    String luceneString = String.format("news_desk:(\"%s\" \"%s\" \"%s\")", art, fashion, sport);
                    //params.put("fq", "news_desk:(\"Arts\" \"Fashion & Style\" \"Sports\")" );
                    params.put("fq", luceneString );
                }

                client.get(url,params, new JsonHttpResponseHandler(){
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        JSONArray articleJsonResults = null;
                        try {
                            articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                            spinner.setVisibility(View.GONE);
                            adapter.clear();
                            adapter.addAll(Article.fromJSONArray(articleJsonResults));
                            //adapter.notifyDataSetChanged();

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        //super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // je vais creer ma fonction

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onSetting();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSetting() {
        Intent i = new Intent(this,Setting.class);
        startActivityForResult (i,100);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
}
