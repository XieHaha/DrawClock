package yxh.dundun.customclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import yxh.dundun.customclock.view.CustomView;

public class MainActivity extends BaseActivity {

    private LinearLayout layout;
    private CustomView view;
    private TextView time, week, year;
    private TickTimeReceiver receiver;
    private IntentFilter filter;
    private Timer timer;

    private String dates = "";
    private String[] date;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    getCurrentTime();
                    view.initData(date[0]);
                    break;
                case 1:
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filter.addAction("android.intent.action.TIME_TICK");
        registerReceiver(receiver, filter);

        initTime();
        getCurrentTime();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        layout = (LinearLayout) findViewById(R.id.paint2_activity);
        view = (CustomView) findViewById(R.id.paint2_activity_view);
        time = (TextView) findViewById(R.id.paint2_activity_time);
        week = (TextView) findViewById(R.id.paint2_activity_week);
        year = (TextView) findViewById(R.id.paint2_activity_date);
    }

    @Override
    protected void initClass() {
        receiver = new TickTimeReceiver();
        filter = new IntentFilter();
        timer = new Timer();
    }

    /**
     * 初始化表盘时间
     */
    private void initTime() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, 1000);
    }

    /**
     * 获取系统当前时间
     */
    private void getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss_EEEE_yyyy年MM月dd日");
        dates = format.format(new Date((System.currentTimeMillis())));
        date = dates.split("_");
        time.setText(date[0]);
        week.setText(date[1]);
        year.setText(date[2]);
    }

    class TickTimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Intent.ACTION_TIME_TICK:
                    //接收时间改变广播，一分钟回调一次
//                    handler.sendEmptyMessage(1);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
