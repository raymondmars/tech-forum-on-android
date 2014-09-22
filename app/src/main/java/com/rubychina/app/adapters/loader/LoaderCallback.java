package com.rubychina.app.adapters.loader;

import com.rubychina.app.entities.ITopic;
import com.rubychina.app.services.BaseService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymond on 9/19/14.
 */
public interface LoaderCallback<E> {
    void onDataLoaded(ArrayList<E> list,BaseService service);
}
