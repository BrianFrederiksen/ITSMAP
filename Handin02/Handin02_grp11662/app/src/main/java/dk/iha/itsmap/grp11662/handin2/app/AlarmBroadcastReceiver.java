package dk.iha.itsmap.grp11662.handin2.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = "State changed:";
    private final String RECIEVE_NOTIFICATION = "Receiving notification from service";

    public AlarmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,RECIEVE_NOTIFICATION);

        Context appContext = context.getApplicationContext();
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
