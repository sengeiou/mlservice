package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by mx on 2017/3/23.
 */

public  final class MotorControl implements Parcelable {

    public String name;
    public int sid;

    protected MotorControl(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<MotorControl> CREATOR = new Creator<MotorControl>() {
        @Override
        public MotorControl createFromParcel(Parcel in) {
            return new MotorControl(in);
        }

        @Override
        public MotorControl[] newArray(int size) {
            return new MotorControl[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(sid);
    }
    public void readFromParcel(Parcel in) {
        name = in.readString();
        sid = in.readInt();
    }
    public String toString() {
        return String.format(Locale.ENGLISH, "MotorControl[ %s, %d]", name, sid);
    }
}
