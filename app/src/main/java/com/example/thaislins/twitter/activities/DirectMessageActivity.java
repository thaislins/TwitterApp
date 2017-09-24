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
import com.example.thaislins.twitter.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
        SocketClient client = new SocketClient();
        client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, textServer.getText().toString(), "4444", textInput.getText().toString());
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
            User recipient = new User("@username", "User", "user@email.com", "+1 555 555 5555");
            DirectMessage message = new DirectMessage(new Date(), recipient, textInput.getText().toString());

            messages.add(message);
            directMessageAdapter.notifyDataSetChanged();
            textInput.setText("");
        }
    }
}