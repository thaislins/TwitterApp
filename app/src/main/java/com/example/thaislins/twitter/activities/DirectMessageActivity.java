package com.example.thaislins.twitter.activities;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thaislins.twitter.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

/**
 * Created by thaislins on 23/09/17.
 */

public class DirectMessageActivity extends AppCompatActivity {

    private TextView textDevice;
    private TextView textServer;
    private EditText textInput;
    private TextView textOutput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_direct);
        run(savedInstanceState);
    }

    @SuppressWarnings("deprecation")
    public void run(Bundle savedInstanceState) {
        textDevice = (TextView) findViewById(R.id.txtDeviceIP);
        textServer = (TextView) findViewById(R.id.txtServerIP);
        textInput = (EditText) findViewById(R.id.txtInput);
        textOutput = (TextView) findViewById(R.id.txtOutput);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        textDevice.setText("Device IP: " + ipAddress);
    }

    public void send(View v) {
        SocketClient client = new SocketClient();
        client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, textServer.getText().toString(), "2222 ", textInput.getText().toString());
    }

    private class SocketClient extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Socket socket = null;
            StringBuilder data = new StringBuilder();

            try {
                socket = new Socket(strings[0], Integer.parseInt(strings[1]));
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                pw.println(strings[2]);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String rawData;
                while ((rawData = br.readLine()) != null) {
                    data.append(rawData);
                }

                br.close();
            } catch (Exception e) {
                Log.e("AsyncTask", "doInBackground: error on socket...", e);
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        Log.e("AsyncTask", "doInBackground: Error closing socket", e);
                    }
                }
            }
            return data.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textOutput.setText("");
        }
    }
}