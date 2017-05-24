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


public class DevParam extends DataSupport {
    @Column(unique = true,nullable = false)
    private  long id;
    @Column( nullable = false)
    private String paramName;
    @Column(nullable = false)
    private double paramValue;
    @Column( nullable = false,defaultValue = "0")
    private int type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public double getParamValue() {
        return paramValue;
    }

    public void setParamValue(double paramValue) {
        this.paramValue = paramValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
