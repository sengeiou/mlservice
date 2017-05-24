package cn.ml_tech.mx.mlservice.DAO;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE "druginfo" (
 * id integer primary key AUTOINCREMENT not null,
 * name text not null ,
 * enName text not null,
 * pinYin text not null,
 * containerId integer not null,
 * factoryId integer not null,
 * foreign key(containerId) REFERENCES drugContainer(id),
 * foreign key (factoryId) REFERENCES factory(id)
 * );

 */
/*
*
*@author wl
*create at  2017/5/24 13:19
CREATE TABLE [drugstandardparam](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [paramname] text NOT NULL,
    [paramvalue] real NOT NULL,
    [type] integer NOT NULL DEFAULT 0,
    [drugstandard_id] integer);


*/

public class DrugStandardParam extends DataSupport {
    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private String paramName;
    @Column(nullable = false)
    private double paramValue;
    @Column(nullable = false, defaultValue = "0")
    private int type;
    @Column(nullable = false)
    private long drugstandard_id;

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

    public long getDrugstandard_id() {
        return drugstandard_id;
    }

    public void setDrugstandard_id(long drugstandard_id) {
        this.drugstandard_id = drugstandard_id;
    }
}