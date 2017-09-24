package com.example.thaislins.twitter.activities;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thaislins.twitter.R;
import com.example.thaislins.twitter.adapter.DirectMessageAdapter;
import com.example.thaislins.twitter.model.DirectMessage;
import com.example.thaislins.twitter.model.SocketClient;
import com.example.thaislins.twitter.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by thaislins on 23/09/17.
 */

public class DirectMessageActivity extends AppCompatActivity {

    DirectMessageAdapter directMessageAdapter;
    private ArrayList<DirectMessage> messages;

    private TextView textDevice;
    private TextView textServer;
    private EditText textInput;
    private ListView textOutput;

    public DirectMessageAdapter getDirectMessageAdapter() {
        return directMessageAdapter;
    }

    public void setDirectMessageAdapter(DirectMessageAdapter directMessageAdapter) {
        this.directMessageAdapter = directMessageAdapter;
    }

    public ArrayList<DirectMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<DirectMessage> messages) {
        this.messages = messages;
    }

    public TextView getTextDevice() {
        return textDevice;
    }

    public void setTextDevice(TextView textDevice) {
        this.textDevice = textDevice;
    }

    public TextView getTextServer() {
        return textServer;
    }

    public void setTextServer(TextView textServer) {
        this.textServer = textServer;
    }

    public EditText getTextInput() {
        return textInput;
    }

    public void setTextInput(EditText textInput) {
        this.textInput = textInput;
    }

    public ListView getTextOutput() {
        return textOutput;
    }

    public void setTextOutput(ListView textOutput) {
        this.textOutput = textOutput;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_dm);
        run(savedInstanceState);
    }

    @SuppressWarnings("deprecation")
    public void run(Bundle savedInstanceState) {
        textDevice = (TextView) findViewById(R.id.txtDeviceIP);
        textServer = (TextView) findViewById(R.id.txtServerIP);
        textInput = (EditText) findViewById(R.id.txtInput);
        textOutput = (ListView) findViewById(R.id.txtOutput);

        messages = new ArrayList<DirectMessage>();

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        textDevice.setText("Device IP: " + ipAddress);

        directMessageAdapter = new DirectMessageAdapter(this, messages);
        textOutput.setAdapter(directMessageAdapter);
    }

    public void send(View v) {
        SocketClient client = new SocketClient(this);
        client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, textServer.getText().toString(), "4444", textInput.getText().toString());
    }

/*
    }*/
}