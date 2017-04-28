package cn.ml_tech.mx.mlservice.Ado;

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

public class DrugContainer extends DataSupport {
    @Column(unique = true, nullable = false)
    private String id;
    @Column(unique = true, nullable = false)
   private String name;
    @Column(nullable = false)
    private int type;
    @Column(nullable = false)
    private  int specification;
    @Column(nullable = false)
    private double diameter;
    @Column(nullable = false)
    private  double height;
    @Column(nullable = false)
    private int trayID;
    @Column(nullable = false)
    private double srcTime;
    @Column(nullable = false)
    private double stpTime;
    @Column(nullable = false)
    private double channelValue1;
    @Column(nullable = false)
    private double channelValue2;
    @Column(nullable = false)
    private double channelValue3;
    @Column(nullable = false)
    private double channelValue4;
    @Column(nullable = false)
    private double shadeParam;
    @Column(nullable = false, defaultValue = "4500")
    private int rotateSpeed;
    @Column(nullable = false)
    private double sendParam;
    private SpecificationType specificationType;
   private Tray tray;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSpecification() {
        return specification;
    }

    public void setSpecification(int specification) {
        this.specification = specification;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getTrayID() {
        return trayID;
    }

    public void setTrayID(int trayID) {
        this.trayID = trayID;
    }

    public double getSrcTime() {
        return srcTime;
    }

    public void setSrcTime(double srcTime) {
        this.srcTime = srcTime;
    }

    public double getStpTime() {
        return stpTime;
    }

    public void setStpTime(double stpTime) {
        this.stpTime = stpTime;
    }

    public double getChannelValue1() {
        return channelValue1;
    }

    public void setChannelValue1(double channelValue1) {
        this.channelValue1 = channelValue1;
    }

    public double getChannelValue2() {
        return channelValue2;
    }

    public void setChannelValue2(double channelValue2) {
        this.channelValue2 = channelValue2;
    }

    public double getChannelValue3() {
        return channelValue3;
    }

    public void setChannelValue3(double channelValue3) {
        this.channelValue3 = channelValue3;
    }

    public double getChannelValue4() {
        return channelValue4;
    }

    public void setChannelValue4(double channelValue4) {
        this.channelValue4 = channelValue4;
    }

    public double getShadeParam() {
        return shadeParam;
    }

    public void setShadeParam(double shadeParam) {
        this.shadeParam = shadeParam;
    }

    public int getRotateSpeed() {
        return rotateSpeed;
    }

    public void setRotateSpeed(int rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }

    public double getSendParam() {
        return sendParam;
    }

    public void setSendParam(double sendParam) {
        this.sendParam = sendParam;
    }

    public SpecificationType getSpecificationType() {
        return specificationType;
    }

    public void setSpecificationType(SpecificationType specificationType) {
        this.specificationType = specificationType;
    }

    public Tray getTray() {
        return tray;
    }

    public void setTray(Tray tray) {
        this.tray = tray;
    }
}
