package com.example.amran.allahakbar;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstButton extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;

            } catch (Exception e) {

                e.printStackTrace();

                return "Failed";

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_button);

        String text = null;

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);

        getSupportActionBar().hide();

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("http://muslimsalat.com/london/daily.json?key=API_KEY&jsoncallback=?").get();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        }

        Pattern p = Pattern.compile("\"fajr\":\"(.*?)\",\"");
        Matcher m = p.matcher(result);

        while (m.find()) {

            textView.setText(String.valueOf(m.group(1)));

        }

        Pattern p1 = Pattern.compile("\"dhuhr\":\"(.*?)\",\"");
        Matcher m1 = p1.matcher(result);

        while (m1.find()) {

            textView1.setText(String.valueOf(m1.group(1)));

        }

        Pattern p2 = Pattern.compile("\"asr\":\"(.*?)\",\"");
        Matcher m2 = p2.matcher(result);

        while (m2.find()) {

            textView2.setText(String.valueOf(m2.group(1)));

        }

        Pattern p3 = Pattern.compile("\"maghrib\":\"(.*?)\",\"");
        Matcher m3 = p3.matcher(result);

        while (m3.find()) {

            textView3.setText(String.valueOf(m3.group(1)));

        }

        Pattern p4 = Pattern.compile("\"isha\":\"(.*?)\"");
        Matcher m4 = p4.matcher(result);

        while (m4.find()) {

            textView4.setText(String.valueOf(m4.group(1)));

        }

    }
}
