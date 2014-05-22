package com.rubychina.app.services;

import com.rubychina.app.entities.Topic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by raymond on 5/14/14.
 */
public class TopicService {
    public static final String Tag_TopicList_Req = "Tag_TopicList_Req";
    public static final String Tag_Topic_Req = "Tag_Topic_Req";

    public static final String Topic_List_Key = "Topic_List_key";

    public static String getTopicApi(int id) {
        return "http://ruby-china.org/api/topics/" + String.valueOf(id) + ".json";
    }
    public static String getTopicListApi() {
        return "http://ruby-china.org/api/topics.json";
    }


}
