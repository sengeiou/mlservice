package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE factory
 (
 id integer primary key AUTOINCREMENT not null,
 name text not null unique,
 address text not null,
 phone text,
 fax text,
 mail text
 contactName text
 contactPhone text,
 webSite text
 , province_code TEXT default NULL, city_code TEXT default NULL, area_code TEXT default NULL, contactName text, contactPhone text);
 */

public class UserType extends DataSupport implements Parcelable {

    @Column(nullable = false, unique = true)
    private long type_id;
    @Column(nullable = false, unique = true)
    private String name;

    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.type_id);
        dest.writeString(this.name);
    }

    public UserType() {
    }

    protected UserType(Parcel in) {
        this.type_id = in.readLong();
        this.name = in.readString();
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