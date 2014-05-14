package dk.iha.itsmap.grp11662.handin04.app;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TrainStations extends AsyncTask<String, Void, String> {

    private RestAccess restAccessInstance;

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static String GET(String url){
        Log.i("Function started", "String GET(String url)");
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    public TrainStations(RestAccess restAccess) {
        Log.i("Function started", "TrainStations(RestAccess restAccess)");
        if(restAccessInstance == null){
            restAccessInstance = restAccess;}
    }

    @Override
    protected String doInBackground(String... strings) {
        return GET(strings[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Log.i("Function started", "void onPostExecute(String result)");
        Toast.makeText(restAccessInstance.GetActivity().getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
        ArrayList<Train> trains = new ArrayList<Train>();


        try {
            JSONArray jsonArray = new JSONArray(result);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject curr = jsonArray.getJSONObject(i);
                Train train = new Train();
                train.setName(curr.getString("name"));
                train.setWid(curr.getString("wid"));
                train.setX(curr.getString("x"));
                train.setY(curr.getString("y"));

                trains.add(train);
            }

        } catch (JSONException e) {
            Log.d("Exception", e.getMessage());
        }

        ListView resultListView = (ListView) restAccessInstance.GetActivity().findViewById(R.id.listView);
        ArrayAdapter<Train> arrayAdapter = new ArrayAdapter<>(restAccessInstance.GetActivity(), android.R.layout.simple_list_item_1, trains);
        resultListView.setAdapter(arrayAdapter);
    }
}

