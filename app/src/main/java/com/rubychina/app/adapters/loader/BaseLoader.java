package com.rubychina.app.adapters.loader;

import android.content.Loader;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import com.rubychina.app.adapters.TopicAdapter;

import java.io.Serializable;

/**
 * Created by raymond on 9/19/14.
 */
public abstract class BaseLoader<E> implements SwipeRefreshLayout.OnRefreshListener, Serializable{
    protected LoaderCallback<E> _listdataLoadedCallback;

    public BaseLoader(LoaderCallback<E> callback) {
        _listdataLoadedCallback = callback;
    }
    public abstract void loadData();
}
