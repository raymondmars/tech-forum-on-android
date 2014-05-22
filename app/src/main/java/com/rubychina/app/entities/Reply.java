package com.rubychina.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by robot on 5/14/14.
 */
public class Reply implements Parcelable {
    private int id;
    private String body_html;
    private String created_at;
    private int topic_id;
    private User user;

    public Reply(Parcel in) {
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody_html() {
        return body_html;
    }

    public void setBody_html(String body_html) {
        this.body_html = body_html;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(this.id);
        p.writeString(this.body_html);
        p.writeString(this.created_at);
        p.writeInt(this.topic_id);
        p.writeParcelable(this.user,flags);
    }
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {

                public Reply createFromParcel(Parcel in) {
                    return new Reply(in);
                }

                public Reply[] newArray(int size) {
                    return new Reply[size];
                }

            };

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        body_html = in.readString();
        created_at = in.readString();
        topic_id = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
    }
}
