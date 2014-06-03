package dk.iha.itsmap.grp11662.telecare.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.audiofx.Visualizer;
import android.net.UrlQuerySanitizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;
import dk.iha.itsmap.grp11662.telecare.app.model.User;

public class TeleCareDataSource {

    private static String LOG_TAG_DATASOURCE = "Datasource: ";

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    private String[] mMeasurementColumns = {TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_WEIGHT, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_TEMPERATURE,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_BLOODGLUCOSE, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DBP, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_SBP,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_COMMENTS, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DATE};

    private String[] mUserColumns = {TeleCareDbOpenHelper.COLUMN_USER_ID, TeleCareDbOpenHelper.COLUMN_USERNAME, TeleCareDbOpenHelper.COLUMN_PASSWORD,
            TeleCareDbOpenHelper.COLUMN_FIRSTNAME, TeleCareDbOpenHelper.COLUMN_SURNAME, TeleCareDbOpenHelper.COLUMN_SIPDOMAIN, TeleCareDbOpenHelper.COLUMN_DOCTORUSERNAME};

    public TeleCareDataSource(Context context){
        dbHelper = new TeleCareDbOpenHelper(context);
    }

    public SQLiteDatabase Open(){
        Log.i(LOG_TAG_DATASOURCE,"Opening database");
        database = dbHelper.getWritableDatabase();
        return database;
    }

    public void Close(){
        Log.i(LOG_TAG_DATASOURCE,"Closing database");
        dbHelper.close();
    }

    public Measurement createMeasurement(Measurement measurement){
        Log.i(LOG_TAG_DATASOURCE,"Creating measurement");
        ContentValues values = new ContentValues();

        values.put(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_WEIGHT, measurement.getWeight());
        values.put(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_TEMPERATURE, measurement.getTemperature());
        values.put(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_BLOODGLUCOSE,measurement.getBloodGlucose());
        values.put(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DBP,measurement.getdBP());
        values.put(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_SBP, measurement.getsBP());
        values.put(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_COMMENTS, measurement.getComments());
        values.put(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DATE, measurement.getDate());
        Long insertId = database.insert(TeleCareDbOpenHelper.TABLE_MEASUREMENT,null,values);
        measurement.setId(insertId);
        Log.i(LOG_TAG_DATASOURCE,"Measurement created");
        return measurement;
    }

    public User createUser(User user){
        Log.i(LOG_TAG_DATASOURCE,"Creating user");
        ContentValues values = new ContentValues();

        values.put(TeleCareDbOpenHelper.COLUMN_FIRSTNAME, user.getFirstname());
        values.put(TeleCareDbOpenHelper.COLUMN_SURNAME,user.getSurname());
        values.put(TeleCareDbOpenHelper.COLUMN_USERNAME, user.getUsername());
        values.put(TeleCareDbOpenHelper.COLUMN_PASSWORD, user.getPassword());

        Long insertId = database.insert(TeleCareDbOpenHelper.TABLE_USER,null,values);
        user.setId(insertId);
        Log.i(LOG_TAG_DATASOURCE,"User created");
        return user;
    }


    public void DeleteMeasurement(Measurement measurement){
        Log.i(LOG_TAG_DATASOURCE,"Deleting measurement");
        Long id = measurement.getId();
        database.delete(TeleCareDbOpenHelper.TABLE_MEASUREMENT, TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID + " = " + id, null);
        Log.i(LOG_TAG_DATASOURCE,"Measurement deleted");
    }

    public void DeleteUser(User user){
        Log.i(LOG_TAG_DATASOURCE,"Deleting user");
        Long id = user.getId();
        database.delete(TeleCareDbOpenHelper.TABLE_USER, TeleCareDbOpenHelper.COLUMN_USER_ID + " = " + id, null);
        Log.i(LOG_TAG_DATASOURCE,"User deleted");
    }

    public List<Measurement> getAllMeasurement(){
        List<Measurement> measurements = new ArrayList<Measurement>();

        Cursor cursor = database.query(TeleCareDbOpenHelper.TABLE_MEASUREMENT,
                mMeasurementColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Measurement measurement = cursorToMeasurement(cursor);
            measurements.add(measurement);
            cursor.moveToNext();
        }
        cursor.close();
        return measurements;
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();

        Cursor cursor = database.query(TeleCareDbOpenHelper.TABLE_USER,
                mUserColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    private Measurement cursorToMeasurement(Cursor cursor){
        Measurement measurement = new Measurement();
        measurement.setId(cursor.getLong(0));
        measurement.setWeight(cursor.getString(1));
        measurement.setTemperature(cursor.getString(2));
        measurement.setBloodGlucose(cursor.getString(3));
        measurement.setdBP(cursor.getString(4));
        measurement.setsBP(cursor.getString(5));
        measurement.setComments(cursor.getString(6));
        measurement.setDate(cursor.getString(7));
        return measurement;
    }

    private User cursorToUser(Cursor cursor){
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        user.setFirstname(cursor.getString(3));
        user.setSurname(cursor.getString(4));
        user.setSipDomain(cursor.getString(5));
        user.setDoctorUsername(cursor.getString(6));
        return user;
    }

}
