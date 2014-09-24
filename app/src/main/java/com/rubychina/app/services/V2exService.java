package com.rubychina.app.services;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.rubychina.app.activities.R;
import com.rubychina.app.entities.Node;
import com.rubychina.app.entities.Reply;
import com.rubychina.app.entities.Topic;
import com.rubychina.app.entities.TopicDetail;
import com.rubychina.app.entities.User;
import com.rubychina.app.exlibs.JsonUTF8ArrayRequest;
import com.rubychina.app.exlibs.JsonUTF8ObjectRequest;
import com.rubychina.app.util.TopApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by raymond on 9/17/14.
 */
public class V2exService extends BaseService {

    public final static V2exService instance = new V2exService();

    @Override
    public String getForumName() {
        return "V2EX";
    }

    @Override
    public int getForumLogoRes() {
        return R.drawable.ic_v2ex;
    }

//    @Override
//    public String getTopicApi(int id) {
//        return "http://www.v2ex.com/api/topics/show.json?id=" + String.valueOf(id);
//    }
//
//    @Override
//    public String getTopicListApi() {
//        return "http://www.v2ex.com/api/topics/latest.json";
//    }


    @Override
    public void getHotTopics(LoaderListCallback<Topic> callback) {
        this.getTopics("https://www.v2ex.com/api/topics/hot.json",callback);
    }

    @Override
    public void getLatestTopics(LoaderListCallback<Topic> callback) {
        this.getTopics("http://www.v2ex.com/api/topics/latest.json",callback);
        //this.getTopics("http://www.v2ex.com/api/topics/hot.json",callback);
    }

    @Override
    public void getNodeTopics(int node_id, LoaderListCallback<Topic> callback) {
        if(node_id > 0) {
            this.getTopics("http://www.v2ex.com/api/topics/show.json?node_id=" + String.valueOf(node_id), callback);
        } else {
            this.getLatestTopics(callback);
        }
    }

    @Override
    public void getNodes(final LoaderNodeListCallback<Node> callback) {
        String url = "http://www.v2ex.com/api/nodes/all.json";
        JsonUTF8ArrayRequest req = new JsonUTF8ArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONArray array = new JSONArray(response.toString());
                            ArrayList<Node> list = new ArrayList<Node>();
                            Node latest_node = new Node();
                            latest_node.setId(-1);
                            latest_node.setName("最新帖子");
                            list.add(latest_node);

                            for(int i=0;i<array.length();i++) {
                                JSONObject json_data = array.getJSONObject(i);
                                Node nd = new Node();
                                nd.setId(json_data.getInt("id"));
                                nd.setName(json_data.getString("title"));
                                nd.setTopicCount(json_data.getInt("topics"));
                                list.add(nd);
                            }

                            callback.onNodeListDataLoaded(list,V2exService.instance);

                        } catch (JSONException ex) {
                            Log.v(ex.getMessage(), "JSON Error.");
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TOPIC", "Error: " + error.getMessage());
            }
        }
        );
        TopApp.getInstance().addToRequestQueue(req, BaseService.Tag_NodeList_Req);
    }

    @Override
    public void getTopicDetail(final Topic topic, final LoaderCallback<TopicDetail> callback) {

        String replies_url = "http://www.v2ex.com/api/replies/show.json?topic_id=" + String.valueOf(topic.getId());

        JsonUTF8ArrayRequest req = new JsonUTF8ArrayRequest(replies_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            TopicDetail detail = new TopicDetail();
                            detail.setTopic(topic);

                            ArrayList<Reply> replies = new ArrayList<Reply>();
                            JSONArray array = new JSONArray(response.toString());
                            for(int i=0;i < array.length();i++) {
                                JSONObject rp = array.getJSONObject(i);
                                Reply reply = new Reply();
                                reply.setId(rp.getInt("id"));
                                reply.setContent(rp.getString("content_rendered"));
                                reply.setLikeCount(rp.getInt("thanks"));
                                reply.setReplyTime(rp.getString("created"));

                                JSONObject user_json = rp.getJSONObject("member");
                                User u = new User();
                                u.setId(user_json.getInt("id"));
                                u.setUsername(user_json.getString("username"));
                                u.setAvatarImage(build_avatar_image_url(user_json.getString("avatar_normal")));

                                reply.setReplyUser(u);

                                replies.add(reply);
                            }
                            detail.setReplyList(replies);

                            callback.onDataLoaded(detail,RubyChinaService.instance);


                        } catch (JSONException ex) {
                            Log.v(ex.getMessage(), "JSON Error.");
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TOPIC", "Error: " + error.getMessage());

            }
        }
        );
        TopApp.getInstance().addToRequestQueue(req, BaseService.Tag_TopicDetail_Req);
    }

    @Override
    protected void getTopics(String url, final LoaderListCallback<Topic> callback) {
        JsonUTF8ArrayRequest req = new JsonUTF8ArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONArray array = new JSONArray(response.toString());
                            ArrayList<Topic> list = new ArrayList<Topic>();
                            for(int i=0;i<array.length();i++) {
                                JSONObject json_data = array.getJSONObject(i);
                                Topic tp = new Topic();
                                tp.setId(json_data.getInt("id"));
                                tp.setTitle(json_data.getString("title"));
                                tp.setReplyCount(json_data.getInt("replies"));
                                tp.setContent(json_data.getString("content_rendered"));

                                Node nd = new Node();
                                JSONObject n_s = json_data.getJSONObject("node");

                                nd.setId(n_s.getInt("id"));
                                nd.setName(n_s.getString("title"));
                                nd.setTopicCount(n_s.getInt("topics"));
                                tp.setBelongNode(nd);

                                User creator = new User();
                                JSONObject json_user = json_data.getJSONObject("member");
                                creator.setId(json_user.getInt("id"));
                                creator.setUsername(json_user.getString("username"));
                                String avatar_url = json_user.getString("avatar_normal");
                                if(!Pattern.compile("^http").matcher(avatar_url).find()) {
                                    avatar_url = "http:" + avatar_url;
                                }
                                creator.setAvatarImage(avatar_url);
                                tp.setCreateUser(creator);

                                if(!json_data.isNull("last_touched")) {
                                    tp.setLastReplyTime(json_data.getString("last_touched"));
                                }

//                                if(!json_data.isNull("last_reply_user_id")) {
//                                    User last_reply_user = new User();
//                                    last_reply_user.setId(json_data.getInt("last_reply_user_id"));
//                                    last_reply_user.setUsername(json_data.getString("last_reply_user_login"));
//                                    tp.setLastReplyUser(last_reply_user);
//                                }


                                list.add(tp);
                            }


                            callback.onListDataLoaded(list,V2exService.instance);

                        } catch (JSONException ex) {
                            Log.v(ex.getMessage(), "JSON Error.");
                        }



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
    protected String build_avatar_image_url(String url) {
        if(!Pattern.compile("^http").matcher(url).find()) {
            return "http:" + url;
        } else {
            return url;
        }
    }
}
