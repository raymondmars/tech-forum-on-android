package com.rubychina.app.entities.v2ex;

import android.os.Parcel;
import android.os.Parcelable;

import com.rubychina.app.entities.IUser;

import java.util.regex.Pattern;

/**
 * Created by raymond on 9/16/14.
 */
public class Member implements IUser {
    private int id;
    private String username;
    private String tagline;
    private String avatar_mini;
    private String avatar_normal;

    public Member() {}
    public Member(Parcel in) {
        readFromParcel(in);
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

    @Override
    public String getAvatarImage() {
        if(Pattern.compile("^http").matcher(avatar_normal).find()) {
            return avatar_normal;
        } else {
            return "http:" + avatar_normal;
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
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
        p.writeString(this.username);
        p.writeString(this.tagline);
        p.writeString(this.avatar_mini);
        p.writeString(this.avatar_normal);
    }
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {

                public Member createFromParcel(Parcel in) {
                    return new Member(in);
                }

                public Member[] newArray(int size) {
                    return new Member[size];
                }

            };

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        username = in.readString();
        tagline = in.readString();
        avatar_mini = in.readString();
        avatar_normal = in.readString();
    }
}
