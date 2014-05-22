package com.rubychina.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rubychina.app.entities.Reply;

import java.util.ArrayList;

/**
 * Created by robot on 5/19/14.
 */
public abstract class CommonListAdapter<T> extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<T> data;
    protected static LayoutInflater inflater=null;

    public CommonListAdapter(Activity a,ArrayList<T> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return data.get(position);
    }

    public abstract long getItemId(int position) ;

    public abstract View getView(int position, View convertView, ViewGroup parent);
}
