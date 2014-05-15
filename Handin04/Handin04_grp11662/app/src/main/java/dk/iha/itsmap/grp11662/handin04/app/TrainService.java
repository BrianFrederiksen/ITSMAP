package dk.iha.itsmap.grp11662.handin04.app;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TrainService extends Service {

    private final IBinder myBinder = new TrainServiceBinder();

    public TrainService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("State changed", "TrainService: onStartCommand()");
        Toast.makeText(getApplicationContext(), "TOASTY", Toast.LENGTH_LONG).show();
        //RestAccess restAccess = new RestAccess(intent.getAction());
        //restAccess.getTrainStations().execute("http://stog.itog.dk/itog/action/list/format/json");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return myBinder;
    }

    public class TrainServiceBinder extends Binder {
        TrainService getService() {
            return TrainService.this;
        }
    }
}
