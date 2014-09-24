package com.rubychina.app.activities;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import com.rubychina.app.entities.Node;
import com.rubychina.app.entities.Topic;
import com.rubychina.app.services.BaseService;
import com.rubychina.app.adapters.*;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;


public class MainActivity extends BaseActivity implements BaseService.LoaderListCallback<Topic>, BaseService.LoaderNodeListCallback<Node>, ListView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<Topic> topics = null;
    private BaseService service = null;
    private SwipeRefreshLayout swipeLayout;
    private TopicAdapter adapter;
    private ProgressDialog progress = null;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private int current_node_id = 0;


    private static HashMap<String,ArrayList<Node>> node_hash = new HashMap<String,ArrayList<Node>>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(topics == null) {
            topics = getIntent().getParcelableArrayListExtra(BaseService.Topic_List_Key);
        }
        if(service == null) {
            service = (BaseService)getIntent().getSerializableExtra(BaseService.Service_key);
        }
        setTitle(service.getForumName());
        getActionBar().setIcon(service.getForumLogoRes());

        if(node_hash.get(service.getForumName()) == null) {
            service.getNodes(this);
        } else {
            render_nodes(node_hash.get(service.getForumName()));
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.node_slide_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
//                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(mDrawerTitle);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
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
                    Topic tp = (Topic) adapterView.getItemAtPosition(i);
                    Intent details = new Intent(getApplicationContext(), TopicDetailsActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BaseService.Service_key,service);
                    bundle.putParcelable(BaseService.Topic_Key,tp);
                    details.putExtras(bundle);

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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListDataLoaded(ArrayList<Topic> list, BaseService service) {
        adapter.reload(list);
        adapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);

        if(progress != null) {
            progress.dismiss();
        }
        Toast.makeText(getApplicationContext(), "Data Updated",
                Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onRefresh() {
        if(current_node_id > 0) {
            service.getNodeTopics(current_node_id, this);
        } else {
            service.getLatestTopics(this);
        }

    }


    @Override
    public void onNodeListDataLoaded(ArrayList<Node> list, BaseService service) {
        node_hash.put(service.getForumName(),list);
        render_nodes(list);
    }
    protected void render_nodes(ArrayList<Node> list) {
        NodeAdapter na = new NodeAdapter(this,list);
        if(drawerList == null) {
            drawerList = (ListView) findViewById(R.id.left_drawer);
        }
        drawerList.setAdapter(na);
        drawerList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Node nd =   (Node)adapterView.getItemAtPosition(i);
        //Log.v(String.valueOf(nd.getId()),"node id");
        //Log.v(nd.getName(),"node name");

        setTitle(service.getForumName() + " > " + nd.getName());
        drawerList.setItemChecked(i,true);
        drawerLayout.closeDrawer(drawerList);

        current_node_id = nd.getId();

        //View parent = adapterView.getRootView();
        progress = new ProgressDialog(drawerLayout.getContext());
        progress.setTitle("Reading");
        progress.setMessage("Wait while reading...");
        progress.show();
        service.getNodeTopics(nd.getId(),this);
    }
}
