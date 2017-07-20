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
*create at  2017/5/24 13:22
CREATE TABLE [p_module](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [title] text NOT NULL,
    [url] text NOT NULL);


*/

public class P_Module extends DataSupport implements Parcelable {

    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String title;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
        dest.writeString(this.title);
    }

    public P_Module() {
    }

    protected P_Module(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<P_Module> CREATOR = new Parcelable.Creator<P_Module>() {
        @Override
        public P_Module createFromParcel(Parcel source) {
            return new P_Module(source);
        }

        @Override
        public P_Module[] newArray(int size) {
            return new P_Module[size];
        }
    };
}