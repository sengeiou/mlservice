package cn.ml_tech.mx.mlservice.DAO;

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

public class DetectionReport extends DataSupport {
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
}
