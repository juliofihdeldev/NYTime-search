package com.juliodev.nytimessearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Julio on 7/21/2016.
 */
public class Article implements Serializable {
    String webUrl;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadLine() {
        return headLine;
    }


    public String getThumNail() {
        return thumNail;
    }

    public String getCategorie() {
        return categorie;
    }

    String categorie;
    String headLine;
    String thumNail;

    public  Article (JSONObject jsonObject){
        try {
            this.webUrl=jsonObject.getString("web_url");
            this.categorie=jsonObject.getString("news_desk");
            this.headLine= jsonObject.getJSONObject("headline").getString("main");
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if(multimedia.length()>0){
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumNail = "http://www.nytimes.com/"+ multimediaJson.getString("url");
            }else{
                this.thumNail="";
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<Article>  fromJSONArray (JSONArray array){
        ArrayList<Article> results = new ArrayList<>();

        for (int x=0; x < array.length(); x++) {
            try{
                results.add(new Article(array.getJSONObject(x)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
