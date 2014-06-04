package dk.iha.itsmap.grp11662.telecare.app.content;

import java.util.HashMap;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;

import dk.iha.itsmap.grp11662.telecare.app.database.TeleCareDataSource;
import dk.iha.itsmap.grp11662.telecare.app.database.TeleCareDbOpenHelper;

public class TeleCareContentProvider extends ContentProvider {


    // fields for content provider
    static final String AUTHORITY = "content://dk.iha.itsmap.grp11662.telecare.app.content.TeleCareContentProvider";
    static final Uri CONTENT_URI = Uri.parse(AUTHORITY);

    // integer values used for content URI
    public static int MEASUREMENT = 1;
    public static int ID_MEASUREMENT = 2;

    TeleCareDbOpenHelper dbHelper;
    TeleCareDataSource dbDataSource;
    SQLiteDatabase database;

    // projection map for a query
    private static HashMap<String, String> mMeasurementMap;
    private static HashMap<String, String> mUserMap;

    // maps content URI "patterns" to integer values
    static final UriMatcher urimatcher;
    static{
        urimatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //urimatcher.addURI(PROVIDER_NAME, "measurements", MEASUREMENT);
        //urimatcher.addURI(PROVIDER_NAME, "measurements/#", ID_MEASUREMENT);
    }

    public TeleCareContentProvider() {
    }

    @Override
    public boolean onCreate(){
        Context context = getContext();
        dbHelper = new TeleCareDbOpenHelper(context);
        dbDataSource = new TeleCareDataSource(context);
        dbDataSource.Open();
        if(dbHelper == null && dbDataSource == null)
            return false;
        else
            return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {

        if(uri.getLastPathSegment()==null){
            return "Uri for item";
        }
        else{
            return "Uri for directory";
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        dbHelper.getWritableDatabase();

        if(urimatcher.match(uri)==MEASUREMENT){
            database = dbDataSource.Open();
            database.insert(dbHelper.TABLE_MEASUREMENT,null,values);
        }
        database.close();
        getContext().getContentResolver().notifyChange(uri,null);
        return null;
     }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        database = dbHelper.getWritableDatabase();
        cursor = database.query(dbHelper.TABLE_MEASUREMENT, projection, selection, selectionArgs, null,null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
      }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
