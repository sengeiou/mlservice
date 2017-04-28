package cn.ml_tech.mx.mlservice.Ado;

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

public class P_UserTypePermission extends DataSupport {
    @Column(unique = true,nullable = false)
    private String id;
    @Column(nullable = false)
    private  int usertype;
    @Column(nullable = false)
    private  int sourceoperatorid;
    @Column(nullable = false)
    private int righttype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public int getSourceoperatorid() {
        return sourceoperatorid;
    }

    public void setSourceoperatorid(int sourceoperatorid) {
        this.sourceoperatorid = sourceoperatorid;
    }

    public int getRighttype() {
        return righttype;
    }

    public void setRighttype(int righttype) {
        this.righttype = righttype;
    }
}
