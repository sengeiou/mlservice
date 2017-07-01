package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE drugContainer
 * (
 * id integer primary key AUTOINCREMENT   not null,
 * name text not null unique,
 * type  INTEGER not null,
 * specification INTEGER not null,
 * diameter REAL not null,
 * height REAL,
 * trayID INTEGER not null,
 * srcTime REAL not null,
 * stpTime REAL not null,
 * channelValue1 REAL not null,
 * channelValue2 REAL not null,
 * channelValue3 REAL not null,
 * channelValue4 REAL not null,
 * shadeParam REAL not null,
 * rotateSpeed INTEGER NOT NULL DEFAULT 4500,
 * sendParam REAL not null,
 * foreign key (specification) REFERENCES specificationType(id),
 * foreign key (trayID) REFERENCES tray(id)
 * );
 */

public class AuditTrailInfoType extends DataSupport implements Parcelable {
    @Column(unique = true, nullable = false)
    private int id;
    @Column(unique = true, nullable = false)
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public AuditTrailInfoType() {
    }

    protected AuditTrailInfoType(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<AuditTrailInfoType> CREATOR = new Parcelable.Creator<AuditTrailInfoType>() {
        @Override
        public AuditTrailInfoType createFromParcel(Parcel source) {
            return new AuditTrailInfoType(source);
        }

        @Override
        public AuditTrailInfoType[] newArray(int size) {
            return new AuditTrailInfoType[size];
        }
    };
}
