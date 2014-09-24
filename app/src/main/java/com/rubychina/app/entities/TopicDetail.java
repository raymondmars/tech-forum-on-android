package com.rubychina.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by raymond on 9/22/14.
 */
public class TopicDetail implements Parcelable {
    private Topic topic;
    private ArrayList<Reply> replyList;

    public ArrayList<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<Reply> replyList) {
        this.replyList = replyList;
    }

    public TopicDetail() {}
    public TopicDetail(Parcel p) {
        this.topic = p.readParcelable(Topic.class.getClassLoader());
        this.replyList = new ArrayList<Reply>();
        p.readTypedList(this.replyList,Reply.CREATOR);
    }
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {

                public TopicDetail createFromParcel(Parcel in) {
                    return new TopicDetail(in);
                }

                public TopicDetail[] newArray(int size) {
                    return new TopicDetail[size];
                }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int i) {
        p.writeParcelable(this.topic,i);
        p.writeTypedList(this.replyList);

    }
}
