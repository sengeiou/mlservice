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
/*
*
*@author wl
*create at  2017/5/24 13:28
CREATE TABLE [systemconfig](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [paramname] text NOT NULL UNIQUE,
    [paramvalue] real NOT NULL);


*/

public class SystemConfig extends DataSupport implements Parcelable {
    @Column(nullable = false, unique = true)
    private long id;
    @Column(nullable = false, unique = true)
    private String paramName;
    @Column(nullable = false)
    private String paramValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.paramName);
        dest.writeString(this.paramValue);
    }

    public SystemConfig() {
    }

    protected SystemConfig(Parcel in) {
        this.id = in.readLong();
        this.paramName = in.readString();
        this.paramValue = in.readString();
    }

    public static final Parcelable.Creator<SystemConfig> CREATOR = new Parcelable.Creator<SystemConfig>() {
        @Override
        public SystemConfig createFromParcel(Parcel source) {
            return new SystemConfig(source);
        }

        @Override
        public SystemConfig[] newArray(int size) {
            return new SystemConfig[size];
        }
    };
}