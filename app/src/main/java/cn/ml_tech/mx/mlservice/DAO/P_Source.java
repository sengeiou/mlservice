package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE factory
 * (
 * id integer primary key AUTOINCREMENT not null,
 * name text not null unique,
 * address text not null,
 * phone text,
 * fax text,
 * mail text
 * contactName text
 * contactPhone text,
 * webSite text
 * , province_code TEXT default NULL, city_code TEXT default NULL, area_code TEXT default NULL, contactName text, contactPhone text);
 */
/*
*
*@author wl
*create at  2017/5/24 13:23
CREATE TABLE [p_source](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [title] text NOT NULL,
    [url] text NOT NULL,
    [p_module_id] integer);


*/

public class P_Source extends DataSupport implements Parcelable {

    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private long parentid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getParentid() {
        return parentid;
    }

    public void setParentid(long parentid) {
        this.parentid = parentid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeLong(this.parentid);
    }

    public P_Source() {
    }

    protected P_Source(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.title = in.readString();
        this.parentid = in.readLong();
    }

    public static final Parcelable.Creator<P_Source> CREATOR = new Parcelable.Creator<P_Source>() {
        @Override
        public P_Source createFromParcel(Parcel source) {
            return new P_Source(source);
        }

        @Override
        public P_Source[] newArray(int size) {
            return new P_Source[size];
        }
    };
}