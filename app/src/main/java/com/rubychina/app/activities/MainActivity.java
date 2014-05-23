package com.rubychina.app.activities;

import android.graphics.Interpolator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rubychina.app.entities.Topic;
import com.rubychina.app.exlibs.JsonUTF8ArrayRequest;
import com.rubychina.app.exlibs.PullToRefreshListView;
import com.rubychina.app.services.TopicService;
import com.rubychina.app.adapters.TopicAdapter;
import com.rubychina.app.util.TopApp;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Result;


public class MainActivity extends BaseActivity {

    protected static ArrayList<Topic> topics = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(topics == null) {
            topics = getIntent().getParcelableArrayListExtra(TopicService.Topic_List_Key);
        }
        if (topics != null) {
            final PullToRefreshListView view = (PullToRefreshListView) findViewById(R.id.listView);
            final TopicAdapter adapter = new TopicAdapter(this, topics);
            view.setAdapter(adapter);

            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Topic tp = (Topic) adapterView.getItemAtPosition(i);
                    Intent details = new Intent(getApplicationContext(), TopicDetailsActivity.class);
                    details.putExtra("topic_id",tp.getId());
                    startActivity(details);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                }
            });

            //view.setChoiceMode(3);

            //view.setChoiceMode(Mode.P);
            view.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Do work to refresh the list here.
                    JsonUTF8ArrayRequest req = new JsonUTF8ArrayRequest(TopicService.getTopicListApi(),
                            new Response.Listener<JSONArray>() {

                                @Override
                                public void onResponse(JSONArray response) {

                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<List<Topic>>() {
                                    }.getType();
                                    List<Topic> tps = gson.fromJson(response.toString(), listType);

                                    if(tps!=null && tps.size()>0){
                                        Topic old_tp = (Topic)adapter.getItem(0);
                                        if(old_tp.getId() != tps.get(0).getId()) {
                                            Toast.makeText(MainActivity.this, "data updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    adapter.reload(tps);
                                    String lastUpdateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                                    //view.setLastUpdated((new Date().toString()));
                                    view.onRefreshComplete(lastUpdateTime);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("TOPIC", "Error: " + error.getMessage());
                        }
                    }
                    );
                    TopApp.getInstance().addToRequestQueue(req, TopicService.Tag_TopicList_Req);

                }

            });

//            view.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                    // TODO Auto-generated method stub
//                    if (scrollState == SCROLL_STATE_IDLE) {
//                        Toast.makeText(MainActivity.this, "down", Toast.LENGTH_SHORT).show();
//                        System.out.println("down");
//                    } else if (scrollState == SCROLL_STATE_IDLE) {
//                        Toast.makeText(MainActivity.this, "up", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem,
//                                     int visibleItemCount, int totalItemCount) {
//                    // TODO Auto-generated method stub
//                    int firstItem = firstVisibleItem;
//                    int lastItem = firstVisibleItem + visibleItemCount - 2;
//                }
//            });


        }


    }


}
