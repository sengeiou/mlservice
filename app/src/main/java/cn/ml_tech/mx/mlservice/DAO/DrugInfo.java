package cn.ml_tech.mx.mlservice.DAO;

import android.icu.text.DateFormat;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
*create at  2017/5/24 13:12
CREATE TABLE [druginfo](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [createdate] integer NOT NULL,
    [deprecate] integer NOT NULL,
    [enname] text NOT NULL,
    [name] text NOT NULL,
    [pinyin] text NOT NULL,
    [user_id] integer,
    [drugcontainer_id] integer,
    [factory_id] integer);


*/

public class DrugInfo extends DataSupport {
    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private Date createdate;
    @Column(nullable = false, defaultValue = "false")
    private boolean deprecate;
    @Column(nullable = false)
    private String enname;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String pinyin;
    @Column(nullable = false)
    private long user_id;
    @Column(nullable = false)
    private long drugcontainer_id;
    @Column(nullable = false)
    private long factory_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getDrugcontainer_id() {
        return drugcontainer_id;
    }

    public void setDrugcontainer_id(long drugcontainer_id) {
        this.drugcontainer_id = drugcontainer_id;
    }

    public long getFactory_id() {
        return factory_id;
    }

    public void setFactory_id(long factory_id) {
        this.factory_id = factory_id;
    }
}