package com.rubychina.app.util;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rubychina.app.activities.R;
import com.rubychina.app.entities.ITopic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by robot on 5/16/14.
 */
public class UIHelper {
    public static String timesAgo(String last_date_str) {
        try {
            if(last_date_str != null) {

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date last_reply_date = format.parse(last_date_str);
                Calendar cal = Calendar.getInstance();
                long mil_seconds = (System.currentTimeMillis() - last_reply_date.getTime())/1000;
                String rt = "";
                if(mil_seconds < 60) {
                    rt = "不到1分钟前回复";
                }else if(mil_seconds>=60 && mil_seconds< 60*60) {
                    rt = "于" + String.valueOf(mil_seconds/60) + "分钟前回复";
                }else if(mil_seconds>= 60*60 && mil_seconds< 60*60*24) {
                    rt = "于" + String.valueOf(mil_seconds/(60*60)) + "小时前回复";
                }else {
                    rt = "于" + String.valueOf(mil_seconds/(60*60*24)) + "天前回复";
                }

                return rt;
            } else {
                return "";
            }

        } catch(ParseException ex) {
            return "";
        }



    }

    public static void renderTopicInfo(View vi,ITopic tp) {
        ((TextView)vi.findViewById(R.id.txt_nodename)).setText(tp.getBelongNode().getName());
        ((TextView)vi.findViewById(R.id.txt_topic_user)).setText(underLine(tp.getCreateUser().getUsername()));
        ((TextView)vi.findViewById(R.id.txt_last_reply_user)).setText(underLine(tp.getLastReplyUser().getUsername()));
        ((TextView)vi.findViewById(R.id.txt_reply_time)).setText(timesAgo(tp.getLastReplyTime()));
    }
    public static void renderV2exTopicInfo(View vi, com.rubychina.app.entities.v2ex.Topic tp) {
        ((TextView)vi.findViewById(R.id.txt_nodename)).setText(tp.getNode().getName());
        ((TextView)vi.findViewById(R.id.txt_topic_user)).setText(underLine(tp.getMember().getUsername()));
        //((TextView)vi.findViewById(R.id.txt_last_reply_user)).setText(underLine(tp.getLast_reply_user_login()));
        //((TextView)vi.findViewById(R.id.txt_reply_time)).setText(timesAgo(tp.getReplied_at()));
    }
    public static void setUserLogo(NetworkImageView imageView,String url) {
        ImageLoader imageLoader = TopApp.getInstance().getImageLoader();
        imageView.setDefaultImageResId(R.drawable.ic_user_default);
        imageView.setErrorImageResId(R.drawable.ic_user_default);
        imageView.setImageUrl(url,imageLoader);
    }
    public static Spanned underLine(String v) {
        return Html.fromHtml("<u>" + v + "</u>");
    }
}
