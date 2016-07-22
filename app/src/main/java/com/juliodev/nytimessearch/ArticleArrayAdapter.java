package com.juliodev.nytimessearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Julio on 7/21/2016.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {
    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1,articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        Article article = this.getItem(position);

        if (convertView == null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
           convertView = inflater.inflate(R.layout.item_article_result,parent,false);
        }
        ///find the image view
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);
        // Clear the recycle image from convertView from last time
        imageView.setImageResource(0);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(article.getHeadLine());
        // populate the thumbNail images
        // remote download image in background
        String thumbNail = article.getThumNail();
        if(!TextUtils.isEmpty(thumbNail)){
            Picasso.with(getContext()).load(thumbNail).into(imageView);
        }
        return convertView;
    }
}
