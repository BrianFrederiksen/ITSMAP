package dk.iha.itsmap.grp11662.handin03.app;

import android.os.Parcel;
import android.os.Parcelable;

public class AndroidVersions implements Parcelable {

    private int mData;

    private String codeName;


    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mData);
    }


}