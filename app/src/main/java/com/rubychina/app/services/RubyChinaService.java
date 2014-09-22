package com.rubychina.app.services;

import com.rubychina.app.activities.R;
import com.rubychina.app.adapters.loader.BaseLoader;
import com.rubychina.app.adapters.loader.LoaderCallback;
import com.rubychina.app.adapters.loader.RubyChinaLoader;

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
    public String getTopicApi(int id) {
        return "https://ruby-china.org/api/topics/" + String.valueOf(id) + ".json";
    }

    @Override
    public String getTopicListApi() {
        return "https://ruby-china.org/api/topics.json";
    }

    @Override
    public BaseLoader create_loader(LoaderCallback callback) {
        return new RubyChinaLoader(callback);
    }
}
