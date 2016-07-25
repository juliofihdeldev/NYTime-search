package com.juliodev.nytimessearch.activties;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.juliodev.nytimessearch.Article;
import com.juliodev.nytimessearch.R;

public class articleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        //je vais gerer mo tolbar
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Article details ");
        // set the top title
        String title = actionBar.getTitle().toString(); // get the title
        //Display a action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_back_classic);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // gestion de ma web view
        Article article = (Article) getIntent().getSerializableExtra("article");
        WebView webView = (WebView) findViewById(R.id.wArticle);
        //load my web in the web view
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(article.getWebUrl());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        startActivity(i);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

}
