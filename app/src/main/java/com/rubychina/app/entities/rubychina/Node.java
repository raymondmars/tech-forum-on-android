package com.rubychina.app.entities.rubychina;

import android.os.Parcel;

import com.rubychina.app.entities.INode;

/**
 * Created by raymond on 9/18/14.
 */
public class Node implements INode {
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
    }
}
