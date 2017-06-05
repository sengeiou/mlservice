package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

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
/*
*
*@author wl
*create at  2017/5/24 13:06
* CREATE TABLE [devuuid](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [devdateofproduction] integer NOT NULL,
    [devfactory] text NOT NULL,
    [devid] text NOT NULL,
    [devmodel] text NOT NULL,
    [devname] text NOT NULL,
    [userabbreviation] text NOT NULL,
    [username] text NOT NULL);


*/

public class DevUuid extends DataSupport implements Parcelable {
    @Column(unique = true,nullable = false)
    private long id;
    @Column( nullable = false)
    private String userAbbreviation;
    @Column(nullable = false)
    private String userName;
    @Column( nullable = false)
    private  String devID;
    @Column( nullable = false)
    private String devModel;
    @Column(nullable = false)
    private String devName;
    @Column( nullable = false)
    private String devFactory;
    @Column( nullable = false)
   private Date devDateOfProduction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserAbbreviation() {
        return userAbbreviation;
    }

    public void setUserAbbreviation(String userAbbreviation) {
        this.userAbbreviation = userAbbreviation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getDevModel() {
        return devModel;
    }

    public void setDevModel(String devModel) {
        this.devModel = devModel;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevFactory() {
        return devFactory;
    }

    public void setDevFactory(String devFactory) {
        this.devFactory = devFactory;
    }

    public Date getDevDateOfProduction() {
        return devDateOfProduction;
    }

    public void setDevDateOfProduction(Date devDateOfProduction) {
        this.devDateOfProduction = devDateOfProduction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.userAbbreviation);
        dest.writeString(this.userName);
        dest.writeString(this.devID);
        dest.writeString(this.devModel);
        dest.writeString(this.devName);
        dest.writeString(this.devFactory);
        dest.writeLong(this.devDateOfProduction != null ? this.devDateOfProduction.getTime() : -1);
    }

    public DevUuid() {

    }

    protected DevUuid(Parcel in) {
        this.id = in.readLong();
        this.userAbbreviation = in.readString();
        this.userName = in.readString();
        this.devID = in.readString();
        this.devModel = in.readString();
        this.devName = in.readString();
        this.devFactory = in.readString();
        long tmpDevDateOfProduction = in.readLong();
        this.devDateOfProduction = tmpDevDateOfProduction == -1 ? null : new Date(tmpDevDateOfProduction);
    }

    public static final Parcelable.Creator<DevUuid> CREATOR = new Parcelable.Creator<DevUuid>() {
        @Override
        public DevUuid createFromParcel(Parcel source) {
            return new DevUuid(source);
        }

        @Override
        public DevUuid[] newArray(int size) {
            return new DevUuid[size];
        }
    };
}
