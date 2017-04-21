package cn.ml_tech.mx.mlservice;

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
    String name;
    @Column(nullable = false)
    int type;
    @Column(nullable = false)
    int specification;
    @Column(nullable = false)
    float diameter;
    @Column(nullable = false)
    float height;
    @Column(nullable = false)
    int trayID;
    @Column(nullable = false)
    float srcTime;
    @Column(nullable = false)
    float stpTime;
    @Column(nullable = false)
    float channelValue1;
    @Column(nullable = false)
    float channelValue2;
    @Column(nullable = false)
    float channelValue3;
    @Column(nullable = false)
    float channelValue4;
    @Column(nullable = false)
    float shadeParam;
    @Column(nullable = false, defaultValue = "4500")
    int rotateSpeed;
    @Column(nullable = false)
    float sendParam;
    SpecificationType specificationType;
    Tray tray;

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

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getTrayID() {
        return trayID;
    }

    public void setTrayID(int trayID) {
        this.trayID = trayID;
    }

    public float getSrcTime() {
        return srcTime;
    }

    public void setSrcTime(float srcTime) {
        this.srcTime = srcTime;
    }

    public float getStpTime() {
        return stpTime;
    }

    public void setStpTime(float stpTime) {
        this.stpTime = stpTime;
    }

    public float getChannelValue1() {
        return channelValue1;
    }

    public void setChannelValue1(float channelValue1) {
        this.channelValue1 = channelValue1;
    }

    public float getChannelValue2() {
        return channelValue2;
    }

    public void setChannelValue2(float channelValue2) {
        this.channelValue2 = channelValue2;
    }

    public float getChannelValue3() {
        return channelValue3;
    }

    public void setChannelValue3(float channelValue3) {
        this.channelValue3 = channelValue3;
    }

    public float getChannelValue4() {
        return channelValue4;
    }

    public void setChannelValue4(float channelValue4) {
        this.channelValue4 = channelValue4;
    }

    public float getShadeParam() {
        return shadeParam;
    }

    public void setShadeParam(float shadeParam) {
        this.shadeParam = shadeParam;
    }

    public int getRotateSpeed() {
        return rotateSpeed;
    }

    public void setRotateSpeed(int rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }

    public float getSendParam() {
        return sendParam;
    }

    public void setSendParam(float sendParam) {
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
