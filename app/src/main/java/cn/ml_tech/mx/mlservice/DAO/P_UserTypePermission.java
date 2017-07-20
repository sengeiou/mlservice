package cn.ml_tech.mx.mlservice.DAO;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

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
*create at  2017/5/24 13:26
CREATE TABLE [p_usertypepermission](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [righttype] integer NOT NULL,
    [usertype] integer NOT NULL,
    [p_sourceoperator_id] integer);


*/

public class P_UserTypePermission extends DataSupport {
    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private int righttype;
    @Column(nullable = false)
    private long usertype;
    @Column(nullable = false)
    private long p_sourceoperator_id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getRighttype() {
        return righttype;
    }

    public void setRighttype(int righttype) {
        this.righttype = righttype;
    }

    public long getUsertype() {
        return usertype;
    }

    public void setUsertype(long usertype) {
        this.usertype = usertype;
    }

    public long getP_sourceoperator_id() {
        return p_sourceoperator_id;
    }

    public void setP_sourceoperator_id(long p_sourceoperator_id) {
        this.p_sourceoperator_id = p_sourceoperator_id;
    }
}