package dk.iha.itsmap.grp11662.handin2.app;

import android.app.Activity;
import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        setupButton((Button) findViewById(R.id.button),(EditText) findViewById(R.id.editText));
        Log.i(STATE_TAG, "onCreate");
    }

    private void setupButton(Button button, final EditText editText) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(BUTTON_TAG, "OnClick, start ServiceAlarm");
                Intent serviceIntent = new Intent();
                serviceIntent.setAction("dk.iha.itsmap.grp11662.handin2.app.ServiceAlarm");
                serviceIntent.putExtra("alarmtime", editText.getText());
                serviceIntent.putExtra("notification","Notification data from ActivityOne");
                startService(serviceIntent);
            }
        });
    }
}
