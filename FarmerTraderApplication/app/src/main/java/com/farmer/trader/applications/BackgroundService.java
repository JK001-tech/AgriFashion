package com.farmer.trader.applications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



public class BackgroundService extends Service {

    Timer timer;
    TimerTask timerTask;
    Handler handler = new Handler();
    SharedPreferences pref;
    String UserId, UserType;
    private Context mContext;

    boolean bValue = false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pref = getSharedPreferences("FarmerTrader", Context.MODE_PRIVATE);
        UserId = pref.getString("UserId", "");
        UserType = pref.getString("LoginType", "");

        timer = new Timer();
        demo();
        timer.schedule(timerTask, 0, 5000);

    }

    public void demo() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        UserId = pref.getString("UserId", "");
                        UserType = pref.getString("LoginType", "");
                        if (UserId.compareTo("") != 0) {

//                            if (!bValue) {
                            new getNotificationTask().execute(UserId);
//                            }

                        }
                    }
                });
            }
        };
    }


    public class getNotificationTask extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            bValue = true;
        }

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.GetNotifications(params[0]);
                JSONPARSE jp = new JSONPARSE();
                a = jp.parse(json);
            } catch (Exception e) {
                a = e.getMessage();
            }
            return a;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.compareTo("no") == 0) {

            } else if (s.compareTo("") == 0) {

            } else if (s.contains("*")) {

                String temp[] = s.split("\\#");
                for (int i = 0; i < temp.length; i++) {

                    String temp1[] = temp[i].split("\\*");
                    //nid*title*mesg
                    String Nid = temp1[0];
                    String Title = temp1[1];
                    String msg = temp1[2];

                    TaskDetailNotification(Nid, Title, msg);
                    new DeleteNotificationTask().execute(Nid);
                }

            }

//            bValue = false;

        }
    }


    public class DeleteNotificationTask extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            bValue = true;
        }

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.DeleteNotifications(params[0]);
                JSONPARSE jp = new JSONPARSE();
                a = jp.parse(json);
            } catch (Exception e) {
                a = e.getMessage();
            }
            return a;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.compareTo("") == 0) {

            } else if (s.compareTo("true") == 0) {

//                Toast.makeText(BackgroundService.this, "Notification Deleted", Toast.LENGTH_SHORT).show();

            }

//            bValue = false;

        }
    }


    public int getKey() {

        int ranArray[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random random = new Random();
        return ranArray[random.nextInt(ranArray.length)];
    }


    public void TaskDetailNotification(String Nid, String Title, String Msg) {

        Intent homeIntent = null;

        int randomValue = getKey() + getKey() + getKey() + getKey() + getKey();

        if (UserType.compareTo("Farmer") == 0) {

            homeIntent = new Intent(BackgroundService.this, MainActivity.class);
            homeIntent.putExtra("NotificationId", randomValue);
            homeIntent.putExtra("position", 2);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);



        } else if (UserType.compareTo("Retailer") == 0) {

            homeIntent = new Intent(BackgroundService.this, R_OrderList_Activity.class);
            homeIntent.putExtra("NotificationId", randomValue);

        } else if (UserType.compareTo("MahilaUdyog") == 0) {

            homeIntent = new Intent(BackgroundService.this, MainActivity.class);
            homeIntent.putExtra("NotificationId", randomValue);
            homeIntent.putExtra("position", 2);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }


        PendingIntent pendingFinishIntent = PendingIntent.getActivity(BackgroundService.this, randomValue, homeIntent, 0);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(BackgroundService.this);

        builder.setContentIntent(pendingFinishIntent)
                .setSmallIcon(R.drawable.notification_icon)
                .setTicker(Nid)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(Title);
        builder.setContentText(Msg);
        Notification n = builder.getNotification();
        nm.notify("cc", randomValue, n);
    }


}
