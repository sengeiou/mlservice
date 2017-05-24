package cn.ml_tech.mx.mlservice.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ml on 2017/5/15.
 */

public class UserType implements Parcelable {
    private int typeId;
    private String typeName;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.typeId);
        dest.writeString(this.typeName);
    }

    public UserType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
    protected UserType(Parcel in) {
        this.typeId = in.readInt();
        this.typeName = in.readString();
    }

    public static final Parcelable.Creator<UserType> CREATOR = new Parcelable.Creator<UserType>() {
        @Override
        public UserType createFromParcel(Parcel source) {
            return new UserType(source);
        }

        @Override
        public UserType[] newArray(int size) {
            return new UserType[size];
        }
    };
}
