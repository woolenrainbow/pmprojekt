package com.example.myapplication;

import android.app.*;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.util.*;

public class TaskMonitor extends Service {
    Timer timer;
    Timer timer2;
    List<Integer> already_used = new ArrayList<Integer>();

    public TaskMonitor() {
    }

    public class MyTimerTask extends TimerTask {
        private Context context;
        public MyTimerTask(Context pContext) {
            this.context = pContext;
        }
        @Override
        public void run() {
            updateUI.sendEmptyMessage(0);
        }
        private Handler updateUI;

        {
            updateUI = new Handler() {
                @Override
                public void dispatchMessage(Message msg) {
                    List<Task> lista = new ArrayList<Task>();
                    DataBase dataBase = new DataBase(getApplicationContext());
                    TaskDAO task_db = new TaskDAO(dataBase.getWritableDatabase());
                    lista = task_db.getAll();
                    Task incoming = null;
                    Iterator<Task> iter = lista.iterator();
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                    while (iter.hasNext()) {
                        Task t = iter.next();
                        if (t.day == day && already_used.indexOf(t.id) == -1)  {
                            int t1h = (int) (t.start.getHours());
                            int t1m = (int) (t.start.getMinutes());
                            int t3h = (int) (t.finish.getHours());
                            int t3m = (int) (t.finish.getMinutes());
                            Date date = new Date(System.currentTimeMillis());
                            int t2m = (int) (date.getMinutes());
                            int t2h = (int) (date.getHours());
                            if ((t1h < t2h && t2h < t3h) || (t1h == t2h && t2m > t1m && t2h < t3h) || (t3h == t2h && t2m < t3m && t2h > t1h) || (t1h == t2h && t3h == t2h && t2m > t1m && t2m < t3m)) {
                                incoming = t;
                                final NotificationManager mNotifyManager;
                                final NotificationCompat.Builder mBuilder;
                                final int min_number = (t3h - t1h) * 60 + (t3m - t1m);
                                final int curr_minute = (t2h - t1h) * 60 + (t2m - t1m);
                                mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                mBuilder = new NotificationCompat.Builder(TaskMonitor.this);
                                mBuilder.setContentTitle("Plan zajęć")
                                        .setSmallIcon(R.drawable.ic_stat_name)
                                        .setContentText(t.name);
                                final int id = t.id;
                                mNotifyManager.notify(id, mBuilder.build());
                                new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                int incr;
                                                for (incr = curr_minute * 6; incr <= min_number * 6; incr+=1) {
                                                    mBuilder.setProgress(min_number * 6, incr, false);
                                                    mNotifyManager.notify(id, mBuilder.build());
                                                    try {
                                                        Thread.sleep(10000);
                                                    } catch (InterruptedException e) {
                                                        Log.d("TAG", "sleep failure");
                                                    }
                                                }
                                                mNotifyManager.cancel(id);
                                                already_used.remove(already_used.indexOf(id));
                                            }
                                        }
                                ).start();
                                //TaskMonitor.this.timer2.cancel();
                                //TaskMonitor.this.timer2.purge();
                                //break;
                                already_used.add(t.id);
                            }
                        }
                    }
                }
            };
        }
    }

    private class MyTimerStart extends TimerTask {

        private Context context;
        public MyTimerStart(Context pContext) {
            this.context = pContext;
        }
        @Override
        public void run() {
            updateUI.sendEmptyMessage(0);
        }
        private Handler updateUI;

        {
            updateUI = new Handler() {
                int first_entry = 1;
                @Override
                public void dispatchMessage(Message msg) {
                    Calendar calendar;
                    if (this.first_entry == 1){
                        super.dispatchMessage(msg);
                        List<Task> lista = new ArrayList<Task>();
                        DataBase dataBase = new DataBase(getApplicationContext());
                        TaskDAO task_db = new TaskDAO(dataBase.getWritableDatabase());
                        lista = task_db.getAll();
                        Task incoming = null;
                        Iterator<Task> iter = lista.iterator();
                        calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                        while (iter.hasNext()) {
                            Task t = iter.next();
                            if (t.day == day && already_used.indexOf(t.id) == -1) {
                                int t1h = (int) (t.start.getHours());
                                int t1m = (int) (t.start.getMinutes());
                                int t3h = (int) (t.finish.getHours());
                                int t3m = (int) (t.finish.getMinutes());
                                Date date = new Date(System.currentTimeMillis());
                                int t2m = (int) (date.getMinutes());
                                int t2h = (int) (date.getHours());
                                if ((t1h < t2h && t2h < t3h) || (t1h == t2h && t2m > t1m && t2h < t3h) || (t3h == t2h && t2m < t3m && t2h > t1h) || (t1h == t2h && t3h == t2h && t2m > t1m && t2m < t3m)) {
                                    incoming = t;
                                    final NotificationManager mNotifyManager;
                                    final NotificationCompat.Builder mBuilder;
                                    final int min_number = (t3h - t1h) * 60 + (t3m - t1m);
                                    final int curr_minute = (t2h - t1h) * 60 + (t2m - t1m);
                                    mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    mBuilder = new NotificationCompat.Builder(TaskMonitor.this);
                                    mBuilder.setContentTitle("Plan zajęć")
                                            .setSmallIcon(R.drawable.ic_stat_name)
                                            .setContentText(t.name);
                                    final int id = t.id;
                                    mNotifyManager.notify(id, mBuilder.build());
                                    new Thread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    int incr;
                                                    for (incr = curr_minute * 6; incr <= min_number * 6; incr += 1) {
                                                        mBuilder.setProgress(min_number * 6, incr, false);
                                                        mNotifyManager.notify(id, mBuilder.build());
                                                        try {
                                                            Thread.sleep(10000);
                                                        } catch (InterruptedException e) {
                                                            Log.d("TAG", "sleep failure");
                                                        }
                                                    }
                                                    mNotifyManager.cancel(id);
                                                    already_used.remove(already_used.indexOf(id));
                                                }
                                            }
                                    ).start();
                                    already_used.add(t.id);
                                }
                            }
                        }
                    }
                    this.first_entry = 0;
                    calendar = Calendar.getInstance();
                    calendar.get(Calendar.SECOND);
                    if (calendar.get(Calendar.SECOND) == 0) {
                        TimerTask timerTask = new MyTimerTask(getApplicationContext());
                        TaskMonitor.this.timer2 = new Timer(true);
                        TaskMonitor.this.timer2.schedule(timerTask, 0,60000);
                        TaskMonitor.this.timer.cancel();
                        TaskMonitor.this.timer.purge();
                    }
                }
            };
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TimerTask timerTask = new MyTimerStart(getApplicationContext());
        this.timer = new Timer(true);
        this.timer.schedule(timerTask, 0, 1000);
        return START_NOT_STICKY;
    }
}
