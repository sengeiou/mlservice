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

public class P_UserPermission extends DataSupport {
    @Column(unique = true,nullable = false)
    private int id;
    @Column(nullable = false)
    private  int userlogicid;
    @Column(nullable = false)
    private  int sourceoperatorid;
    @Column(nullable = false)
    private  int righttype;
    private  User user;
    private   P_SourceOperator p_sourceOperator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserlogicid() {
        return userlogicid;
    }

    public void setUserlogicid(int userlogicid) {
        this.userlogicid = userlogicid;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public P_SourceOperator getP_sourceOperator() {
        return p_sourceOperator;
    }

    public void setP_sourceOperator(P_SourceOperator p_sourceOperator) {
        this.p_sourceOperator = p_sourceOperator;
    }
}
