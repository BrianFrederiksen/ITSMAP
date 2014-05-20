package dk.iha.itsmap.grp11662.telecare.app.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Parcelable, Serializable {

    //TODO implement User class (Brian)

    private int id;
    private String username;
    private String firstname;
    private String surname;
    private String password;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    //TODO add more detailed data about the user (bachelor minded)
}
