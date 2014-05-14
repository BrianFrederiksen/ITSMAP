package dk.iha.itsmap.grp11662.handin04.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class TrainService extends Service {
    public TrainService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("State changed", "TrainService: onStartCommand()");
        Toast.makeText(getApplicationContext(), "TOASTY", Toast.LENGTH_LONG).show();

        try {
            URL urlRequest = new URL("http://stog.itog.dk/itog/action/list/format/json");
            HttpURLConnection urlConnection = (HttpURLConnection) urlRequest.openConnection();
            //InputStream in = new BufferedInputStream(urlConnection.getInputStream());

          /* Log hvad der står i InputStream...
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            Log.i("Recieved from URL", String.valueOf(total));*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return super.onStartCommand(intent, flags, startId);
    }
//public List<togname> GetTrainList(){

    //Hent data fra http://stog.itog.dk/itog/action/list/format/json
    //Deserialize dataen og henter navnet ud
    //send TogName<string>

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
