package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

/**
 * Created by TEARREAL on 2018/4/30.
 * chronometer控件实现秒表
 */

public class TimeActivity extends Activity {

    private Chronometer chronometer;
    private Button btn_timebegin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        chronometer = (Chronometer) findViewById(R.id.timer_show);
        btn_timebegin = (Button) findViewById(R.id.btn_timebegin);

        btn_timebegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
            }
        });

    }
}
