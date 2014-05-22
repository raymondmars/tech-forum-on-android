package com.rubychina.app.adapters;

import android.app.Activity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.rubychina.app.activities.R;
import com.rubychina.app.adapters.CommonListAdapter;
import com.rubychina.app.entities.Reply;
import com.rubychina.app.util.HtmlImageDrawer;
import com.rubychina.app.util.UIHelper;

import java.util.ArrayList;

/**
 * Created by raymond on 5/16/14.
 */
public class ReplyAdapter extends CommonListAdapter<Reply> {

    public ReplyAdapter(Activity a,ArrayList<Reply> d) {
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
            vi = inflater.inflate(R.layout.reply_list_row, null);
        }
        if(data.size() > 0) {

            Reply reply = (Reply) getItem(position);

            NetworkImageView imageView = (NetworkImageView) vi.findViewById(R.id.reply_user_image);
            UIHelper.setUserLogo(imageView, reply.getUser().getAvatar_url());

            ((TextView) vi.findViewById(R.id.txt_reply_user)).setText(reply.getUser().getLogin());
            ((TextView) vi.findViewById(R.id.txt_reply_floor_count)).setText(String.valueOf(position + 1) + "楼");
            ((TextView) vi.findViewById(R.id.txt_reply_list_time)).setText(UIHelper.timesAgo(reply.getCreated_at()));

            TextView reply_content = ((TextView) vi.findViewById(R.id.txt_reply_content));
            //reply_content.setMovementMethod(new ScrollingMovementMethod());
            reply_content.setMovementMethod(LinkMovementMethod.getInstance());

            HtmlImageDrawer drawer = new HtmlImageDrawer(reply_content, vi.getResources().getDrawable(R.drawable.ic_image_loading));
            reply_content.setText(Html.fromHtml(reply.getBody_html(), drawer, null));
        }

        return vi;

    }
}
