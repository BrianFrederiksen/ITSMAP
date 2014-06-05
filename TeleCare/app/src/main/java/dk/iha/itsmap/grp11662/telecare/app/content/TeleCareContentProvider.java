package dk.iha.itsmap.grp11662.telecare.app.content;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

import dk.iha.itsmap.grp11662.telecare.app.database.TeleCareDataSource;
import dk.iha.itsmap.grp11662.telecare.app.database.TeleCareDbOpenHelper;

public class TeleCareContentProvider extends ContentProvider {


    // Integer values used for content URI
    public static final int MEASUREMENTS = 1;
    public static final int MEASUREMENT_ID = 2;
    public static final int USERS = 3;
    public static final int USER_ID = 4;
    // Content types
    public static final String CONTENT_TYPE_MEASUREMENTS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/measurements";
    public static final String CONTENT_ITEM_TYPE_MEASUREMENTS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/measurement";
    public static final String CONTENT_TYPE_USERS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/users";
    public static final String CONTENT_ITEM_TYPE_USERS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/user";
    // Fields for content provider
    private static final String AUTHORITY = "dk.iha.itsmap.grp11662.telecare.app.contentprovider";
    public static final Uri CONTENT_URI_MEASUREMENTS = Uri.parse("content://" + AUTHORITY + "/measurements");
    public static final Uri CONTENT_URI_USERS = Uri.parse("content://" + AUTHORITY + "/users");
    private static final UriMatcher urimatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        urimatcher.addURI(AUTHORITY, "measurements", MEASUREMENTS);
        urimatcher.addURI(AUTHORITY, "measurements/#", MEASUREMENT_ID);
        urimatcher.addURI(AUTHORITY, "users", USERS);
        urimatcher.addURI(AUTHORITY, "users/#", USER_ID);
    }

    // Database related members
    private TeleCareDbOpenHelper dbHelper;
    private TeleCareDataSource dbDataSource;

    public TeleCareContentProvider() {
    }

    @Override
    public boolean onCreate() {
        dbHelper = new TeleCareDbOpenHelper(getContext());
        dbDataSource = new TeleCareDataSource(getContext());
        dbDataSource.Open();

        if (dbHelper == null && dbDataSource == null)
            return false;
        else
            return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = urimatcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        String id;

        switch (uriType) {
            case MEASUREMENTS:
                rowsDeleted = db.delete(TeleCareDbOpenHelper.TABLE_MEASUREMENT, selection,
                        selectionArgs);
                break;
            case MEASUREMENT_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(TeleCareDbOpenHelper.TABLE_MEASUREMENT,
                            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(TeleCareDbOpenHelper.TABLE_MEASUREMENT,
                            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID + "=" + id
                                    + " and " + selection, selectionArgs
                    );
                }
                break;
            case USERS:
                rowsDeleted = db.delete(TeleCareDbOpenHelper.TABLE_USER, selection,
                        selectionArgs);
                break;
            case USER_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(TeleCareDbOpenHelper.TABLE_USER,
                            TeleCareDbOpenHelper.COLUMN_USER_ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(TeleCareDbOpenHelper.TABLE_USER,
                            TeleCareDbOpenHelper.COLUMN_USER_ID + "=" + id
                                    + " and " + selection, selectionArgs
                    );
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        if (uri.equals(CONTENT_URI_MEASUREMENTS)) {
            return CONTENT_TYPE_MEASUREMENTS;
        } else if (uri.equals(CONTENT_URI_USERS)) {
            return CONTENT_ITEM_TYPE_USERS;
        } else {
            return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = urimatcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        Uri returnUri;

        switch (uriType) {
            case MEASUREMENTS:
                id = db.insert(TeleCareDbOpenHelper.TABLE_MEASUREMENT, null, values);
                returnUri = Uri.parse("measurements/" + id);
                break;
            case USERS:
                id = db.insert(TeleCareDbOpenHelper.TABLE_USER, null, values);
                returnUri = Uri.parse("users/" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table according to given Uri
        if (uri.equals(CONTENT_URI_USERS)) {
            queryBuilder.setTables(TeleCareDbOpenHelper.TABLE_USER);
        } else if (uri.equals(CONTENT_URI_MEASUREMENTS)) {
            queryBuilder.setTables(TeleCareDbOpenHelper.TABLE_MEASUREMENT);
        } else {
            throw new IllegalArgumentException("Non-existing table");
        }

        int uriType = urimatcher.match(uri);
        switch (uriType) {
            case MEASUREMENTS:
                break;
            case MEASUREMENT_ID:
                // Adding Measurement ID to the original query
                queryBuilder.appendWhere(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID + "=" +
                        uri.getLastPathSegment());
            case USERS:
                break;
            case USER_ID:
                // Adding User ID to the original query
                queryBuilder.appendWhere(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID + "=" +
                        uri.getLastPathSegment());
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = urimatcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;
        String id;

        switch (uriType) {
            case MEASUREMENTS:
                rowsUpdated = db.update(TeleCareDbOpenHelper.TABLE_MEASUREMENT,
                        values, selection, selectionArgs);
                break;
            case MEASUREMENT_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(TeleCareDbOpenHelper.TABLE_MEASUREMENT,
                            values, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID + "=" + id, null);
                } else {
                    rowsUpdated = db.update(TeleCareDbOpenHelper.TABLE_MEASUREMENT,
                            values, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID + "=" + id
                                    + " and " + selection, selectionArgs
                    );
                }
                break;
            case USERS:
                rowsUpdated = db.update(TeleCareDbOpenHelper.TABLE_USER,
                        values, selection, selectionArgs);
                break;
            case USER_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(TeleCareDbOpenHelper.TABLE_USER,
                            values, TeleCareDbOpenHelper.COLUMN_USER_ID + "=" + id, null);
                } else {
                    rowsUpdated = db.update(TeleCareDbOpenHelper.TABLE_USER,
                            values, TeleCareDbOpenHelper.COLUMN_USER_ID + "=" + id
                                    + " and " + selection, selectionArgs
                    );
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    // Method for checking if columns are available
    private void checkColumns(String[] projection) {
        String[] available = {TeleCareDbOpenHelper.COLUMN_DOCTORUSERNAME,
                TeleCareDbOpenHelper.COLUMN_FIRSTNAME, TeleCareDbOpenHelper.COLUMN_PASSWORD,
                TeleCareDbOpenHelper.COLUMN_SIPDOMAIN, TeleCareDbOpenHelper.COLUMN_USERNAME,
                TeleCareDbOpenHelper.COLUMN_USER_ID, TeleCareDbOpenHelper.COLUMN_SURNAME,
                TeleCareDbOpenHelper.COLUMN_MEASUREMENT_BLOODGLUCOSE, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_COMMENTS,
                TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DATE, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DBP,
                TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_SBP,
                TeleCareDbOpenHelper.COLUMN_MEASUREMENT_WEIGHT, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_TEMPERATURE};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));

            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}
