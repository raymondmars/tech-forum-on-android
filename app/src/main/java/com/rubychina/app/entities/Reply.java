package com.rubychina.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raymond on 9/22/14.
 */
public class Reply implements Parcelable {
    private int id;
    private String content;
    private User replyUser;
    private String replyTime;
    private int likeCount;

    public Reply() {}
    public Reply(Parcel p) {
        this.id = p.readInt();
        this.content = p.readString();
        this.replyUser = p.readParcelable(User.class.getClassLoader());
        this.replyTime = p.readString();
        this.likeCount = p.readInt();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(User replyUser) {
        this.replyUser = replyUser;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int i) {
        p.writeInt(this.id);
        p.writeString(this.content);
        p.writeParcelable(this.replyUser,i);
        p.writeString(this.replyTime);
        p.writeInt(this.likeCount);

    }
}
