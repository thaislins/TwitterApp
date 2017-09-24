package com.example.thaislins.twitter.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.thaislins.twitter.activities.DirectMessageActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import static android.app.PendingIntent.getActivity;

/**
 * Created by thaislins on 24/09/17.
 */

public class SocketClient extends AsyncTask<String, Void, String> {

    private Context context;

    public SocketClient(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        Socket socket = null;
        StringBuilder data = new StringBuilder();

        try {
            socket = new Socket(strings[0], Integer.parseInt(strings[1]));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            pw.println(strings[2]);
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
        User recipient = new User("@recipient", "User", "user@email.com", "+1 555 555 5555");
        DirectMessage directMessage = new DirectMessage(new Date(), recipient, ((DirectMessageActivity) context).getTextInput().getText().toString());

        ((DirectMessageActivity) context).getMessages().add(directMessage);
        ((DirectMessageActivity) context).getDirectMessageAdapter().notifyDataSetChanged();
        ((DirectMessageActivity) context).getTextInput().setText("");
    }
}