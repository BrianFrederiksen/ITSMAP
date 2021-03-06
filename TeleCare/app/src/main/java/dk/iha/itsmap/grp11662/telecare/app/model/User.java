package dk.iha.itsmap.grp11662.telecare.app.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Parcelable, Serializable {

    //class variables
    private ArrayList<Measurement> measurements;
    private Long id;
    private String username;
    private String firstname;
    private String surname;
    private String password;
    private String sipDomain;
    private String doctorUsername;

    //getter and setters
    public String getSipDomain() {
        return sipDomain;
    }

    public void setSipDomain(String sipDomain) {
        this.sipDomain = sipDomain;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
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

    //constructors
    public User(){

    }

    public User(String username, String firstname, String surname, String password, ArrayList<Measurement> measurements,
                String sipDomain, String doctorUsername) {
        this.username = username;
        this.firstname = firstname;
        this.surname = surname;
        this.password = password;
        this.measurements = measurements;
        this.sipDomain = sipDomain;
        this.doctorUsername = doctorUsername;
    }

    public User(Parcel in) {
        String[] userData = new String[7];
        in.readStringArray(userData);
        this.id = Long.parseLong(userData[0]);
        this.username = userData[1];
        this.password = userData[2];
        this.firstname = userData[3];
        this.surname = userData[4];
        this.sipDomain = userData[5];
        this.doctorUsername = userData[6];
    }

    //methods
    public void AddUserMeasurements(Measurement newMeasurement) {

        if(measurements == null)
        {
            measurements = new ArrayList<>();
        }
        measurements.add(newMeasurement);
    }

    //overrides
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.id.toString(), this.username, this.password,
                this.firstname, this.surname, this.sipDomain, this.doctorUsername});
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
