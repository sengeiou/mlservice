package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by mx on 2017/4/21.
 */
/*
*
*@author wl
*create at  2017/5/24 13:27
CREATE TABLE [specificationtype](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [name] text NOT NULL);
*/

public class SpecificationType extends DataSupport implements Parcelable {

    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    public SpecificationType() {
    }

    protected SpecificationType(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<SpecificationType> CREATOR = new Parcelable.Creator<SpecificationType>() {
        @Override
        public SpecificationType createFromParcel(Parcel source) {
            return new SpecificationType(source);
        }

        @Override
        public SpecificationType[] newArray(int size) {
            return new SpecificationType[size];
        }
    };
}