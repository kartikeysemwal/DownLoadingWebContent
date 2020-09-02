package com.example.downloadingwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadContent extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls)
        {
            URL url = null;
            HttpURLConnection urlConnection = null;
            String result = "";

            try
            {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data= reader.read();
                while(data!=-1)
                {
                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }

                return  result;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "Failed";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadContent task = new DownloadContent();
        String result=null;
        try
        {
            result = task.execute("http://www.google.com").get();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        Log.i("Contents of URL", result);
    }
}
