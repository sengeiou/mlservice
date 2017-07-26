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
*create at  2017/5/24 13:17
CREATE TABLE [drugstandard](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [deprecate] integer NOT NULL DEFAULT false,
    [name] text NOT NULL UNIQUE,
    [druginfo_id] integer);


*/

public class DrugStandard extends DataSupport {
    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false, defaultValue = "false")
    private boolean deprecate;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private long druginfo_id;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDruginfo_id() {
        return druginfo_id;
    }

    public void setDruginfo_id(long druginfo_id) {
        this.druginfo_id = druginfo_id;
    }
}