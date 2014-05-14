package dk.iha.itsmap.grp11662.handin04.app;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class RestAccess {

    private Activity ParentActivity;
    TrainStations trainStations;

    public TrainStations getTrainStations() {
        Log.i("Function started", "TrainStations getTrainStations(");
        if (trainStations == null)
            trainStations = new TrainStations(this);
        return trainStations;
    }

    public Activity GetActivity(){
        return ParentActivity;
    }

    public RestAccess(Activity activity) {
        Log.i("Function started", "RestAccess(Activity activity)");
        ParentActivity = activity;
        if (trainStations == null){
        trainStations= new TrainStations(this);
        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) ParentActivity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
