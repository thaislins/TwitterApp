package com.example.thaislins.twitter.activities;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thaislins.twitter.R;
import com.example.thaislins.twitter.adapter.DirectMessageAdapter;
import com.example.thaislins.twitter.model.DirectMessage;
import com.example.thaislins.twitter.model.SocketClient;
import com.example.thaislins.twitter.model.SocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by thaislins on 23/09/17.
 */

public class DirectMessageActivity extends AppCompatActivity {

    public static final String MESSAGES = "messages";

    DirectMessageAdapter directMessageAdapter;

    private ArrayList<DirectMessage> messages;

    private SocketServer socketServer;

    private ServerSocket serverSocket;
    private TextView textDevice;

    private TextView textServer;
    private EditText textName;
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

    public EditText getTextName() {
        return textName;
    }

    public void setTextName(EditText textName) {
        this.textName = textName;
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

    @Override
    protected void onResume() {
        super.onResume();
        if (socketServer.getStatus() != AsyncTask.Status.RUNNING) {
            socketServer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Toast.makeText(DirectMessageActivity.this, "Server Stopped", Toast.LENGTH_SHORT).show();
            serverSocket.close();
            Log.d(DirectMessageActivity.class.getName(), "onDestroy: Server Socket closed");
        } catch (IOException e) {
            Log.e(DirectMessageActivity.class.getName(), "onDestroy: Error when closing server socket", e);
        }
    }

    @SuppressWarnings("deprecation")
    public void run(Bundle savedInstanceState) {
        textDevice = (TextView) findViewById(R.id.txtDeviceIP);
        textServer = (TextView) findViewById(R.id.txtServerIP);
        textName = (EditText) findViewById(R.id.txtName);
        textInput = (EditText) findViewById(R.id.txtInput);
        textOutput = (ListView) findViewById(R.id.txtOutput);
        messages = new ArrayList<>();

        createSocketServer();

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        textDevice.setText("Device IP: " + ipAddress);

        directMessageAdapter = new DirectMessageAdapter(this, messages);
        textOutput.setAdapter(directMessageAdapter);

        //adds back arrow to layout
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void createSocketServer() {
        if (serverSocket == null) {
            try {
                serverSocket = new ServerSocket();
                serverSocket.setReuseAddress(true);
                serverSocket.bind(new InetSocketAddress(Integer.parseInt("4444")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            socketServer = new SocketServer(this, serverSocket);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void send(View v) {
        String ipServer = textServer.getText().toString();
        String username = textName.getText().toString();
        String message = textInput.getText().toString();

        SocketClient client = new SocketClient(this);
        client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ipServer, "4444", username, message);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(MESSAGES, messages);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}