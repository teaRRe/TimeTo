package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TEARREAL on 2018/5/3.
 */

public class ItemAdapter extends ArrayAdapter {

    private int resourceId;

    public ItemAdapter( Context context,  int resource, List objects){
        super(context, resource, objects);
        resourceId = resource;

    }
    public View getView(int position, View convertView, ViewGroup parent){
        List list = new ArrayList();
        for (int i = 0; i < 10; i ++){
            String s = "this is " + i + " position";
            list.add(s);
        }
        View view  = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);

        return view;
    }
}
