package dk.iha.itsmap.grp11662.telecare.app;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class Measurement implements Parcelable, Serializable {

    //class variables
     private String weight;
    private String temperature;
    private String bloodGlucose;
    private String dBP;
    private String sBP;
    private String comments;
    private String date;

    //Getter and setters

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getBloodGlucose() {
        return bloodGlucose;
    }

    public void setBloodGlucose(String bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }

    public String getdBP() {
        return dBP;
    }

    public void setdBP(String dBP) {
        this.dBP = dBP;
    }

    public String getsBP() {
        return sBP;
    }

    public void setsBP(String sBP) {
        this.sBP = sBP;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //Constructors
    public Measurement(String Weight, String Temperature,String BloodGlucose, String DBP, String SBP, String Comments, String Date){

        this.weight = Weight;
        this.temperature = Temperature;
        this.bloodGlucose = BloodGlucose;
        this.dBP = DBP;
        this.sBP = SBP;
        this.comments = Comments;
        this.date = Date;
    }

    public Measurement(Parcel in){

        String[] measurementData = new String[7];
        in.readStringArray(measurementData);
        this.weight =  measurementData[0];
        this.temperature =  measurementData[1];
        this.bloodGlucose =  measurementData[2];
        this.dBP =  measurementData[3];
        this.sBP =  measurementData[4];
        this.comments =  measurementData[5];
        this.date =  measurementData[6];
    }

    //Parcelable override methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.weight, this.temperature, this.dBP,
                this.sBP, this.bloodGlucose, this.comments, this.date});
    }

    public static final Parcelable.Creator<Measurement> MEASUREMENT_CREATOR = new Parcelable.Creator<Measurement>() {
        public Measurement createFromParcel(Parcel in){
        return new Measurement(in);}

        @Override
        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }
    };


}
