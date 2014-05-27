package dk.iha.itsmap.grp11662.telecare.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.UrlQuerySanitizer;

import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;
import dk.iha.itsmap.grp11662.telecare.app.model.User;

public class TeleCareDataSource {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    public TeleCareDataSource(Context context){
        dbHelper = new TeleCareDbOpenHelper(context);
    }

    public void Open(){
        database = dbHelper.getWritableDatabase();
    }

    public void Close(){
        dbHelper.close();
    }

    public Measurement createMeasurement(Measurement measurement){

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

        return measurement;
    }

    public User createUser(User user){

        ContentValues values = new ContentValues();

        values.put(TeleCareDbOpenHelper.COLUMN_FIRSTNAME, user.getFirstname());
        values.put(TeleCareDbOpenHelper.COLUMN_SURNAME,user.getSurname());
        values.put(TeleCareDbOpenHelper.COLUMN_USERNAME, user.getUsername());
        values.put(TeleCareDbOpenHelper.COLUMN_PASSWORD, user.getPassword());

        Long insertId = database.insert(TeleCareDbOpenHelper.TABLE_USER,null,values);
        user.setId(insertId);

        return user;
    }

}
