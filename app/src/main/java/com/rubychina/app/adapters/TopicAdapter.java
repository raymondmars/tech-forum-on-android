package com.rubychina.app.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.rubychina.app.activities.R;
import com.rubychina.app.adapters.CommonListAdapter;
import com.rubychina.app.entities.Topic;
import com.rubychina.app.util.UIHelper;

import java.util.ArrayList;

/**
 * Created by robot on 5/12/14.
 */
public class TopicAdapter extends CommonListAdapter<Topic> {

    public TopicAdapter(Activity a,ArrayList<Topic> d)  {
        super(a,d);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(convertView == null) {
            vi = inflater.inflate(R.layout.list_row, null);
        }
        Topic tp = (Topic)getItem(position);

        NetworkImageView imageView = (NetworkImageView)vi.findViewById(R.id.list_image);
        UIHelper.setUserLogo(imageView, tp.getUser().getAvatar_url());

        TextView title = (TextView)vi.findViewById(R.id.txt_title);
        title.setText(tp.getTitle());

        UIHelper.renderTopicInfo(vi,tp);

        ((TextView)vi.findViewById(R.id.txt_replycount)).setText(String.valueOf(tp.getReplies_count()));

        return vi;

    }

}
