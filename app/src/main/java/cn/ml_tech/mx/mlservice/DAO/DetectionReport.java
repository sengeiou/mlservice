package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
*create at  2017/5/24 13:00
* CREATE TABLE [detectionreport](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [date] integer NOT NULL,
    [deprecate] integer NOT NULL DEFAULT false,
    [detectionbatch] text NOT NULL,
    [detectioncount] integer NOT NULL,
    [detectionfirstcount] integer NOT NULL,
    [detectionnumber] text NOT NULL,
    [detectionsecondcount] integer NOT NULL,
    [detectionsn] text NOT NULL,
    [ispdfdown] integer NOT NULL DEFAULT false,
    [user_id] integer,
    [druginfo_id] integer);


*/

public class DetectionReport extends DataSupport implements Parcelable {
    @Column(unique = true,nullable = false)
    private  long id;
    @Column(nullable = false)
    private long user_id;
    @Column(nullable = false)
    private long druginfo_id;
    @Column(nullable = false)
    private String detectionSn;
    @Column( nullable = false)
    private String detectionNumber;
    @Column(nullable = false)
    private  String detectionBatch;
    @Column( nullable = false)
    private  int detectionCount;
    @Column(nullable = false)
    private  int detectionFirstCount;
    @Column( nullable = false)
    private  int detectionSecondCount;
    @Column(nullable = false)
    private  Date date;
    @Column( nullable = false,defaultValue = "false")
    private boolean deprecate;
    @Column( nullable = false,defaultValue = "false")
    private boolean ispdfdown;
    private List<DetectionDetail>listDetail=new ArrayList<DetectionDetail>();
    protected String drugName;
    protected String factoryName;
    protected String userName;

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

    public long getDruginfo_id() {
        return druginfo_id;
    }

    public void setDruginfo_id(long druginfo_id) {
        this.druginfo_id = druginfo_id;
    }

    public String getDetectionSn() {
        return detectionSn;
    }

    public void setDetectionSn(String detectionSn) {
        this.detectionSn = detectionSn;
    }

    public String getDetectionNumber() {
        return detectionNumber;
    }

    public void setDetectionNumber(String detectionNumber) {
        this.detectionNumber = detectionNumber;
    }

    public String getDetectionBatch() {
        return detectionBatch;
    }

    public void setDetectionBatch(String detectionBatch) {
        this.detectionBatch = detectionBatch;
    }

    public int getDetectionCount() {
        return detectionCount;
    }

    public void setDetectionCount(int detectionCount) {
        this.detectionCount = detectionCount;
    }

    public int getDetectionFirstCount() {
        return detectionFirstCount;
    }

    public void setDetectionFirstCount(int detectionFirstCount) {
        this.detectionFirstCount = detectionFirstCount;
    }

    public int getDetectionSecondCount() {
        return detectionSecondCount;
    }

    public void setDetectionSecondCount(int detectionSecondCount) {
        this.detectionSecondCount = detectionSecondCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public boolean ispdfdown() {
        return ispdfdown;
    }

    public void setIspdfdown(boolean ispdfdown) {
        this.ispdfdown = ispdfdown;
    }

    public List<DetectionDetail> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<DetectionDetail> listDetail) {
        this.listDetail = listDetail;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.user_id);
        dest.writeLong(this.druginfo_id);
        dest.writeString(this.detectionSn);
        dest.writeString(this.detectionNumber);
        dest.writeString(this.detectionBatch);
        dest.writeInt(this.detectionCount);
        dest.writeInt(this.detectionFirstCount);
        dest.writeInt(this.detectionSecondCount);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeByte(this.deprecate ? (byte) 1 : (byte) 0);
        dest.writeByte(this.ispdfdown ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.listDetail);
        dest.writeString(this.drugName);
        dest.writeString(this.factoryName);
        dest.writeString(this.userName);
    }

    public DetectionReport() {
    }

    protected DetectionReport(Parcel in) {
        this.id = in.readLong();
        this.user_id = in.readLong();
        this.druginfo_id = in.readLong();
        this.detectionSn = in.readString();
        this.detectionNumber = in.readString();
        this.detectionBatch = in.readString();
        this.detectionCount = in.readInt();
        this.detectionFirstCount = in.readInt();
        this.detectionSecondCount = in.readInt();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.deprecate = in.readByte() != 0;
        this.ispdfdown = in.readByte() != 0;
        this.listDetail = in.createTypedArrayList(DetectionDetail.CREATOR);
        this.drugName = in.readString();
        this.factoryName = in.readString();
        this.userName = in.readString();
    }

    public static final Parcelable.Creator<DetectionReport> CREATOR = new Parcelable.Creator<DetectionReport>() {
        @Override
        public DetectionReport createFromParcel(Parcel source) {
            return new DetectionReport(source);
        }

        @Override
        public DetectionReport[] newArray(int size) {
            return new DetectionReport[size];
        }
    };
}
