package com.rubychina.app.services;

import android.support.v4.widget.SwipeRefreshLayout;

import com.rubychina.app.adapters.loader.BaseLoader;
import com.rubychina.app.adapters.loader.LoaderCallback;

import java.io.Serializable;

/**
 * Created by raymond on 9/17/14.
 */
public abstract class BaseService implements Serializable {

    public final static String Service_key = "Service_Key";
    public final static String Tag_TopicList_Req = "Tag_TopicList_Req";
    public final static String Tag_Topic_Req = "Tag_Topic_Req";
    public final static String Topic_List_Key = "Topic_List_Key";
    public final static String Topic_List_Loader = "Topic_List_Loader_Key";

    public abstract String getForumName();
    public abstract int getForumLogoRes();
    public abstract String getTopicApi(int id);
    public abstract String getTopicListApi();

    public abstract BaseLoader create_loader(LoaderCallback callback);
}
