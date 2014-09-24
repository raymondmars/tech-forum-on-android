package com.rubychina.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raymond on 9/22/14.
 */
public class Topic implements Parcelable {

    private int id;
    private String title;
    private String content;
    private int replyCount;
    private Node belongNode;
    private User createUser;
    private User lastReplyUser;
    private String lastReplyTime;

    public Topic() {}
    public Topic(Parcel p) {
        this.id = p.readInt();
        this.title = p.readString();
        this.content = p.readString();
        this.replyCount = p.readInt();
        this.belongNode = p.readParcelable(Node.class.getClassLoader());
        this.createUser = p.readParcelable(User.class.getClassLoader());
        this.lastReplyUser = p.readParcelable(User.class.getClassLoader());
        this.lastReplyTime = p.readString();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Node getBelongNode() {
        return belongNode;
    }

    public void setBelongNode(Node belongNode) {
        this.belongNode = belongNode;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public User getLastReplyUser() {
        return lastReplyUser;
    }

    public void setLastReplyUser(User lastReplyUser) {
        this.lastReplyUser = lastReplyUser;
    }

    public String getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(String lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int i) {
        p.writeInt(this.id);
        p.writeString(this.title);
        p.writeString(this.content);
        p.writeInt(this.replyCount);
        p.writeParcelable(this.belongNode,i);
        p.writeParcelable(this.createUser,i);
        p.writeParcelable(this.lastReplyUser,i);
        p.writeString(this.lastReplyTime);

    }
}
