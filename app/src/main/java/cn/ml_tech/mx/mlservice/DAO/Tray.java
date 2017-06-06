package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE tray (
 "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 "displayId" INTEGER UNIQUE NOT NULL,
 "icId" TEXT UNIQUE  NOT NULL,
 "innerDiameter" REAL NOT NULL,
 "externalDiameter" REAL NOT NULL,
 "diameter" REAL NOT NULL,
 "desc" TEXT NOT NULL
 );
 */
/*
*
*@author wl
*create at  2017/5/24 13:28
CREATE TABLE [tray](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [deprecate] integer NOT NULL DEFAULT false,
    [diameter] real NOT NULL,
    [displayid] integer NOT NULL UNIQUE,
    [externaldiameter] real NOT NULL,
    [icid] text NOT NULL UNIQUE,
    [innerdiameter] real NOT NULL,
    [mark] text NOT NULL);


*/

public class Tray extends DataSupport implements Parcelable {
    @Column(unique = true, nullable = false)
    private long id;
    @Column(unique = true, nullable = false)
    private int displayId;
    @Column(unique = true, nullable = false)
    private String icId;
    @Column(nullable = false)
    private double innerDiameter;
    @Column(nullable = false)
    private double externalDiameter;
    @Column(nullable = false)
    private double diameter;
    @Column(nullable = false)
    private String mark;
    @Column(nullable = false, defaultValue = "false")
    private boolean deprecate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDisplayId() {
        return displayId;
    }

    public void setDisplayId(int displayId) {
        this.displayId = displayId;
    }

    public String getIcId() {
        return icId;
    }

    public void setIcId(String icId) {
        this.icId = icId;
    }

    public double getInnerDiameter() {
        return innerDiameter;
    }

    public void setInnerDiameter(double innerDiameter) {
        this.innerDiameter = innerDiameter;
    }

    public double getExternalDiameter() {
        return externalDiameter;
    }

    public void setExternalDiameter(double externalDiameter) {
        this.externalDiameter = externalDiameter;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.displayId);
        dest.writeString(this.icId);
        dest.writeDouble(this.innerDiameter);
        dest.writeDouble(this.externalDiameter);
        dest.writeDouble(this.diameter);
        dest.writeString(this.mark);
        dest.writeByte(this.deprecate ? (byte) 1 : (byte) 0);
    }

    public Tray() {
    }

    protected Tray(Parcel in) {
        this.id = in.readLong();
        this.displayId = in.readInt();
        this.icId = in.readString();
        this.innerDiameter = in.readDouble();
        this.externalDiameter = in.readDouble();
        this.diameter = in.readDouble();
        this.mark = in.readString();
        this.deprecate = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Tray> CREATOR = new Parcelable.Creator<Tray>() {
        @Override
        public Tray createFromParcel(Parcel source) {
            return new Tray(source);
        }

        @Override
        public Tray[] newArray(int size) {
            return new Tray[size];
        }
    };
}
