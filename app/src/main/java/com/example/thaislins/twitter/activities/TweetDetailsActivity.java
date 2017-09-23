package com.example.thaislins.twitter.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.thaislins.twitter.R;
import com.example.thaislins.twitter.Tweet;
import com.example.thaislins.twitter.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by thaislins on 20/09/17.
 */

public class TweetDetailsActivity extends AppCompatActivity {

    public static final String TWEET_INFO = "tweet_info";
    private Tweet tweet;
    private TextView textUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_details);
        run(savedInstanceState);
    }

    public void run(Bundle savedInstanceState) {
        Intent intent = getIntent();
        tweet = (Tweet) intent.getSerializableExtra(TWEET_INFO);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy - hh:mm", Locale.getDefault());

        textUsername = (TextView) findViewById(R.id.userDt);
        TextView textTweet = (TextView) findViewById(R.id.textDt);
        TextView textDate = (TextView) findViewById(R.id.dateDt);

        textUsername.setText(tweet.getUsername());
        textTweet.setText(tweet.getTweet());
        textDate.setText(df.format(tweet.getDate()));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void makeCall(View v) {
        User user = tweet.getUser();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + user.getPhone()));
        startActivity(intent);
    }
}
