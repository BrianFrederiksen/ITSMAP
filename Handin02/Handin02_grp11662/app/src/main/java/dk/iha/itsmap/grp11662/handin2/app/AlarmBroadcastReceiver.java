package dk.iha.itsmap.grp11662.handin2.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.protocol.HTTP;

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
