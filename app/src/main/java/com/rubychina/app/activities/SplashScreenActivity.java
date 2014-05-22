package com.rubychina.app.activities;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rubychina.app.entities.Topic;
import com.rubychina.app.exlibs.JsonUTF8ArrayRequest;
import com.rubychina.app.services.TopicService;
import com.rubychina.app.util.TopApp;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SplashScreenActivity extends Activity {

    protected boolean _active = true;
    protected int _splashTime = 10 * 1000;
    protected Intent _main_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.hideActionBar();
        //this.hideStatusBar();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        _main_activity = new Intent(getApplicationContext(),MainActivity.class);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    // 启动主应用
                    startActivity(_main_activity);

                }
            }
        };
        splashTread.start();
        loadTopicList();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }

    protected void loadTopicList() {

        JsonUTF8ArrayRequest req = new JsonUTF8ArrayRequest(TopicService.getTopicListApi(),
            new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response)  {

                Gson gson = new Gson();
                //Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Type listType = new TypeToken<List<Topic>>(){}.getType();
                List<Topic> topics = gson.fromJson(response.toString(),listType);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(TopicService.Topic_List_Key,(ArrayList<Topic>)topics);
                _main_activity.putExtras(bundle);

                //pDialog.hide();
                _active = false;
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.d("TOPIC", "Error: " + error.getMessage());
            //pDialog.hide();
            _active = false;
            }
        });
        TopApp.getInstance().addToRequestQueue(req, TopicService.Tag_TopicList_Req);
    }


}
