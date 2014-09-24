package com.rubychina.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raymond on 9/22/14.
 */
public class User implements Parcelable {
    private int id;
    private String username;
    private String avatarImage;

    public User() {}
    public User(Parcel p) {
        this.id = p.readInt();
        this.username = p.readString();
        this.avatarImage = p.readString();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {

                public User createFromParcel(Parcel in) {
                    return new User(in);
                }

                public User[] newArray(int size) {
                    return new User[size];
                }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int i) {
        p.writeInt(this.id);
        p.writeString(this.username);
        p.writeString(this.avatarImage);

    }
}
