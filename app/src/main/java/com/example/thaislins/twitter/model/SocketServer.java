
package com.example.thaislins.twitter.model;

import android.content.Context;
import android.os.AsyncTask;

import com.example.thaislins.twitter.activities.DirectMessageActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


/**
 * Created by thaislins on 24/09/17.
 */

public class SocketServer extends AsyncTask<Void, String, Void> {

    private Context context;
    private final String tag = SocketServer.class.getName();

    private ServerSocket serverSocket;

    public SocketServer(Context context, ServerSocket serverSocket) {
        this.context = context;
        this.serverSocket = serverSocket;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        boolean listening = true;
        String username;
        String message;

        try {
            while (listening) {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                username = in.readLine();
                while ((message = in.readLine()) != null) {

                    if ("stop_server".equals(message)) {
                        listening = false;
                        break;
                    }

                    publishProgress(username, message);
                }

                in.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... data) {
        super.onProgressUpdate(data);
        User receiver = new User(data[0], "User", "user@email.com", "+1 555 555 5555");
        DirectMessage directMessage = new DirectMessage(new Date(), receiver, data[1]);
        ((DirectMessageActivity) context).getMessages().add(directMessage);
        ((DirectMessageActivity) context).getDirectMessageAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
