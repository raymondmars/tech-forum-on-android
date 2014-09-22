package com.rubychina.app.entities;

import android.os.Parcelable;

/**
 * Created by raymond on 9/17/14.
 */
public interface IUser extends Parcelable {
    int getId();
    String getUsername();
    String getAvatarImage();
}
