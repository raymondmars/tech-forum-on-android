package com.rubychina.app.services;

import com.rubychina.app.activities.R;
import com.rubychina.app.adapters.loader.BaseLoader;
import com.rubychina.app.adapters.loader.LoaderCallback;
import com.rubychina.app.adapters.loader.V2exLoader;

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

    @Override
    public String getTopicApi(int id) {
        return "http://www.v2ex.com/api/topics/show.json?id=" + String.valueOf(id);
    }

    @Override
    public String getTopicListApi() {
        return "http://www.v2ex.com/api/topics/latest.json";
    }

    @Override
    public BaseLoader create_loader(LoaderCallback callback) {
        return new V2exLoader(callback);
    }
}
