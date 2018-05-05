package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TEARREAL on 2018/5/3.
 */

public class FirstFragment extends Fragment {

    private ListView list_item;
    private LinearLayout l;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        String[] presidents={"北京","深圳","济南","广州","海南","香港","澳门"};

        FragmentManager fragmentManager = getFragmentManager();


        View view = inflater.inflate(R.layout.fragment_first, container, false);
        view.setBackgroundColor(0xff00ff00);
        view.findViewById(R.id.lay_frag1);
        return view;
    }

    class DataAdapter extends ArrayAdapter{
        public DataAdapter(Context context, int textViewResourceId, List<String> objects){
            super(context, textViewResourceId, objects);
        }
    }
}
