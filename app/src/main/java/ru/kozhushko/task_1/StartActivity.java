package ru.kozhushko.task_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getActionBar().hide();
        Toast.makeText(getApplicationContext(),"onCreate",Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        JSONObject json;
        final Runnable doNextActivity = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        //здесь загрузить JSON
        new Thread() {
            @Override
            public void run() {
                Log.d("D","StartLoad");
                JSONObject json = getJSONFromUrl("http://mobevo.ext.terrhq.ru/shr/j/ru/technology.js");
                Intent intent=new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("JSON",json.toString());
                startActivity(intent);
                handler.post(doNextActivity);
            }
        }.start();
    }

    public static JSONObject getJSONFromUrl(String url) {
        UrlEncodedFormEntity entity = null;
        JSONObject jsn=null;
        try {
            HttpGet request = new HttpGet(url);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            response = client.execute(request);
            HttpEntity entry = response.getEntity();
            String responseText = null;
            responseText = EntityUtils.toString(entry, HTTP.UTF_8);
            jsn = new JSONObject(responseText);
        } catch (UnsupportedEncodingException e) {

        }  catch (IOException e) {

        } catch (JSONException e) {

        } catch (NullPointerException e) {

        }
        return jsn;
    }

}
