package dk.iha.itsmap.grp11662.handin03.app;

import android.os.Parcel;
import android.os.Parcelable;

//How to use: intent.putExtra("student", new Student("1","Mike","6"));
public class AndroidVersion implements Parcelable {

    private int mData;

    private String codeName;
    private String version;
    private String description;

    public AndroidVersion(String codeName, String version, String description) {
        this.codeName = codeName;
        this.version = version;
        this.description = description;
    }

    public AndroidVersion(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.codeName = data[0];
        this.version = data[1];
        this.description = data[2];
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.codeName, this.version, this.description});
    }

    public static final Parcelable.Creator<AndroidVersion> CREATOR
            = new Parcelable.Creator<AndroidVersion>() {
        public AndroidVersion createFromParcel(Parcel in) {
            return new AndroidVersion(in);
        }

        //@Override
        public AndroidVersion[] newArray(int size) {
            return new AndroidVersion[size];
        }
    };

}