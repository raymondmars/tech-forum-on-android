package com.rubychina.app.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rubychina.app.activities.R;
import com.rubychina.app.entities.Node;

import java.util.ArrayList;

/**
 * Created by raymond on 9/23/14.
 */
public class NodeAdapter extends CommonListAdapter<Node> {
    public NodeAdapter(Activity a,ArrayList<Node> d) {
        super(a,d);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(convertView == null) {
            vi = inflater.inflate(R.layout.node_drawer_item, null);
        }
        if(data.size() > 0) {

            Node node = (Node) getItem(position);
            ((TextView) vi.findViewById(R.id.txt_node_name)).setText(node.getName());
//            ((TextView) vi.findViewById(R.id.txt_node_topicCount)).setText(String.valueOf(node.getTopicCount()));


        }

        return vi;

    }
}
