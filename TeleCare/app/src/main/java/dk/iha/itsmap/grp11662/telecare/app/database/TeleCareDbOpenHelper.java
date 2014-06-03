package dk.iha.itsmap.grp11662.telecare.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TeleCareDbOpenHelper extends SQLiteOpenHelper {

    private static final String LOGTAG_DB = "Database: ";

    //Database information
    private static final String DATABASE_NAME = "telecare.db";
    private static final int DATABASE_VERSION = 1;

    //Table User
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FIRSTNAME ="firstname";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_SIPDOMAIN = "sipdomain";
    public static final String COLUMN_DOCTORUSERNAME = "doctorsipaddress";


    //Table Measurement
    public static final String TABLE_MEASUREMENT = "measurement";
    public static final String COLUMN_MEASUREMENT_ID = "measurementId";
    public static final String COLUMN_MEASUREMENT_WEIGHT = "weight";
    public static final String COLUMN_MEASUREMENT_TEMPERATURE = "temperature";
    public static final String COLUMN_MEASUREMENT_BLOODGLUCOSE = "bloodglucose";
    public static final String COLUMN_MEASUREMENT_DBP = "dBP";
    public static final String COLUMN_MEASUREMENT_SBP = "sBP";
    public static final String COLUMN_MEASUREMENT_COMMENTS = "comment";
    public static final String COLUMN_MEASUREMENT_DATE = "date";

    //Creation of tables
    private static final String TABLE_CREATE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_FIRSTNAME + " TEXT, " +
                    COLUMN_SURNAME + " TEXT " +
                    COLUMN_SIPDOMAIN + " TEXT " +
                    COLUMN_DOCTORUSERNAME + " TEXT " +
                    ")";

    private static final String TABLE_CREATE_MEASUREMENT =
            "CREATE TABLE " + TABLE_MEASUREMENT + " (" +
                    COLUMN_MEASUREMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MEASUREMENT_WEIGHT + " TEXT, " +
                    COLUMN_MEASUREMENT_TEMPERATURE + " TEXT, " +
                    COLUMN_MEASUREMENT_BLOODGLUCOSE + " TEXT, " +
                    COLUMN_MEASUREMENT_DBP + " TEXT " +
                    COLUMN_MEASUREMENT_SBP + " TEXT " +
                    COLUMN_MEASUREMENT_COMMENTS + " TEXT " +
                    COLUMN_MEASUREMENT_DATE + " TEXT " +
                    ")";


    //Constructors
    public TeleCareDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //Override methods
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE_USER);
        Log.i(LOGTAG_DB, "user table created");
        sqLiteDatabase.execSQL(TABLE_CREATE_MEASUREMENT);
        Log.i(LOGTAG_DB, "measurement table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS  " + TABLE_USER);
    sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MEASUREMENT);
    onCreate(sqLiteDatabase);
    }

}
