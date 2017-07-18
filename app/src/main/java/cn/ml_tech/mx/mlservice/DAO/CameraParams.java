package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE drugContainer
 (
 id integer primary key AUTOINCREMENT   not null,
 name text not null unique,
 type  INTEGER not null,
 specification INTEGER not null,
 diameter REAL not null,
 height REAL,
 trayID INTEGER not null,
 srcTime REAL not null,
 stpTime REAL not null,
 channelValue1 REAL not null,
 channelValue2 REAL not null,
 channelValue3 REAL not null,
 channelValue4 REAL not null,
 shadeParam REAL not null,
 rotateSpeed INTEGER NOT NULL DEFAULT 4500,
 sendParam REAL not null,
 foreign key (specification) REFERENCES specificationType(id),
 foreign key (trayID) REFERENCES tray(id)
 );
 */

public class CameraParams extends DataSupport implements Parcelable {

    @Column(nullable = false,unique = true)
    private long id;
    @Column(unique = true,nullable = false)
    private String ParamName;
    @Column( nullable = false)
    private double ParamValue;

    @Override
    public String toString() {
        return "CameraParams{" +
                "id=" + id +
                ", ParamName='" + ParamName + '\'' +
                ", ParamValue=" + ParamValue +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParamName() {
        return ParamName;
    }

    public void setParamName(String paramName) {
        ParamName = paramName;
    }

    public double getParamValue() {
        return ParamValue;
    }

    public void setParamValue(double paramValue) {
        ParamValue = paramValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.ParamName);
        dest.writeDouble(this.ParamValue);
    }

    public CameraParams() {
    }

    protected CameraParams(Parcel in) {
        this.id = in.readLong();
        this.ParamName = in.readString();
        this.ParamValue = in.readDouble();
    }

    public static final Parcelable.Creator<CameraParams> CREATOR = new Parcelable.Creator<CameraParams>() {
        @Override
        public CameraParams createFromParcel(Parcel source) {
            return new CameraParams(source);
        }

        @Override
        public CameraParams[] newArray(int size) {
            return new CameraParams[size];
        }
    };
}
