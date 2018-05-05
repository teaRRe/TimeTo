package com.example.myapplication;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 4个Time+TimerTask+Handler实现秒表
 */
public class MainActivity extends AppCompatActivity {

    private TextView text_show;
    private TextView text_hour;
    private TextView text_minute;
    private TextView text_second;
    private TextView text_ms;

    private int second = 50;
    private int minute = 58;
    private int hour;
    private int ms;

    private Button btn_begin;
    private Button btn_pause;

    private int btnFlag;

    myHandler myHandler = new myHandler();
    secondHandler sHandler = new secondHandler();
    minuteHandler mHandler = new minuteHandler();
    hourHandler hHandler = new hourHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_show = (TextView) findViewById(R.id.text_show);
        text_minute = (TextView) findViewById(R.id.text_minute);
        text_second = (TextView) findViewById(R.id.text_second);
        text_ms = (TextView) findViewById(R.id.text_ms);
        btn_begin = (Button) findViewById(R.id.btn_begin);
        btn_pause = (Button) findViewById(R.id.btn_pause);


        Date testDate = new Date();
        long time = testDate.getTime() + 1000*10;
        testDate = new Date(time);


        final Timer Thour = new Timer();
        final Timer Tminute = new Timer();
        final Timer Tsecond = new Timer();
        final Timer Tms = new Timer();

        final ThreadMs tms = new ThreadMs();
        final Thread thMs = new Thread(tms);

        btnFlag = 0;
        btn_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnFlag == 0)
                    btnFlag =1;
                else
                    btnFlag=0;

                /**
                 * 小时
                 */
                try {
                    Thour.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            Bundle b = new Bundle();

                            if (btnFlag == 1){
                                hour++;
                                if (hour == 24)
                                    hour = 0;
                                b.putString("hour","" + hour);
                            }else if (btnFlag == 0){
                                b.putString("hour","n");
                                this.cancel();
                            }

                            msg.setData(b);
                            hHandler.sendMessage(msg);
                        }
                    },1000*60*60-1000*60*minute, 1000*60*60);
                }catch (Exception e){

                }

                /**
                 * 分钟
                 */
                try {
                    Tminute.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            Bundle b = new Bundle();

                            if (btnFlag == 1){
                                minute++;
                                if (minute == 60)
                                    minute = 0;
                                b.putString("minute","" + minute);
                            }else if (btnFlag == 0){
                                b.putString("minute","n");
                                this.cancel();
                            }

                            msg.setData(b);
                            mHandler.sendMessage(msg);
                        }
                    },1000*60 -1000*second , 1000*60);

                }catch (Exception e){

                }
                /**
                 * 秒
                 */
                try {
                    Tsecond.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            try {
                                Message msg = new Message();
                                Bundle b = new Bundle();

                                if (btnFlag == 1){
                                    second++;
                                    if (second == 60)
                                        second = 0;
                                    b.putString("second","" + second);
                                }else if (btnFlag == 0){
                                    b.putString("second","n");
                                    this.cancel();
                                }

                                msg.setData(b);
                                sHandler.sendMessage(msg);
                            }catch (Exception e){

                            }

                        }
                    }, 1000 - ms*10, 1000);

                }catch (Exception e){

                }

                /**
                 * 毫秒
                 */
                try {

                    Tms.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            Message msg = new Message();
                            Bundle b = new Bundle();

                            if (btnFlag == 1){
                                ms++;
                                if (ms == 100)
                                    ms = 0;
                                b.putString("ms","" + ms);
                            }else if (btnFlag == 0){
                                b.putString("ms","n");
                                this.cancel();
                            }

                            msg.setData(b);
                            myHandler.sendMessage(msg);
                        }
                    },  0 , 10);
                }catch (Exception e){

                }
            }
        });

        btn_begin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return false;
            }
        });

        //CountDown(10);
    }

    public void CountDown(int seconds){
        //开始时间
        long start = System.currentTimeMillis();
        //结束时间
        final long end = start + seconds * 1000;
        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i("Log", "-----");
            }
        }, 0, 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
            }
        }, new Date(end));
    }

    public void ms(){
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putString("ms","" + ms);

                msg.setData(b);



                ms++;
                if (ms == 999)
                    ms = 0;

            }
        }, 0 ,1);
    }

    /**
     * 毫秒 Handler
     */
    public class myHandler extends Handler{
        public myHandler(){
        }
        public myHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            Bundle bundle = msg.getData();

            String ms = bundle.getString("ms");
            if (!"n".equals(ms))
                if (ms.length()<2)
                    ms = "0"+ms;
            if (!"n".equals(ms))
                text_ms.setText(ms);

        }
    }

    /**
     * 秒 Handler
     */
    public class secondHandler extends Handler{
        public secondHandler(){
        }
        public secondHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle bundle = msg.getData();

            String second = bundle.getString("second");
            if (!"n".equals(second))
                if (second.length()<2)
                    second = "0"+second;
            if (!"n".equals(second))
                text_second.setText(second);
        }
    }

    /**
     * 分钟 Handler
     */
    public class minuteHandler extends Handler{
        public minuteHandler(){
        }
        public minuteHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String minute = bundle.getString("minute");
            if (!"n".equals(minute))
                if (minute.length()<2)
                    minute = "0"+minute;
            if (!"n".equals(minute))
                text_minute.setText(minute);
        }
    }

    /**
     * 小时 Handler
     */
    public class hourHandler extends Handler{
        public hourHandler(){
        }
        public hourHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String hour = bundle.getString("hour");
            if (!"n".equals(hour))
                if (hour.length()<2)
                    hour = "0"+hour;
            if (!"n".equals(hour))
                text_hour.setText(hour);
        }
    }
    public class ThreadMs implements Runnable{
        @Override
        public void run() {

            try {
                while (true){
/*                    Message msg = new Message();
                    Bundle b = new Bundle();

                    if (btnFlag == 1){

                        if (ms == 9)
                            ms = 0;

                        b.putString("ms","" + ms);
                        ms++;

                    }else if (btnFlag == 0){
                        b.putString("ms","n");
                        break;
                    }
                    msg.setData(b);
                    myHandler.sendMessage(msg);*/

/*                    ms++;
                    Log.i("Log", "msThread run---" + ms);
                    Thread.sleep(1000);
                    if (btnFlag == 1)
                        Thread.yield();*/

                }
            }catch (Exception e){}

        }

    }
}
