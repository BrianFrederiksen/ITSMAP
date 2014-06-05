package dk.iha.itsmap.grp11662.telecare.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.audiofx.Visualizer;
import android.net.UrlQuerySanitizer;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;
import dk.iha.itsmap.grp11662.telecare.app.model.User;

public class TeleCareDataSource {

    //static fields
    private static String LOG_TAG_DATASOURCE = "Datasource: ";
    private static final String[] mMeasurementColumns = {
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_USER_ID,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_WEIGHT,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_TEMPERATURE,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_BLOODGLUCOSE,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DBP,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_SBP,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_COMMENTS,
            TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DATE};

    private static final String[] mUserColumns = {
            TeleCareDbOpenHelper.COLUMN_USER_ID,
            TeleCareDbOpenHelper.COLUMN_USERNAME,
            TeleCareDbOpenHelper.COLUMN_PASSWORD,
            TeleCareDbOpenHelper.COLUMN_FIRSTNAME,
            TeleCareDbOpenHelper.COLUMN_SURNAME,
            TeleCareDbOpenHelper.COLUMN_SIPDOMAIN,
            TeleCareDbOpenHelper.COLUMN_DOCTORUSERNAME};

    //class variables
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    //constructor
    public TeleCareDataSource(Context context){
        dbHelper = new TeleCareDbOpenHelper(context);
    }

    //methods for handling database
    public SQLiteDatabase Open(){
        Log.i(LOG_TAG_DATASOURCE,"Opening database");
        database = dbHelper.getWritableDatabase();
        return database;
    }

    public void Close(){
        Log.i(LOG_TAG_DATASOURCE,"Closing database");
        dbHelper.close();
    }

    //User handling
    public User Login(String username, String password){
        User user = new User();
        Cursor cursor = database.rawQuery("SELECT * FROM" +
                        TeleCareDbOpenHelper.TABLE_USER +
                        "WHERE username=? AND password=?",
                new String[]{username,password});
        if(cursor != null){
            if(cursor.getCount() > 0 ){
                user.setId(cursor.getLong(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_USER_ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_PASSWORD)));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_FIRSTNAME)));
                user.setSurname(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_FIRSTNAME)));
                user.setSipDomain(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_SIPDOMAIN)));
                user.setDoctorUsername(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_DOCTORUSERNAME)));
                return user;
            }
        }
        return null;
    }

    //Create methods
    public Measurement createMeasurement(Measurement measurement, User user){
        Log.i(LOG_TAG_DATASOURCE,"Creating measurement");
        ContentValues values = new ContentValues();
        values.put(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_USER_ID, user.getId());
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
        values.put(TeleCareDbOpenHelper.COLUMN_SIPDOMAIN, user.getSipDomain());
        values.put(TeleCareDbOpenHelper.COLUMN_DOCTORUSERNAME, user.getDoctorUsername());

        Long insertId = database.insert(TeleCareDbOpenHelper.TABLE_USER,null,values);
        user.setId(insertId);
        Log.i(LOG_TAG_DATASOURCE,"User created");
        return user;
    }

    //find methods
    public List<Measurement> findAllMeasurements(){
        List<Measurement> measurements = new ArrayList<Measurement>();
        Cursor cursor = database.query(TeleCareDbOpenHelper.TABLE_MEASUREMENT, mMeasurementColumns,
                null,null,null,null,null );
        Log.i(LOG_TAG_DATASOURCE, "Returned: " + cursor.getCount() + " rows");
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Measurement measurement = new Measurement();
                measurement.setId(cursor.getLong(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID)));
                measurement.setUserId(cursor.getLong(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_USER_ID)));
                measurement.setWeight(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_WEIGHT)));
                measurement.setTemperature(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_TEMPERATURE)));
                measurement.setBloodGlucose(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_BLOODGLUCOSE)));
                measurement.setdBP(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DBP)));
                measurement.setsBP(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_SBP)));
                measurement.setComments(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_COMMENTS)));
                measurement.setDate(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DATE)));
                measurements.add(measurement);
            }
        }
        return measurements;
    }

    public List<Measurement> findAllUserMeasurements(Long userId){
        List<Measurement> measurements = new ArrayList<Measurement>();
        Cursor cursor =  this.database.rawQuery("SELECT * FROM " +
                TeleCareDbOpenHelper.TABLE_MEASUREMENT + " WHERE " +
                TeleCareDbOpenHelper.COLUMN_MEASUREMENT_USER_ID + " = " + userId  , null);
        Log.i(LOG_TAG_DATASOURCE, "Returned: " + cursor.getCount() + " rows");
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Measurement measurement = new Measurement();
                measurement.setId(cursor.getLong(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID)));
                measurement.setUserId(cursor.getLong(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_USER_ID)));
                measurement.setWeight(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_WEIGHT)));
                measurement.setTemperature(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_TEMPERATURE)));
                measurement.setBloodGlucose(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_BLOODGLUCOSE)));
                measurement.setdBP(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DBP)));
                measurement.setsBP(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_SBP)));
                measurement.setComments(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_COMMENTS)));
                measurement.setDate(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DATE)));
                measurements.add(measurement);
            }
        }
        return measurements;
    }


    public List<User> findAllUsers(){
        List<User> users = new ArrayList<User>();
        Cursor cursor = database.query(TeleCareDbOpenHelper.TABLE_USER, mUserColumns,
                null,null,null,null,null );
        Log.i(LOG_TAG_DATASOURCE, "Returned: " + cursor.getCount() + " rows");
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                User user = new User();
                user.setId(cursor.getLong(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_USER_ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_PASSWORD)));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_FIRSTNAME)));
                user.setSurname(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_SURNAME)));
                user.setSipDomain(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_SIPDOMAIN)));
                user.setDoctorUsername(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_DOCTORUSERNAME)));
                users.add(user);
            }
        }
        return users;
    }

    public Measurement findMeasurement(Long id){
        Measurement measurement = new Measurement();
        Cursor cursor =  this.database.rawQuery("SELECT * FROM " +
                TeleCareDbOpenHelper.TABLE_MEASUREMENT + " WHERE " +
                TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID + " = " + id  , null);

        Log.i(LOG_TAG_DATASOURCE, "Returned: " + cursor.getCount() + " rows");
        while(cursor.moveToNext()) {
            measurement.setId(cursor.getLong(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_ID)));
            measurement.setWeight(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_WEIGHT)));
            measurement.setTemperature(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_TEMPERATURE)));
            measurement.setBloodGlucose(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_BLOODGLUCOSE)));
            measurement.setdBP(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DBP)));
            measurement.setsBP(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_SBP)));
            measurement.setComments(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_COMMENTS)));
            measurement.setDate(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_MEASUREMENT_DATE)));
        }
        return measurement;
    }

    public User findUser(Long id){
        User user = new User();
        Cursor cursor =  this.database.rawQuery("SELECT * FROM " +
                TeleCareDbOpenHelper.TABLE_USER + " WHERE " +
                TeleCareDbOpenHelper.COLUMN_USER_ID + "=" + id  , null);
        Log.i(LOG_TAG_DATASOURCE, "Returned: " + cursor.getCount() + " rows");
        while(cursor.moveToNext()) {
            user.setId(cursor.getLong(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_USER_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_PASSWORD)));
            user.setFirstname(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_FIRSTNAME)));
            user.setSurname(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_SURNAME)));
            user.setSipDomain(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_SIPDOMAIN)));
            user.setDoctorUsername(cursor.getString(cursor.getColumnIndex(TeleCareDbOpenHelper.COLUMN_DOCTORUSERNAME)));
        }
        return user;
    }

    //delete methods
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

/*    public List<Measurement> getAllMeasurement(){
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
    }*/

/*    public List<User> getAllUsers(){
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
    }*/

/*    private Measurement cursorToMeasurement(Cursor cursor){
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
    }*/

/*    private User cursorToUser(Cursor cursor){
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        user.setFirstname(cursor.getString(3));
        user.setSurname(cursor.getString(4));
        user.setSipDomain(cursor.getString(5));
        user.setDoctorUsername(cursor.getString(6));
        return user;
    }*/

    //TODO move this to main activtity
    /*TeleCareDataSource datasource;
    datasource = new TeleCareDataSource(this);
    datasource.Open();

    createDummyMeasurement();
    createDummyUser();*/

    //TODO createDummyUser
/*    private void createDummyUser(){
        Log.i(TeleCareDataSource.LOG_TAG_DATASOURCE, "Creating dummy user");
        User user = new User();
        user.setUsername("jrt");
        user.setPassword("jesper");
        user.setFirstname("Jesper");
        user.setSurname("Toerreseo");
        user.setSipDomain("insertSipDomainHere");
        user.setDoctorUsername("insertDoctorUsernameHere");
        user = datasource.createUser(user);
        Log.i(TeleCareDataSource.LOG_TAG_DATASOURCE, "Dummy user created with username: " +
                user.getUsername() + "password: " + user.getPassword() + "and id: " + user.getId());
    }*/

    //TODO createDummyMeasurement
    /*private void createDummyMeasurement(){
        Log.i(TeleCareDataSource.LOG_TAG_DATASOURCE, "creating demo data measurement");
        Measurement measurement = new Measurement();
        measurement.setWeight("80");
        measurement.setTemperature("37");
        measurement.setBloodGlucose("7");
        measurement.setsBP("120");
        measurement.setdBP("80");
        measurement.setComments("Test measurement");
        measurement.setDate("04/05/2014");
        measurement = datasource.createMeasurement(mDemoDataMeasurement);
        Log.i(TeleCareDataSource.LOG_TAG_DATASOURCE,"Demo data measurement created with id: " + measurement.getId());
    }*/


    /*List<Measurement> measurements = datasource.findAllMeasurements();
    if(measurements.size() == 0){
        createDummyMeasurement();
        measurements = datasource.findAllMeasurements();
    }*/
}
