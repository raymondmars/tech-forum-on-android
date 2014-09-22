package com.rubychina.app.entities.v2ex;

import android.os.Parcel;
import android.os.Parcelable;

import com.rubychina.app.entities.INode;
import com.rubychina.app.entities.ITopic;
import com.rubychina.app.entities.IUser;

/**
 * Created by raymond on 9/16/14.
 */
public class Topic implements ITopic {

    private int id;
    private String title;
    private String last_touched;
    private int replies;
    private Node node;
    private String content_rendered;
    private Member member;

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

    @Override
    public int getReplyCount() {
        return replies;
    }

    @Override
    public INode getBelongNode() {
        return node;
    }

    @Override
    public IUser getCreateUser() {
        return member;
    }

    @Override
    public IUser getLastReplyUser() {
        Member m = new Member();
        m.setUsername("Unknown");
        return m;
    }

    @Override
    public String getLastReplyTime() {
        return last_touched;
    }

    public String getLast_touched() {
        return last_touched;
    }

    public void setLast_touched(String last_touched) {
        this.last_touched = last_touched;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getContent_rendered() {
        return content_rendered;
    }

    public void setContent_rendered(String content_rendered) {
        this.content_rendered = content_rendered;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(this.id);
        p.writeString(this.title);
        p.writeString(this.last_touched);
        p.writeInt(this.replies);
        p.writeString(this.content_rendered);
        p.writeParcelable(this.node, flags);
        p.writeParcelable(this.member,flags);

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
        last_touched = in.readString();
        replies = in.readInt();
        content_rendered = in.readString();
        node = in.readParcelable(Node.class.getClassLoader());
        member = in.readParcelable(Member.class.getClassLoader());


    }
}
