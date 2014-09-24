package com.rubychina.app.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.rubychina.app.adapters.ReplyAdapter;
import com.rubychina.app.entities.Reply;
import com.rubychina.app.entities.Topic;
import com.rubychina.app.entities.TopicDetail;
import com.rubychina.app.exlibs.*;
import com.rubychina.app.services.BaseService;
import com.rubychina.app.util.HtmlImageDrawer;
import com.rubychina.app.util.UIHelper;

import java.util.ArrayList;


public class TopicDetailsActivity extends BaseActivity implements BaseService.LoaderCallback<TopicDetail> {

    protected ProgressDialog progress = null;
    protected BaseService service = null;
    protected Topic topic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((LinearLayout)findViewById(R.id.detail_layout)).setVisibility(View.INVISIBLE);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(topic == null) {
            topic = (Topic)getIntent().getParcelableExtra(BaseService.Topic_Key);
        }

        setTitle(topic.getTitle());

        if(service == null) {
            service = (BaseService)getIntent().getSerializableExtra(BaseService.Service_key);
        }
        getActionBar().setIcon(service.getForumLogoRes());

        if(progress == null)
            progress = new ProgressDialog(this);

        if(topic != null) {
            progress = new ProgressDialog(this);
            progress.setTitle("Reading");
            progress.setMessage("Wait while reading...");
            progress.show();
            service.getTopicDetail(topic,this);

        }

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
    private void render_topic_detail(TopicDetail detail) {

        Topic topic = detail.getTopic();

        NetworkImageView imageView = (NetworkImageView)findViewById(R.id.img_detail_user_logo);
        UIHelper.setUserLogo(imageView, topic.getCreateUser().getAvatarImage());

        ((TextView) findViewById(R.id.txt_detail_title)).setText(topic.getTitle());
        TextView topic_content_view = (TextView)findViewById(R.id.txt_topic_content);
        topic_content_view.setMovementMethod(new ScrollingMovementMethod());
        topic_content_view.setMovementMethod(LinkMovementMethod.getInstance());
        HtmlImageDrawer drawer = new HtmlImageDrawer(topic_content_view,getResources().getDrawable(R.drawable.ic_image_loading));
        topic_content_view.setText(Html.fromHtml(topic.getContent(),drawer,null));

        View current_view = this.findViewById(android.R.id.content);

        UIHelper.renderTopicInfo(current_view,topic);

        //render reply list
        String rc = "共收到 " + String.valueOf(topic.getReplyCount()) + " 条回复";
        ((TextView)findViewById(R.id.txt_reply_count_display)).setText(rc);

        ExpandableHeightListView view = (ExpandableHeightListView) findViewById(R.id.listview_replies);

//        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) view.getLayoutParams();
//        lp.height = 100*detail.getReplyList().size();
//        view.setLayoutParams(lp);

        ReplyAdapter adapter = new ReplyAdapter(this, detail.getReplyList());
        view.setAdapter(adapter);


        ScrollView scrollView = (ScrollView)findViewById(R.id.main_scroll_view);
        scrollView.fullScroll(View.FOCUS_UP);
    }


    @Override
    public void onDataLoaded(TopicDetail data, BaseService service) {

        ((LinearLayout)findViewById(R.id.detail_layout)).setVisibility(View.VISIBLE);

        render_topic_detail(data);

        progress.dismiss();
    }
}
