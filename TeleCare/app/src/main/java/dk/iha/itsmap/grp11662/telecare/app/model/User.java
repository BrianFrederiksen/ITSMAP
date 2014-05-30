package dk.iha.itsmap.grp11662.telecare.app.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Parcelable, Serializable {

    private ArrayList<Measurement> measurements;
    private Long id;
    private String username;
    private String firstname;
    private String surname;
    private String password;
    private String sipAdress;
    private String doctorSipAdress;


    public String getSipAdress() {
        return sipAdress;
    }

    public void setSipAdress(String sipAdress) {
        this.sipAdress = sipAdress;
    }

    public String getDoctorSipAdress() {
        return doctorSipAdress;
    }

    public void setDoctorSipAdress(String doctorSipAdress) {
        this.doctorSipAdress = doctorSipAdress;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String firstname, String surname, String password, ArrayList<Measurement> measurements) {

        this.username = username;
        this.firstname = firstname;
        this.surname = surname;
        this.password = password;
        this.measurements = measurements;


    }

    public User(Parcel in) {

        String[] userData = new String[4];
        in.readStringArray(userData);
        this.username = userData[0];
        this.password = userData[1];
        this.firstname = userData[2];
        this.surname = userData[3];
    }

    public void AddUserMeasurements(Measurement newMeasurement) {

        if(measurements == null)
        {
            measurements = new ArrayList<>();
        }
        measurements.add(newMeasurement);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.username, this.password,
                this.firstname, this.surname});
    }

    public static final Parcelable.Creator<User> USER_CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
