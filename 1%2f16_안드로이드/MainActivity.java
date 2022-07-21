package com.example.student.mysocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    EditText editText;
    EditText editText2;

    String host;
    int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });

    }
    public void request() {
        host = editText.getText().toString().trim();
        String portStr = editText2.getText().toString().trim();

        try {
            port = Integer.parseInt(portStr);
        } catch(Exception e) { e.printStackTrace(); }


        RequestThread thread = new RequestThread();
        thread.start();


    }

    class RequestThread extends Thread() {
        public void run() {

            try {
                Socket socket = new Socket(host, port);
                println("서버에 연결함 : " + host +", " + port);

                String output = "안녕!";
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeObject(output);
                outstream.flush();
                println("서버로 보냄: " + output);

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                String input = (String) instream.readObject();
                println("서버로부터 받은 데이터: " + input);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void println(String data) {
        handler.post(new Runnable() {
            public void run() {
                textView.append(data + "\n");
            }
        });
    }
}
