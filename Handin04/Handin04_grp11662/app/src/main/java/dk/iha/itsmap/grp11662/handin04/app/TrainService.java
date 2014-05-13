package dk.iha.itsmap.grp11662.handin04.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TrainService extends Service {
    public TrainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
