package cn.ml_tech.mx.mlservice.DAO;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * CREATE TABLE factory
 (
 id integer primary key AUTOINCREMENT not null,
 name text not null unique,
 address text not null,
 phone text,
 fax text,
 mail text
 contactName text
 contactPhone text,
 webSite text
 , province_code TEXT default NULL, city_code TEXT default NULL, area_code TEXT default NULL, contactName text, contactPhone text);
 */
/*
*
*@author wl
*create at  2017/5/24 13:21
CREATE TABLE [loginlog](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [logindatetime] integer NOT NULL,
    [user_id] integer);


*/

public class LoginLog extends DataSupport {
    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private Date loginDateTime;
    @Column(nullable = false)
    private long user_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(Date loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
