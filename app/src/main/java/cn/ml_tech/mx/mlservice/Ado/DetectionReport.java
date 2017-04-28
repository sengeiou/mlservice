package cn.ml_tech.mx.mlservice.Ado;

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

public class DetectionReport extends DataSupport {
    @Column(unique = true,nullable = false)
    private  int id;
    @Column(nullable = false)
    private int drugId;
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
    @Column( nullable = false)
    private  String operator;
    @Column( nullable = false,defaultValue = "false")
    private boolean deprecate;
    @Column( nullable = false,defaultValue = "false")
    private boolean ispdfdown;
    private DrugInfo drugInfo;
    private User user;
    private List<DetectionDetail>listDetail=new ArrayList<DetectionDetail>();
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public DrugInfo getDrugInfo() {
        return drugInfo;
    }

    public void setDrugInfo(DrugInfo drugInfo) {
        this.drugInfo = drugInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DetectionDetail> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<DetectionDetail> listDetail) {
        this.listDetail = listDetail;
    }



}
