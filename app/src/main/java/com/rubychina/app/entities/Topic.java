package com.rubychina.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by raymond on 5/12/14.
 */
public class Topic implements Parcelable {

    private int id;
    private String title;
    private String replied_at;
    private int replies_count;
    private String node_name;
    private int node_id;
    private int last_reply_user_id;
    private String last_reply_user_login;
    private String body_html;
    private int hits;
    private User user;
    private List<Reply> replies;


    public Topic(Parcel in) {
        readFromParcel(in);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getReplies_count() {
        return replies_count;
    }

    public String getReplied_at() {
        return replied_at;
    }

    public void setReplied_at(String replied_at) {
        this.replied_at = replied_at;
    }

    public void setReplies_count(int replies_count) {
        this.replies_count = replies_count;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public int getLast_reply_user_id() {
        return last_reply_user_id;
    }

    public void setLast_reply_user_id(int last_reply_user_id) {
        this.last_reply_user_id = last_reply_user_id;
    }

    public String getLast_reply_user_login() {
        return last_reply_user_login;
    }

    public void setLast_reply_user_login(String last_reply_user_login) {
        this.last_reply_user_login = last_reply_user_login;
    }

    public String getBody_html() {
        return body_html;
    }

    public void setBody_html(String body_html) {
        this.body_html = body_html;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(this.id);
        p.writeString(this.title);
        p.writeString(this.replied_at);
        p.writeInt(this.replies_count);
        p.writeString(this.node_name);
        p.writeInt(this.node_id);
        p.writeInt(this.last_reply_user_id);
        p.writeString(this.last_reply_user_login);
        p.writeString(this.body_html);
        p.writeInt(this.hits);
        p.writeParcelable(this.user, flags);
        p.writeTypedList(this.replies);


    }
    public static final Parcelable.Creator CREATOR =
        new Parcelable.Creator() {

            public Topic createFromParcel(Parcel in) {
                return new Topic(in);
            }

            public Topic[] newArray(int size) {
                return new Topic[size];
            }

        };

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        replied_at = in.readString();
        replies_count = in.readInt();
        node_name = in.readString();
        node_id = in.readInt();
        last_reply_user_id = in.readInt();
        last_reply_user_login = in.readString();
        body_html = in.readString();
        hits = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        replies = new ArrayList<Reply>();
        in.readTypedList(replies, Reply.CREATOR);

    }
}
