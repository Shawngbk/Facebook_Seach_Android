package com.example.apple.hw9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 17/4/24.
 */

public class MyAlbumSimpleAdapter extends BaseExpandableListAdapter {
    Context context;
    List<String> listTitle;
    HashMap<String, List<String>> listTopic;

    public MyAlbumSimpleAdapter(Context context, List<String> listTitle, HashMap<String, List<String>> listTopic) {
        this.context = context;
        this.listTitle = listTitle;
        this.listTopic = listTopic;
    }



    @Override
    public int getGroupCount() {
        return listTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listTopic.get(listTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listTopic.get(listTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String lang = (String) getGroup(groupPosition);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_parent, null);
        }
        TextView txtParent = (TextView) convertView.findViewById(R.id.txtParent);
        txtParent.setText(lang);
        //System.out.println("test listtopic++++::::" + listTopic);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String topic = (String)getChild(groupPosition, childPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child, null);
        }
        //final List dataSet = listTopic.get(childPosition);
        //System.out.println("test dataset++++::::" + topic);
        ImageView thumbnailImage = (ImageView) convertView.findViewById(R.id.first_child);
        String imageUrl = topic;
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(imageUrl, thumbnailImage);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
