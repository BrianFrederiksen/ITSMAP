package dk.iha.handin05.grp11662.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Customer implements Parcelable, Serializable{

    private String name;
    private String adress;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public Customer(String Name, String Adress){
       name = Name;
       adress = Adress;
   }

    public Customer(Parcel in){
        String[] customer = new String[2];
        in.readStringArray(customer);
        name = customer[0];
        adress = customer[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
