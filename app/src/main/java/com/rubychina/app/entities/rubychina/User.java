package com.rubychina.app.entities.rubychina;

import android.os.Parcel;
import android.os.Parcelable;

import com.rubychina.app.entities.IUser;

/**
 * Created by robot on 5/12/14.
 */
public class User implements IUser {
    private int id;
    private String login;
    private String avatar_url;

    public User() {}
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
        if(avatar_url.indexOf("https:") < 0) {
            avatar_url = "http:" + avatar_url;
        }
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getAvatarImage() {
        return avatar_url;
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
