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
*create at  2017/5/24 13:25
CREATE TABLE [p_userpermission](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [righttype] integer NOT NULL,
    [userlogicid] integer NOT NULL,
    [p_sourceoperator_id] integer);


*/

public class P_UserPermission extends DataSupport implements Parcelable {

    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private long user_id;
    @Column(nullable = false)
    private long p_sourceoperator_id;
    @Column(nullable = false)
    private int righttype;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getP_sourceoperator_id() {
        return p_sourceoperator_id;
    }

    public void setP_sourceoperator_id(long p_sourceoperator_id) {
        this.p_sourceoperator_id = p_sourceoperator_id;
    }

    public int getRighttype() {
        return righttype;
    }

    public void setRighttype(int righttype) {
        this.righttype = righttype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.user_id);
        dest.writeLong(this.p_sourceoperator_id);
        dest.writeInt(this.righttype);
    }

    public P_UserPermission() {
    }

    protected P_UserPermission(Parcel in) {
        this.id = in.readLong();
        this.user_id = in.readLong();
        this.p_sourceoperator_id = in.readLong();
        this.righttype = in.readInt();
    }

    public static final Parcelable.Creator<P_UserPermission> CREATOR = new Parcelable.Creator<P_UserPermission>() {
        @Override
        public P_UserPermission createFromParcel(Parcel source) {
            return new P_UserPermission(source);
        }

        @Override
        public P_UserPermission[] newArray(int size) {
            return new P_UserPermission[size];
        }
    };
}