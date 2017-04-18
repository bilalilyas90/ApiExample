package com.project.coresol.apiexample.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.coresol.apiexample.Models.YoutubeModel;
import com.project.coresol.apiexample.R;
import com.project.coresol.apiexample.activities.YoutubeActivity;

import java.util.ArrayList;

/**
 * Created by coresol on 18/04/17.
 */

public class CustomBaseAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<YoutubeModel> allRecord = new ArrayList<>();
    LayoutInflater inflater;
    public CustomBaseAdapter(Activity activity, ArrayList<YoutubeModel> allRecord) {
        this.activity = activity;
        this.allRecord = allRecord;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return allRecord.size();
    }

    @Override
    public YoutubeModel getItem(int position) {
        return allRecord.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null){
            v = inflater.inflate(R.layout.listitem_youtube,viewGroup,false);
        }

        TextView textView_title = (TextView)v.findViewById(R.id.textView_title);
        TextView textView_desc = (TextView)v.findViewById(R.id.textView_desc);
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);

        YoutubeModel youtubeModel = getItem(position);

        textView_title.setText(youtubeModel.getTitle());
        textView_desc.setText(youtubeModel.getDesc());
        Glide.with(activity).load(youtubeModel.getThumbUrl()).into(imageView);


        return v;
    }
}
