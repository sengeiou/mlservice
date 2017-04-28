package cn.ml_tech.mx.mlservice.Ado;

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

public class DevUuid extends DataSupport {
    @Column(unique = true,nullable = false)
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Column( nullable = false)
    Date devDateOfProduction;
}
