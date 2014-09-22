package com.rubychina.app.adapters.loader;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rubychina.app.activities.MainActivity;
import com.rubychina.app.adapters.TopicAdapter;
import com.rubychina.app.entities.ITopic;
import com.rubychina.app.entities.v2ex.Topic;
import com.rubychina.app.exlibs.JsonUTF8ArrayRequest;
import com.rubychina.app.services.BaseService;
import com.rubychina.app.services.V2exService;
import com.rubychina.app.util.TopApp;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymond on 9/19/14.
 */
public class V2exLoader extends BaseLoader<ITopic> {

    public V2exLoader(LoaderCallback<ITopic> callback) {
        super(callback);
    }

    @Override
    public void loadData() {
        JsonUTF8ArrayRequest req = new JsonUTF8ArrayRequest(V2exService.instance.getTopicListApi(),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Topic>>() {}.getType();
                        List<com.rubychina.app.entities.v2ex.Topic> topics = gson.fromJson(response.toString(), listType);

                        ArrayList<ITopic> list = new ArrayList<ITopic>() ;
                        for(int i=0;i < topics.size();i++){
                            list.add(topics.get(i));
                        }
                        _listdataLoadedCallback.onDataLoaded(list,V2exService.instance);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TOPIC", "Error: " + error.getMessage());
            }
        }
        );
        TopApp.getInstance().addToRequestQueue(req, BaseService.Tag_TopicList_Req);
    }

    @Override
    public void onRefresh() {
        this.loadData();
    }
}
