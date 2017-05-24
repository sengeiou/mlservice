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

public class Hat_Area extends DataSupport {
    @Column(unique = true,nullable = false)
    private  String id;
    @Column(defaultValue = "NULL")
    private  String areaID;
    @Column(defaultValue = "NULL")
    private String area;
    @Column(defaultValue = "NULL")
    private String father;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }
}
