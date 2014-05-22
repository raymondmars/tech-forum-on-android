package com.rubychina.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by robot on 5/12/14.
 */
public class User implements Parcelable {
    private int id;
    private String login;
    private String avatar_url;

//    public User(int id, String login, String avatar_url) {
//        this.id = id;
//        this.login = login;
//        this.avatar_url = avatar_url;
//    }
    public User(Parcel in) {
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(this.id);
        p.writeString(this.login);
        p.writeString(this.avatar_url);
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

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        login = in.readString();
        avatar_url = in.readString();
    }
}
