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

public class AuditTrail extends DataSupport {
    @Column( unique = true,nullable = false)
    private String id;
    @Column( nullable = false)
    private String userlogicid;

    @Column( nullable = false)
    private int event;
    @Column( nullable = false)
    private int info;
    @Column( nullable = false)
    private String time;
    @Column( nullable = false)
    private String mark;
    private  AuditTrailInfoType infoType;
    private AuditTrailEventType eventType;

    public AuditTrailInfoType getInfoType() {
        return infoType;
    }

    public void setInfoType(AuditTrailInfoType infoType) {
        this.infoType = infoType;
    }

    public AuditTrailEventType getEventType() {
        return eventType;
    }

    public void setEventType(AuditTrailEventType eventType) {
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserlogicid() {
        return userlogicid;
    }

    public void setUserlogicid(String userlogicid) {
        this.userlogicid = userlogicid;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }


}
