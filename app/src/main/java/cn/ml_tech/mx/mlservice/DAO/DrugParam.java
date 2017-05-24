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
*create at  2017/5/24 13:15
CREATE TABLE [drugparam](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [paramname] text NOT NULL,
    [paramvalue] real NOT NULL,
    [type] integer NOT NULL DEFAULT 0,
    [druginfo_id] integer);


*/

public class DrugParam extends DataSupport {
    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private String paramname;
    @Column(nullable = false)
    private double paramvalue;
    @Column(nullable = false,defaultValue = "0")
    private int type;
    @Column(nullable = false)
    private long druginfo_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public double getParamvalue() {
        return paramvalue;
    }

    public void setParamvalue(double paramvalue) {
        this.paramvalue = paramvalue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDruginfo_id() {
        return druginfo_id;
    }

    public void setDruginfo_id(long druginfo_id) {
        this.druginfo_id = druginfo_id;
    }
}