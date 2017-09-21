package com.example.thaislins.twitter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.thaislins.twitter.R;
import com.example.thaislins.twitter.Tweet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by thaislins on 20/09/17.
 */

public class TweetDetailsActivity extends AppCompatActivity {

    public static final String TWEET_INFO = "TWEET_INFO";
    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_details);
        run();
    }

    private void run() {

        Intent intent = getIntent();
        tweet = intent.getParcelableExtra(TWEET_INFO);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy - hh:mm", Locale.getDefault());

        TextView textUsername = (TextView) findViewById(R.id.usernameDt);
        TextView textTweet = (TextView) findViewById(R.id.textDt);
        TextView textDate = (TextView) findViewById(R.id.dateDt);

        textUsername.setText(tweet.getUsername());
        textTweet.setText(tweet.getTweet());
        textDate.setText(df.format(tweet.getDate()));
    }

    public void back(View v) {
        finish();
    }
}
