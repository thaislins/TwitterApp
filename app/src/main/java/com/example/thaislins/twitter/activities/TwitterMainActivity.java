package com.example.thaislins.twitter.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
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
import com.example.thaislins.twitter.model.Tweet;
import com.example.thaislins.twitter.adapter.TweetAdapter;
import com.example.thaislins.twitter.model.User;

import java.util.ArrayList;

public class TwitterMainActivity extends AppCompatActivity {

    protected static final int EDIT = 1;
    protected static final int PHOTO = 2;
    private static final String IMAGE = "image";
    private static final String TWEETS = "tweets";

    private ArrayList<Tweet> listOfTweets;
    private TweetAdapter listAdapter;
    private ListView listTwitter;
    private ImageView imgView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.twitter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reload) {
            Toast.makeText(this, "Reloading...", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.logout) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;

        if (item.getTitle() == "Edit") {
            Tweet selectedTweet = listOfTweets.get(listPosition);
            Intent intent = new Intent(TwitterMainActivity.this, EditTweetActivity.class);
            intent.putExtra(EditTweetActivity.EDIT_TWEET, selectedTweet);
            intent.putExtra(EditTweetActivity.TWEET_ID, info.position);
            startActivityForResult(intent, EDIT);
        } else if (item.getTitle() == "Remove") {
            listOfTweets.remove(listOfTweets.get(listPosition));//list item title
            listAdapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    public void run(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            listOfTweets = (ArrayList<Tweet>) savedInstanceState.getSerializable(TWEETS);
            bitmap = savedInstanceState.getParcelable(IMAGE);

            if (bitmap != null) {
                imgView = (ImageView) findViewById(R.id.imgTwitter);

                imgView.setImageBitmap(bitmap);
            }
        } else {
            listOfTweets = new ArrayList<Tweet>();
        }

        listTwitter = (ListView) findViewById(R.id.listTweet);
        listAdapter = new TweetAdapter(this, listOfTweets);
        listTwitter.setAdapter(listAdapter);
        registerForContextMenu(listTwitter);

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

        User user = new User("@username", "User", "user@email.com", "+1 555 555 5555");
        Tweet tweetInfo = new Tweet(user, tweet);
        listOfTweets.add(tweetInfo);
        txtTwitter.setText("");

        listAdapter.notifyDataSetChanged();
    }

    public void sendDirect(View v) {
        startActivity(new Intent(this, DirectMessageActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EDIT:
                if (resultCode == RESULT_OK) {
                    modifyTweet(data);
                }
                break;
            case PHOTO:
                if (resultCode == RESULT_OK) {
                    modifyPhoto(data);
                }
                break;
            default:
                break;
        }
    }

    private void modifyTweet(Intent data) {
        Tweet tweet = (Tweet) data.getSerializableExtra(EditTweetActivity.EDIT_TWEET);
        int tweetId = data.getIntExtra(EditTweetActivity.TWEET_ID, 0);

        listOfTweets.set(tweetId, tweet);
        listAdapter.notifyDataSetChanged();
    }

    private void modifyPhoto(Intent data) {
        imgView = (ImageView) findViewById(R.id.imgTwitter);
        bitmap = (Bitmap) data.getExtras().get("data");
        imgView.setImageBitmap(bitmap);
    }

    public void takePhoto(View v) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, PHOTO);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(IMAGE, bitmap);
        outState.putSerializable(TWEETS, listOfTweets);
        super.onSaveInstanceState(outState);
    }
}
