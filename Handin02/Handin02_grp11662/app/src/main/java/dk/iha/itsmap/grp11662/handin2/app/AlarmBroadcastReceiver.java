package dk.iha.itsmap.grp11662.handin2.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.protocol.HTTP;

import static android.app.PendingIntent.*;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = "State changed:";
    private final String RECIEVE_NOTIFICATION = "Receiving notification from service";
    private final String SETUP_INTENT = "Setting up intent";
    private final String START_NEW_ACTIVITY = "Starting ActivityTwo";
    private final String INTENT_MESSAGE = "notfication";

    public AlarmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,RECIEVE_NOTIFICATION);

        Bundle extras = intent.getExtras();

        if(extras != null)
        {
            if(extras.containsKey("notification"))
            {
                //Kill countdown alarm
                Intent countdownIntent = new Intent(ActivityOne.COUNTDOWN_NOTIFICATION);
                PendingIntent countdownPendingIntent = getBroadcast(context, FLAG_UPDATE_CURRENT, countdownIntent, FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                alarmManager.cancel(countdownPendingIntent);

                //Get app context
                Context appContext = context.getApplicationContext();

                Log.i(TAG,SETUP_INTENT);
                //Create and setup intent used to start ActivityTwo
                Intent startActivityTwoIntent = new Intent();
                startActivityTwoIntent.setAction(Intent.ACTION_SEND);
                startActivityTwoIntent.putExtra("notification",extras.getString("notification"));
                startActivityTwoIntent.setType(HTTP.PLAIN_TEXT_TYPE);
                startActivityTwoIntent.setClassName(appContext, "dk.iha.itsmap.grp11662.handin2.app.ActivityTwo");
                startActivityTwoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Log.i(TAG,START_NEW_ACTIVITY);
                //Start new activity
                appContext.startActivity(startActivityTwoIntent);
            }
        }

    }
}
