package dk.iha.itsmap.grp11662.handin2.app;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class ServiceAlarm extends Service {
    Context context = this;
    AlarmManager alarm;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("State changed", "onStartCommand");

        alarm = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        Bundle btime = intent.getExtras();

        long time = Long.parseLong(btime.getString("alarmtime"));

        //Intents for broadcasting
        Intent countdownIntent = new Intent(ActivityOne.COUNTDOWN_NOTIFICATION);
        Intent alarmIntent = new Intent(this, AlarmBroadcastReceiver.class);
        alarmIntent.putExtra("notification",intent.getExtras().getString("notification"));

        //PendingIntents
        PendingIntent countdownPendingIntent = PendingIntent.getBroadcast(this, PendingIntent.FLAG_UPDATE_CURRENT, countdownIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this, PendingIntent.FLAG_ONE_SHOT, alarmIntent,PendingIntent.FLAG_ONE_SHOT);

        //Set alarms
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, 1000, countdownPendingIntent);
        alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time*1000), alarmPendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    public ServiceAlarm() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented, dont ya know?!");
    }
}
