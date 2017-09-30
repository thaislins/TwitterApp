package com.example.thaislins.twitter.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thaislins.twitter.R;
import com.example.thaislins.twitter.model.DirectMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by thaislins on 24/09/17.
 */

public class DirectMessageAdapter extends BaseAdapter {

    private final Context context;
    ArrayList<DirectMessage> directMessages = new ArrayList<DirectMessage>();

    public DirectMessageAdapter(Context context, ArrayList<DirectMessage> directMessages) {
        this.context = context;
        this.directMessages = directMessages;
    }

    @Override
    public int getCount() {
        return directMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return directMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DirectMessage directMessage = directMessages.get(position);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy - hh:mm", Locale.getDefault());

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.tweet_layout, null);

        TextView txtTweet = (TextView) view.findViewById(R.id.text);
        TextView txtUsername = (TextView) view.findViewById(R.id.user);
        TextView txtDate = (TextView) view.findViewById(R.id.date);

        txtTweet.setText(directMessage.getMessage());
        txtUsername.setText(directMessage.getUser().getUsername());
        txtDate.setText(df.format(directMessage.getDate()));

        return view;
    }
}
