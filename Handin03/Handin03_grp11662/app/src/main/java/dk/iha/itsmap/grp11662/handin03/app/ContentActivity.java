package dk.iha.itsmap.grp11662.handin03.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class ContentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        if (savedInstanceState == null && getIntent().getExtras().containsKey("data")) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("data",(AndroidVersion) getIntent().getExtras().get("data"));
            ContentFragment contentFragment = new ContentFragment();
            contentFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.content_container,contentFragment).commit();
        }
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
}
