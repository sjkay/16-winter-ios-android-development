package com.example.student.mysocket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                String urlStr = "http://m.naver.com";
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    int resCode = conn.getResponseCode();
                    //if (resCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while(true) {
                            String line = reader.readLine();
                            if (line == null) {
                                break;
                            }

                            printLog(line);
                        }

                        reader.close();
                        conn.disconnect();
                    //}
                }
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
