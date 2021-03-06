package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by TEARREAL on 2018/5/2.
 * 采用一个Timer+TimerTask+Handler实现秒表
 */

public class Time2Activity extends Activity {

    private TextView text_ms2;
    private TextView text_minute2;
    private TextView text_second2;
    private Button btn_cancel;
    private Button btn_reset;
    private ListView lst_mark;

    private int ms2;
    private int second2;
    private int minute2;

    countHandler cHandler = new countHandler();

    final Timer t = new Timer();
    TimerTask ts;

    int btnFlag;
    int btnFlag_Reset;
    List list = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time2);

        text_ms2 = (TextView) findViewById(R.id.text_ms2);
        text_second2 = (TextView) findViewById(R.id.text_second2);
        text_minute2 = (TextView) findViewById(R.id.text_minute2);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        lst_mark = (ListView) findViewById(R.id.lst_mark);



        //btn_reset.setEnabled(false);

        btnFlag = 1;
        btn_cancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:

                        if (btnFlag == 1){
                            ts = getTimerTask();
                            try {
                                ct(ts);
                            }catch (Exception e){}

                            btn_cancel.setText("Pause");
                            btnFlag = 0;
                            btnFlag_Reset = 1;
                            btn_reset.setText("Mark");

                            Log.i("Log","BtnFlag--- " + btnFlag);
                        }else {
                            ts.cancel();
                            btnFlag = 1;
                            btn_cancel.setText("Begin");
                            btn_reset.setText("Reset");
                            btnFlag_Reset = 0;
                            Log.i("Log","BtnFlag--- " + btnFlag);
                        }
                        if (!btn_reset.isEnabled())
                            //btn_reset.setEnabled(true);

                        break;
                }
                return false;
            }
        });


        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnFlag_Reset == 0){
                    ms2 = 0;
                    second2 = 0;
                    minute2 = 0;

                    text_ms2.setText("00");
                    text_second2.setText("00");
                    text_minute2.setText("00");

                    list.clear();
                }else{
                    String sb;
                    sb = minute2 + ":" + second2 + ":" + ms2;
                    list.add(sb);
                    ArrayAdapter adapter = new ArrayAdapter(Time2Activity.this, android.R.layout.simple_list_item_1, list);
                    lst_mark.setAdapter(adapter);
                }


                //btn_reset.setEnabled(false);
            }
        });
    }

    public void ct(TimerTask timerTask){

        t.schedule(timerTask , 0, 10);
    }

    class countHandler extends Handler{
        public countHandler(){}

        public countHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            Bundle bundle = msg.getData();
            String s_ms = bundle.getString("ms");
            String s_second = bundle.getString("second");
            String s_minute = bundle.getString("minute");

            text_ms2.setText(s_ms);
            text_second2.setText(s_second);
            text_minute2.setText(s_minute);

        }
    }
    public void CancelTimer(Timer t){
        t.cancel();
    }

    public TimerTask getTimerTask(){

            TimerTask ts = new TimerTask() {

                @Override
                public void run() {

                        try {

                            Message msg = new Message();
                            Bundle b = new Bundle();

                            ms2++;
                            if (ms2 == 100){
                                second2 ++;
                                ms2 = 0;
                                if (second2 == 60){
                                    minute2 ++;
                                    second2 = 0;
                                }
                            }

                            if (ms2 < 10) {
                                b.putString("ms", "0" + ms2);
                            }else {
                                b.putString("ms", "" + ms2); }
                            if (second2 < 10) {
                                b.putString("second", "0" + second2);
                            }else {
                                b.putString("second", "" + second2); }
                            if (minute2 < 10) {
                                b.putString("minute", "0" + minute2);
                            }else {
                                b.putString("minute", "" + minute2); }

                            msg.setData(b);
                            cHandler.sendMessage(msg);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                }
            };
            return ts;
        }


    public TimerTask getNullTimerTask(){
        TimerTask ts = new TimerTask() {
            @Override
            public void run() {

            }
        };
        return ts;
    }
}
