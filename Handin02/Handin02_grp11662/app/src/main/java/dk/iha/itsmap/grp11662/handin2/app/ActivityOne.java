package dk.iha.itsmap.grp11662.handin2.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ActivityOne extends Activity {
    private final String STATE_TAG = "State changed ";
    private final String BUTTON_TAG = "Button ";
    public static final String COUNTDOWN_NOTIFICATION = "dk.iha.itsmap.grp11662.handin2.app.ActivityOne.CountdownNotification";

    BroadcastReceiver countdownReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            EditText editText = (EditText) ActivityOne.this.findViewById(R.id.editText);
            editText.setText((CharSequence) intent.getExtras().get("countdown"));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        setupButton((Button) findViewById(R.id.button),(EditText) findViewById(R.id.editText));

        Log.i(STATE_TAG, "onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(STATE_TAG, "onResume");

        registerReceiver(countdownReceiver,new IntentFilter(COUNTDOWN_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(STATE_TAG,"onPause");

        unregisterReceiver(countdownReceiver);
    }

    private void setupButton(final Button button, final EditText editText) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(BUTTON_TAG, "OnClick, start ServiceAlarm");
                Intent serviceIntent = new Intent();
                serviceIntent.setAction("dk.iha.itsmap.grp11662.handin2.app.ServiceAlarm");
                serviceIntent.putExtra("alarmtime", editText.getText());
                serviceIntent.putExtra("notification","Notification data from ActivityOne");
                startService(serviceIntent);

                //Disable UI interaction
                button.setClickable(false);
                button.setActivated(false);
                editText.setFocusable(false);
            }
        });
    }
}
