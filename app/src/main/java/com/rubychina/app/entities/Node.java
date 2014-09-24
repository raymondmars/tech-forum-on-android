package com.rubychina.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raymond on 9/22/14.
 */
public class Node implements Parcelable {
    private int id;
    private String name;
    private int topicCount;

    public Node() {}
    public Node(Parcel p) {
        this.id = p.readInt();
        this.name = p.readString();
        this.topicCount = p.readInt();
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

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int i) {
        p.writeInt(this.id);
        p.writeString(this.name);
        p.writeInt(this.topicCount);
    }
}
