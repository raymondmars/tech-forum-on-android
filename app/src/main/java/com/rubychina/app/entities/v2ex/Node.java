package com.rubychina.app.entities.v2ex;

import android.os.Parcel;
import android.os.Parcelable;

import com.rubychina.app.entities.INode;

/**
 * Created by raymond on 9/16/14.
 */
public class Node implements INode {
    private int id;
    private String name;
    private String title;
    private int topics;
    private String avatar_mini;
    private String avatar_normal;

    public Node(Parcel in) {
        readFromParcel(in);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTopics() {
        return topics;
    }

    public void setTopics(int topics) {
        this.topics = topics;
    }

    public String getAvatar_mini() {
        return avatar_mini;
    }

    public void setAvatar_mini(String avatar_mini) {
        this.avatar_mini = avatar_mini;
    }

    public String getAvatar_normal() {
        return avatar_normal;
    }

    public void setAvatar_normal(String avatar_normal) {
        this.avatar_normal = avatar_normal;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(this.id);
        p.writeString(this.name);
        p.writeString(this.title);
        p.writeInt(this.topics);
        p.writeString(this.avatar_mini);
        p.writeString(this.avatar_normal);
    }
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {

                public Node createFromParcel(Parcel in) {
                    return new Node(in);
                }

                public Node[] newArray(int size) {
                    return new Node[size];
                }

            };

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        title = in.readString();
        topics = in.readInt();
        avatar_mini = in.readString();
        avatar_normal = in.readString();
    }
}
