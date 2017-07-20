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
*create at  2017/5/24 13:23
CREATE TABLE [p_operator](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [operatortext] text NOT NULL,
    [title] text NOT NULL);


*/

public class P_Operator extends DataSupport implements Parcelable {

    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private String operatortext;

    @Column(nullable = false)
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperatortext() {
        return operatortext;
    }

    public void setOperatortext(String operatortext) {
        this.operatortext = operatortext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.operatortext);
        dest.writeString(this.title);
    }

    public P_Operator() {
    }

    protected P_Operator(Parcel in) {
        this.id = in.readLong();
        this.operatortext = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<P_Operator> CREATOR = new Parcelable.Creator<P_Operator>() {
        @Override
        public P_Operator createFromParcel(Parcel source) {
            return new P_Operator(source);
        }

        @Override
        public P_Operator[] newArray(int size) {
            return new P_Operator[size];
        }
    };
}