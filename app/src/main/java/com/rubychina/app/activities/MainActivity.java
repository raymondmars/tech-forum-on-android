package com.rubychina.app.activities;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import com.rubychina.app.adapters.loader.BaseLoader;
import com.rubychina.app.adapters.loader.LoaderCallback;
import com.rubychina.app.entities.ITopic;
import com.rubychina.app.services.BaseService;
import com.rubychina.app.adapters.*;
import java.util.ArrayList;



public class MainActivity extends BaseActivity implements LoaderCallback<ITopic> {

    protected ArrayList<ITopic> topics = null;
    protected BaseService service = null;
    protected BaseLoader loader = null;
    protected SwipeRefreshLayout swipeLayout;
    protected TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(topics == null) {
            topics = getIntent().getParcelableArrayListExtra(BaseService.Topic_List_Key);
        }
        if(service == null) {
            service = (BaseService)getIntent().getSerializableExtra(BaseService.Service_key);
            setTitle(service.getForumName());
            //getActionBar().setIcon(service.getForumLogoRes());
        }
        loader = service.create_loader(this);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(loader);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if (topics != null) {

            final ListView view = (ListView) findViewById(R.id.listView);
            adapter = new TopicAdapter(this,topics);
            view.setAdapter(adapter);

            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ITopic tp = (ITopic) adapterView.getItemAtPosition(i);
                    Intent details = new Intent(getApplicationContext(), TopicDetailsActivity.class);
                    details.putExtra("topic_id",tp.getId());
                    startActivity(details);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                }
            });

        }

    }

//    @Override
//    public void onRefresh() {
//
//
//
////        new Handler().postDelayed(new Runnable() {
////            @Override public void run() {
////                swipeLayout.setRefreshing(false);
////            }
////        }, 5000);
//    }


    @Override
    public void onDataLoaded(ArrayList<ITopic> list, BaseService service) {
        adapter.reload(list);
        adapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
    }
}
