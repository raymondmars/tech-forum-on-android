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
 * Created by robot on 5/14/14.
 */
public class RubyChinaService extends BaseService {

    public final static RubyChinaService instance = new RubyChinaService();

    @Override
    public String getForumName() {
        return "RubyChina";
    }

    @Override
    public int getForumLogoRes() {
        return R.drawable.ic_rubychina;
    }


    @Override
    protected void getTopics(String url,final LoaderListCallback<Topic> callback) {
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
                                tp.setReplyCount(json_data.getInt("replies_count"));

                                Node nd = new Node();
                                nd.setId(json_data.getInt("node_id"));
                                nd.setName(json_data.getString("node_name"));
                                nd.setTopicCount(0);
                                tp.setBelongNode(nd);

                                User creator = new User();
                                JSONObject json_user = json_data.getJSONObject("user");
                                creator.setId(json_user.getInt("id"));
                                creator.setUsername(json_user.getString("login"));
                                String avatar_url = json_user.getString("avatar_url");
                                if(!Pattern.compile("^https").matcher(avatar_url).find()) {
                                    avatar_url = "http:" + avatar_url;
                                }
                                creator.setAvatarImage(avatar_url);
                                tp.setCreateUser(creator);

                                if(!json_data.isNull("replied_at")) {
                                    tp.setLastReplyTime(json_data.getString("replied_at"));
                                }

                                if(!json_data.isNull("last_reply_user_id")) {
                                    User last_reply_user = new User();
                                    last_reply_user.setId(json_data.getInt("last_reply_user_id"));
                                    last_reply_user.setUsername(json_data.getString("last_reply_user_login"));
                                    tp.setLastReplyUser(last_reply_user);
                                }


                                list.add(tp);
                            }


                            callback.onListDataLoaded(list,RubyChinaService.instance);

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

    @Override
    public void getHotTopics(LoaderListCallback<Topic> callback) {
        this.getTopics("https://ruby-china.org/api/topics.json",callback);
    }

    @Override
    public void getLatestTopics(LoaderListCallback<Topic> callback) {
        this.getTopics("https://ruby-china.org/api/topics.json",callback);
    }

    @Override
    public void getNodeTopics(int node_id, LoaderListCallback<Topic> callback) {
        if(node_id>0) {
            this.getTopics("https://ruby-china.org/api/topics/node/" + String.valueOf(node_id) + ".json", callback);
        } else {
            this.getLatestTopics(callback);
        }

    }

    @Override
    public void getNodes(final LoaderNodeListCallback<Node> callback) {
        String url = "https://ruby-china.org/api/nodes.json";
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
                                nd.setName(json_data.getString("name"));
                                nd.setTopicCount(json_data.getInt("topics_count"));
                                list.add(nd);
                            }

                            callback.onNodeListDataLoaded(list,RubyChinaService.instance);

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
        String target_url = "https://ruby-china.org/api/topics/" + String.valueOf(topic.getId()) +".json";
        JsonUTF8ObjectRequest req = new JsonUTF8ObjectRequest(target_url,null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)  {
                    try {
                        TopicDetail detail = new TopicDetail();

                        JSONObject json_data = new JSONObject(response.toString());
                        topic.setContent(json_data.getString("body_html"));


//                        Topic tp = new Topic();
//                        tp.setId(json_data.getInt("id"));
//                        tp.setTitle(json_data.getString("title"));
//                        tp.setReplyCount(json_data.getInt("replies_count"));
//                        tp.setContent(json_data.getString("body_html"));
//
//                        Node nd = new Node();
//                        nd.setId(json_data.getInt("node_id"));
//                        nd.setName(json_data.getString("node_name"));
//                        nd.setTopicCount(0);
//                        tp.setBelongNode(nd);
//
//                        User creator = new User();
//                        JSONObject json_user = json_data.getJSONObject("user");
//                        creator.setId(json_user.getInt("id"));
//                        creator.setUsername(json_user.getString("login"));
//                        creator.setAvatarImage(build_avatar_image_url(json_user.getString("avatar_url")));
//
//                        tp.setCreateUser(creator);
//
//                        if(!json_data.isNull("replied_at")) {
//                            tp.setLastReplyTime(json_data.getString("replied_at"));
//                        }
//
//                        if(!json_data.isNull("last_reply_user_id")) {
//                            User last_reply_user = new User();
//                            last_reply_user.setId(json_data.getInt("last_reply_user_id"));
//                            last_reply_user.setUsername(json_data.getString("last_reply_user_login"));
//                            tp.setLastReplyUser(last_reply_user);
//                        }

                        detail.setTopic(topic);

                        ArrayList<Reply> replies = new ArrayList<Reply>();
                        JSONArray array = json_data.getJSONArray("replies");
                        for(int i=0;i < array.length();i++) {
                            JSONObject rp = array.getJSONObject(i);
                            Reply reply = new Reply();
                            reply.setId(rp.getInt("id"));
                            reply.setContent(rp.getString("body_html"));
                            reply.setLikeCount(0);
                            reply.setReplyTime(rp.getString("created_at"));

                            JSONObject user_json = rp.getJSONObject("user");
                            User u = new User();
                            u.setId(user_json.getInt("id"));
                            u.setUsername(user_json.getString("login"));

                            u.setAvatarImage(build_avatar_image_url(user_json.getString("avatar_url")));


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

    protected String build_avatar_image_url(String url) {
        if(!Pattern.compile("^https").matcher(url).find()) {
            return "http:" + url;
        } else {
            return url;
        }
    }
}
