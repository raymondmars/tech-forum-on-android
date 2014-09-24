package com.rubychina.app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.rubychina.app.entities.*;
import com.rubychina.app.services.BaseService;
import com.rubychina.app.services.RubyChinaService;
import com.rubychina.app.services.V2exService;

import java.util.ArrayList;



public class NavActivity extends Activity implements BaseService.LoaderListCallback<Topic> {

    protected  ProgressDialog progress;
    protected Intent _main_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        setTitle("Tech Forums");
        _main_activity = new Intent(getApplicationContext(),MainActivity.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick_RubyChina(View view) {
        progress = new ProgressDialog(view.getContext());
        progress.setTitle("Reading");
        progress.setMessage("Wait while reading...");
        progress.show();
        RubyChinaService.instance.getLatestTopics(this);
    }

    public void onClick_V2ex(View view) {
        progress = new ProgressDialog(view.getContext());
        progress.setTitle("Reading");
        progress.setMessage("Wait while reading...");
        progress.show();
        V2exService.instance.getLatestTopics(this);
    }

    @Override
    public void onListDataLoaded(ArrayList<Topic> list, BaseService service) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BaseService.Topic_List_Key, list);
        bundle.putSerializable(BaseService.Service_key,service);
        _main_activity = new Intent(getApplicationContext(),MainActivity.class);
        _main_activity.putExtras(bundle);
        startActivity(_main_activity);

        progress.dismiss();
    }
}
