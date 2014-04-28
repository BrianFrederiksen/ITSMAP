package dk.iha.itsmap.grp11662.handin2.app;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class ServiceAlarm extends Service {
    Context context = this;
    AlarmManager alarm = (AlarmManager)context.getSystemService(ALARM_SERVICE);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        PendingIntent pIntent = PendingIntent.getService(context,PendingIntent.FLAG_ONE_SHOT,intent,PendingIntent.FLAG_ONE_SHOT);
        Bundle btime = intent.getExtras();
        long time = btime.getLong("alarmtime");
        alarm.set(AlarmManager.ELAPSED_REALTIME, time ,pIntent);
        intent.setAction("dk.iha.itsmap.grp11662.handin2.app.AlarmBroadcastReceiver");
        sendBroadcast(intent);
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
