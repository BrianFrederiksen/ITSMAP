package dk.iha.itsmap.grp11662.handin04.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TrainService extends Service {

    public TrainService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("State changed", "TrainService: onStartCommand()");
        Toast.makeText(getApplicationContext(), "TOASTY", Toast.LENGTH_LONG).show();

        //RestAccess restAccess = new RestAccess(this);
        //restAccess.getTrainStations().execute("http://stog.itog.dk/itog/action/list/format/json");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
