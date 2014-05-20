package dk.iha.handin05.grp11662.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomerDBOpenHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "Database: ";

    private static final String DATABASE_NAME = "customers.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TOURS = "customers";
    public static final String COLUMN_ID = "customerId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TOURS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    ")";



    public CustomerDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
