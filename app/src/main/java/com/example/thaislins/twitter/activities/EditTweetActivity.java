package com.example.thaislins.twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thaislins.twitter.R;
import com.example.thaislins.twitter.Tweet;

import java.util.Date;

/**
 * Created by thaislins on 22/09/17.
 */

public class EditTweetActivity extends AppCompatActivity {

    public static final String EDIT_TWEET = "edit_tweet";
    public static final String TWEET_ID = "tweet_id";
    private Tweet tweet;
    private int tweetId;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tweet);
        run(savedInstanceState);
    }

    public void run(Bundle savedInstanceState) {
        Intent intent = getIntent();
        tweet = (Tweet) intent.getSerializableExtra(EDIT_TWEET);
        tweetId = intent.getIntExtra(TWEET_ID, 0);

        TextView textUsername = (TextView) findViewById(R.id.txtUsername);
        editText = (EditText) findViewById(R.id.editTweet);

        textUsername.setText(tweet.getUsername());
        editText.setText(tweet.getTweet());

        //adds back arrow to layout
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveTweet(View v) {
        tweet.setTweet(editText.getText().toString());
        tweet.setDate(new Date());

        Intent intent = new Intent();
        intent.putExtra(EDIT_TWEET, tweet);
        intent.putExtra(TWEET_ID, tweetId);

        setResult(RESULT_OK, intent);
        finish();
    }
}
