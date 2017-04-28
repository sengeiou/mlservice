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

public class P_SourceOperator extends DataSupport {
    @Column(unique = true,nullable = false)
    private String id;
    @Column(nullable = false)
    private String sourceid;
    @Column(nullable = false)
    private  String operatorid;
    private  P_Source p_source;
    private  P_Operator p_operator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public P_Source getP_source() {
        return p_source;
    }

    public void setP_source(P_Source p_source) {
        this.p_source = p_source;
    }

    public P_Operator getP_operator() {
        return p_operator;
    }

    public void setP_operator(P_Operator p_operator) {
        this.p_operator = p_operator;
    }
}
