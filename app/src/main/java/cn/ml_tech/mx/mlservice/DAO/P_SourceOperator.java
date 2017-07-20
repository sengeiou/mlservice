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
*create at  2017/5/24 13:24
CREATE TABLE [p_sourceoperator](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [operatorid] text NOT NULL,
    [sourceid] text NOT NULL,
    [p_source_id] integer,
    [p_operator_id] integer);


*/

public class P_SourceOperator extends DataSupport implements Parcelable {

    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private long p_source_id;
    @Column(nullable = false)
    private long p_operator_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getP_source_id() {
        return p_source_id;
    }

    public void setP_source_id(long p_source_id) {
        this.p_source_id = p_source_id;
    }

    public long getP_operator_id() {
        return p_operator_id;
    }

    public void setP_operator_id(long p_operator_id) {
        this.p_operator_id = p_operator_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.p_source_id);
        dest.writeLong(this.p_operator_id);
    }

    public P_SourceOperator() {
    }

    protected P_SourceOperator(Parcel in) {
        this.id = in.readLong();
        this.p_source_id = in.readLong();
        this.p_operator_id = in.readLong();
    }

    public static final Parcelable.Creator<P_SourceOperator> CREATOR = new Parcelable.Creator<P_SourceOperator>() {
        @Override
        public P_SourceOperator createFromParcel(Parcel source) {
            return new P_SourceOperator(source);
        }

        @Override
        public P_SourceOperator[] newArray(int size) {
            return new P_SourceOperator[size];
        }
    };
}