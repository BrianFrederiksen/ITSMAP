package dk.iha.itsmap.grp11662.handin2.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class ActivityTwo extends Activity {

    private final String TAG = "State Changed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        Log.i(TAG,"Running method onCreate");
        int ToastDuration = Toast.LENGTH_SHORT;

        Log.i(TAG,"Getting application context");
        Context context = getApplicationContext();

        Log.i(TAG,"Getting intent extras");
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            Log.i(TAG,"value of extras was null - returning");
            return;
        }

        String ToastToDisplay = extras.getString("notification");
        if(ToastToDisplay != null)
        {
            Log.i(TAG,"Creating Toast");
            Toast toast = Toast.makeText(context,ToastToDisplay,ToastDuration);
            toast.show();
        }







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_two, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
