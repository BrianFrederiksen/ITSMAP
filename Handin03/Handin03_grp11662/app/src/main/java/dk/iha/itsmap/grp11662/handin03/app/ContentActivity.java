package dk.iha.itsmap.grp11662.handin03.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class ContentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.content, menu);
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

     public void SetDetails(View v){
         AndroidVersion av = new AndroidVersion("kitkat","version 4.4","dumdum");
         EditText codeName = (EditText)findViewById(R.id.codeName);
         EditText version = (EditText)findViewById(R.id.version);
         EditText description = (EditText)findViewById(R.id.description);

         codeName.setText(av.getCodeName());
         version.setText(av.getVersion());
         description.setText(av.getDescription());
     }
}