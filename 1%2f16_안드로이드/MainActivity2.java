package com.example.student.mysocket;

import android.os.AsyncTask;
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


        RequestTask task = new RequestTask();
        task.execute();
    }

    class RequestTask extends AsyncTask<String, Bundle, Integer> {
        public RequestTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params){
            try {
                Socket socket = new Socket(host, port);
                printLog("서버에 연결함 : " + host + ", " + port);

                String output = "안녕!";
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeObject(output);
                outstream.flush();
                printLog("서버로 보냄: " + output);

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                String input = (String) instream.readObject();
                printLog("서버로부터 받은 데이터: " + input);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private void printLog(String data) {
            Bundle bundle = new Bundle();
            bundle.putString("command", "println");
            bundle.putString("data", data);
            publicProgress(bundle);
        }

        @Override
        protected void onProgressUpdate(Bundle... values) {
            if (values != null && values.length > 0) {
                String command = values[0].getString("command");
                if (command != null) {
                    if(command.equals("println")) {
                        String data = values[0].getString("data");
                        println(data);
                    }
                }
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    public void println(String data) {
        textView.append(data + "\n");
    }
}
