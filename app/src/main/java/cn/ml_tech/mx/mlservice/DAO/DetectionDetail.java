package cn.ml_tech.mx.mlservice.DAO;

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
/*
*
*@author wl
*create at  2017/5/24 12:59
* CREATE TABLE [detectiondetail](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [colorfactor] integer NOT NULL,
    [data1] real NOT NULL,
    [data2] real NOT NULL,
    [data3] real NOT NULL,
    [data4] real NOT NULL,
    [detindex] integer NOT NULL,
    [ispositive] integer NOT NULL,
    [isvalid] integer NOT NULL,
    [nodeinfo] text NOT NULL,
    [repindex] integer NOT NULL,
    [scrtime] real NOT NULL,
    [scrtimetext] text NOT NULL,
    [stptime] real NOT NULL,
    [stptimetext] text NOT NULL,
    [video] text NOT NULL,
    [videomd5] text NOT NULL,
    [detectionreport_id] integer);


*/

public class DetectionDetail extends DataSupport {
    @Column(unique = true,nullable = false)
    private long id;
    @Column( nullable = false)
    private  long detectionreport_id;
    @Column(nullable = false)
    private  int detIndex;
    @Column(nullable = false)
    private  int repIndex;
    @Column(nullable = false)
    private  double data1;
    @Column( nullable = false)
    private double data2;
    @Column(nullable = false)
    private double data3;
    @Column( nullable = false)
    private double data4;
    @Column(nullable = false)
    private  int colorFactor;
    @Column( nullable = false)
    private double scrTime;
    @Column(nullable = false)
    private double stpTime;
    @Column( nullable = false)
    private String scrTimeText;
    @Column(nullable = false)
    private String stpTimeText;
    @Column( nullable = false)
    private String video;
    @Column(nullable = false)
    private String videoMd5;
    @Column( nullable = false)
    private boolean isPositive;
    @Column(nullable = false)
    private boolean isValid;
    @Column(nullable = false)
    private String nodeInfo;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getDetectionreport_id() {
        return detectionreport_id;
    }

    public void setDetectionreport_id(long detectionreport_id) {
        this.detectionreport_id = detectionreport_id;
    }



    public int getDetIndex() {
        return detIndex;
    }

    public void setDetIndex(int detIndex) {
        this.detIndex = detIndex;
    }

    public int getRepIndex() {
        return repIndex;
    }

    public void setRepIndex(int repIndex) {
        this.repIndex = repIndex;
    }

    public double getData1() {
        return data1;
    }

    public void setData1(double data1) {
        this.data1 = data1;
    }

    public double getData2() {
        return data2;
    }

    public void setData2(double data2) {
        this.data2 = data2;
    }

    public double getData3() {
        return data3;
    }

    public void setData3(double data3) {
        this.data3 = data3;
    }

    public double getData4() {
        return data4;
    }

    public void setData4(double data4) {
        this.data4 = data4;
    }

    public int getColorFactor() {
        return colorFactor;
    }

    public void setColorFactor(int colorFactor) {
        this.colorFactor = colorFactor;
    }

    public double getScrTime() {
        return scrTime;
    }

    public void setScrTime(double scrTime) {
        this.scrTime = scrTime;
    }

    public double getStpTime() {
        return stpTime;
    }

    public void setStpTime(double stpTime) {
        this.stpTime = stpTime;
    }

    public String getScrTimeText() {
        return scrTimeText;
    }

    public void setScrTimeText(String scrTimeText) {
        this.scrTimeText = scrTimeText;
    }

    public String getStpTimeText() {
        return stpTimeText;
    }

    public void setStpTimeText(String stpTimeText) {
        this.stpTimeText = stpTimeText;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideoMd5() {
        return videoMd5;
    }

    public void setVideoMd5(String videoMd5) {
        this.videoMd5 = videoMd5;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(String nodeInfo) {
        this.nodeInfo = nodeInfo;
    }
}
