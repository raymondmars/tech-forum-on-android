package com.rubychina.app.services;

import com.rubychina.app.entities.Node;
import com.rubychina.app.entities.Topic;
import com.rubychina.app.entities.TopicDetail;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by raymond on 9/17/14.
 */
public abstract class BaseService implements Serializable {

    public interface LoaderCallback<E>{
        void onDataLoaded(E data,BaseService service);
    }
    public interface LoaderListCallback<E> {
        void onListDataLoaded(ArrayList<E> list,BaseService service);
    }

    public interface LoaderNodeListCallback<E> {
        void onNodeListDataLoaded(ArrayList<E> list,BaseService service);
    }
    public final static String Tag_TopicList_Req = "Tag_TopicList_Req";
    public final static String Tag_TopicDetail_Req = "Tag_TopicDetail_Req";
    public final static String Tag_NodeList_Req = "Tag_NodeList_Req";
    public final static String Topic_Key = "Topic_Key";
    public final static String Topic_List_Key = "Topic_List_Key";
    public final static String Service_key = "Service_Key";

    public abstract String getForumName();
    public abstract int getForumLogoRes();


    public abstract void getHotTopics(LoaderListCallback<Topic> callback);
    public abstract void getLatestTopics(LoaderListCallback<Topic> callback);
    public abstract void getNodeTopics(int node_id,LoaderListCallback<Topic> callback);

    public abstract void getNodes(LoaderNodeListCallback<Node> callback);
    public abstract void getTopicDetail(Topic topic, LoaderCallback<TopicDetail> callback);

    protected abstract void getTopics(String url,final LoaderListCallback<Topic> callback);
}
