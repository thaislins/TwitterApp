package com.example.thaislins.twitter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by thaislins on 20/09/17.
 */

public class TweetAdapter extends BaseAdapter {

    private final Context context;
    ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    public TweetAdapter(Context context, ArrayList<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @Override
    public int getCount() {
        return tweets.size();
    }

    @Override
    public Object getItem(int position) {
        return tweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = tweets.get(position);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy - hh:mm", Locale.getDefault());

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.tweet_layout, null);

        TextView txtTweet = (TextView) view.findViewById(R.id.text);
        TextView txtUsername = (TextView) view.findViewById(R.id.user);
        TextView txtDate = (TextView) view.findViewById(R.id.date);

        txtTweet.setText(tweet.getTweet());
        txtUsername.setText(tweet.getUsername());
        txtDate.setText(df.format(tweet.getDate()));

        return view;
    }
}
