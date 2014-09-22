package com.rubychina.app.entities;

import android.os.Parcelable;

/**
 * Created by raymond on 9/17/14.
 */
public interface ITopic extends Parcelable{

    int getId();
    String getTitle();
    int getReplyCount();
    INode getBelongNode();
    IUser getCreateUser();
    IUser getLastReplyUser();
    String getLastReplyTime();


}
