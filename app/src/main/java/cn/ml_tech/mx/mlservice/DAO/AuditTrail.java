package cn.ml_tech.mx.mlservice.DAO;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 CREATE TABLE [audittrail](
 [id] integer PRIMARY KEY AUTOINCREMENT,
 [event] integer NOT NULL,
 [info] integer NOT NULL,
 [mark] text NOT NULL,
 [time] text NOT NULL,
 [userautoid] integer,
 [userlogicid] text ,
 [audittraileventtype_id] integer,
 [audittrailinfotype_id] integer);


 */

public class AuditTrail extends DataSupport {
    @Column(nullable = false,unique = true)
    private long id;
   @Column(nullable = false)
    private int event_id;
    @Column(nullable = false)
    private int info_id;
    @Column(nullable = false)
    private String mark;
    @Column(nullable = false)
    private String time;
    private int userauto_id;
    private String username;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserauto_id() {
        return userauto_id;
    }

    public void setUserauto_id(int userauto_id) {
        this.userauto_id = userauto_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
