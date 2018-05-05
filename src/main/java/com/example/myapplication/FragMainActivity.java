package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by TEARREAL on 2018/5/3.
 */

public class FragMainActivity extends AppCompatActivity {

    private RadioButton rd_first;
    private RadioButton rd_second;
    private RadioGroup rdgp_bottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        rdgp_bottom = (RadioGroup) findViewById(R.id.rdgp_bottom);

        rdgp_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rd_first:
                        changeFragment(new FirstFragment());
                        break;
                    case R.id.rd_second:
                        changeFragment(new SecondFragment());
                        break;
                }
            }
        });

    }

    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag, fragment);
        fragmentTransaction.commit();
    }
}
