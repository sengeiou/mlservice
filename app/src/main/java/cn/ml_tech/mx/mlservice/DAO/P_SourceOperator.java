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
*create at  2017/5/24 13:24
CREATE TABLE [p_sourceoperator](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [operatorid] text NOT NULL,
    [sourceid] text NOT NULL,
    [p_source_id] integer,
    [p_operator_id] integer);


*/

public class P_SourceOperator extends DataSupport {
    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private long p_source_id;
    @Column(nullable = false)
    private long p_operator_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getP_source_id() {
        return p_source_id;
    }

    public void setP_source_id(long p_source_id) {
        this.p_source_id = p_source_id;
    }

    public long getP_operator_id() {
        return p_operator_id;
    }

    public void setP_operator_id(long p_operator_id) {
        this.p_operator_id = p_operator_id;
    }
}