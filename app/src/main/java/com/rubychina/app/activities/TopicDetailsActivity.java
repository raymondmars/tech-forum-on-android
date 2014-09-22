package com.rubychina.app.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.rubychina.app.entities.rubychina.Reply;
import com.rubychina.app.entities.rubychina.Topic;
import com.rubychina.app.exlibs.*;
import com.rubychina.app.util.HtmlImageDrawer;
import com.rubychina.app.exlibs.JsonUTF8ObjectRequest;
import com.rubychina.app.adapters.ReplyAdapter;
import com.rubychina.app.util.TopApp;
import com.rubychina.app.util.UIHelper;

import org.json.JSONObject;

import java.util.ArrayList;

public class TopicDetailsActivity extends BaseActivity  {

    protected ProgressDialog progress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        int topic_id = getIntent().getIntExtra("topic_id",0);
        //Log.d("Topic",String.valueOf(topic_id));

        if(progress == null)
            progress = new ProgressDialog(this);

//        if(topic_id > 0) {
//            progress = new ProgressDialog(this);
//            progress.setTitle("Reading");
//            progress.setMessage("Wait while reading...");
//            progress.show();
//            JsonUTF8ObjectRequest req = new JsonUTF8ObjectRequest(TopicService.getTopicApi(topic_id),null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response)  {
//                            Gson gson = new Gson();
//                            Topic tp = gson.fromJson(response.toString(),Topic.class);
//                            //Log.d("Topic",tp.getTitle());
//                            render_topic(tp);
//                            progress.dismiss();
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.d("TOPIC", "Error: " + error.getMessage());
//                    progress.dismiss();
//                }
//            });
//            TopApp.getInstance().addToRequestQueue(req, TopicService.Tag_Topic_Req);
//        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            progress.dismiss();
            //NavUtils.navigateUpTo(this, new Intent(getApplicationContext(), MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void render_topic(Topic topic) {

        NetworkImageView imageView = (NetworkImageView)findViewById(R.id.img_detail_user_logo);
        UIHelper.setUserLogo(imageView, topic.getUser().getAvatar_url());

        ((TextView) findViewById(R.id.txt_detail_title)).setText(topic.getTitle());
        TextView topic_content_view = (TextView)findViewById(R.id.txt_topic_content);
        topic_content_view.setMovementMethod(new ScrollingMovementMethod());
        topic_content_view.setMovementMethod(LinkMovementMethod.getInstance());
        HtmlImageDrawer drawer = new HtmlImageDrawer(topic_content_view,getResources().getDrawable(R.drawable.ic_image_loading));
        topic_content_view.setText(Html.fromHtml(topic.getBody_html(),drawer,null));

        View current_view = this.findViewById(android.R.id.content);

        //UIHelper.renderTopicInfo(current_view,topic);

        //render reply list
        String rc = "共收到 " + String.valueOf(topic.getReplies_count()) + " 条回复";
        ((TextView)findViewById(R.id.txt_reply_count_display)).setText(rc);

        ExpandableHeightListView view = (ExpandableHeightListView) findViewById(R.id.listview_replies);

        //ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) view.getLayoutParams();
        //lp.height = 100*topic.getReplies_count();
        //view.setLayoutParams(lp);

        ReplyAdapter adapter = new ReplyAdapter(this, (ArrayList<Reply>)topic.getReplies());
        view.setAdapter(adapter);


        //ScrollView scrollView = (ScrollView)findViewById(R.id.main_scroll_view);
        //scrollView.fullScroll(View.FOCUS_UP);
    }



}
