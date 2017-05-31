package com.example.angelachang.rubibot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
//    private Socket socket;

    private static final int SERVER_PORT = 8080;
    private static final String SERVER_IP = "192.168.2.24";

    private PrintWriter out;
    private BufferedReader in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // new Thread(new ClientThread()).start();
    }

    public void sendMessage(String message) {
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }

    public void onClick(View view) {
        try {
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddress, SERVER_PORT);
            try {
                // sends the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                // receives the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                EditText sendText = (EditText) findViewById(R.id.sendText);
                String data = sendText.getText().toString();

                sendMessage(data);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            EditText sendText = (EditText) findViewById(R.id.sendText);
//            String data = sendText.getText().toString();
//
//            // writes data to socket
//            PrintWriter out = new PrintWriter(new BufferedWriter(
//                    new OutputStreamWriter(socket.getOutputStream())), true);
//            out.println(data);
//            out.flush();
//
//            // reads data from socket
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String received = in.readLine();
//            System.out.println("message: " + received);
//
//            TextView receiveText = (TextView) findViewById(R.id.receiveText);
//            receiveText.setText(received);
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
//
//    class ClientThread implements Runnable {
//        @Override
//        public void run() {
//            try {
//                InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
//                socket = new Socket(serverAddress, SERVER_PORT);
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();;
//            }
//        }
//    }
}
