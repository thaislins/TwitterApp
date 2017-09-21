package com.example.thaislins.twitter.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thaislins.twitter.R;
import com.example.thaislins.twitter.Tweet;
import com.example.thaislins.twitter.TweetAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class TwitterMainActivity extends AppCompatActivity {

    private ArrayList<Tweet> listOfTweets;
    private TweetAdapter listAdapter;
    private ListView listTwitter;
    protected static final int PHOTO = 1;
    private static final String IMAGE ="image" ;
    private ImageView imgView;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.twitter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.reload) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Reloading...",
                    Toast.LENGTH_LONG);
            toast.show();
        }
        if(id == R.id.logout) {
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Edit");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getTitle() == "Remove") {
            int listPosition = info.position;
            listOfTweets.remove(listOfTweets.get(listPosition));//list item title
            listAdapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    private void run(Bundle savedInstanceState) {
        listTwitter = (ListView) findViewById(R.id.listTweet);
        listOfTweets = new ArrayList<Tweet>();
        listAdapter = new TweetAdapter(this, listOfTweets);
        listTwitter.setAdapter(listAdapter);
        registerForContextMenu(listTwitter);

        if(savedInstanceState != null){

            bitmap = savedInstanceState.getParcelable(IMAGE);

            if(bitmap != null){
                imgView = (ImageView) findViewById(R.id.twitterPicture);

                imgView.setImageBitmap(bitmap);
            }
        }

        listTwitter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                Tweet selectedTweet = listOfTweets.get(position);

                Intent intent = new Intent(TwitterMainActivity.this, TweetDetailsActivity.class);
                intent.putExtra(TweetDetailsActivity.TWEET_INFO, selectedTweet);
                startActivity(intent);
            }
        });
    }

    public void tweet(View v) {
        EditText txtTwitter = (EditText) findViewById(R.id.txtTweet);
        String tweet = txtTwitter.getText().toString();
        Tweet tweetInfo = new Tweet("@username", tweet);
        listOfTweets.add(tweetInfo);
        txtTwitter.setText("");

        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO:
                if (resultCode == RESULT_OK) {
                    modifyPhoto(data);
                }
                break;
            default:
                break;
        }
    }

    private void modifyPhoto(Intent data) {
        imgView = (ImageView) findViewById(R.id.twitterPicture);

        bitmap = (Bitmap) data.getExtras().get("data");
        imgView.setImageBitmap(bitmap);
    }

    public void takePhoto(View v) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, PHOTO);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(IMAGE,bitmap);
        super.onSaveInstanceState(outState);
    }
}
