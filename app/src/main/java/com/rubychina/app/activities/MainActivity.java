package com.rubychina.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import com.rubychina.app.entities.Topic;
import com.rubychina.app.services.TopicService;
import com.rubychina.app.adapters.TopicAdapter;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    private static ArrayList<Topic> topics = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(topics == null) {
            topics = getIntent().getParcelableArrayListExtra(TopicService.Topic_List_Key);
        }
        if (topics != null) {
            ListView view = (ListView) findViewById(R.id.listView);
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
        }

    }


}
