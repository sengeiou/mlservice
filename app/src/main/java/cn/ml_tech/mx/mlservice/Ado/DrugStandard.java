package cn.ml_tech.mx.mlservice.Ado;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
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

public class DrugStandard extends DataSupport {

    @Column(nullable = false)
    private int id;
    @Column(unique = true,nullable = false)
    private String name;
    @Column(nullable = false)
    private  String drugId;
    @Column(nullable = false,defaultValue = "false")
    private  boolean deprecate;
    private DrugInfo info;
    private  List<DrugParam>drugParamList=new ArrayList<DrugParam>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public DrugInfo getInfo() {
        return info;
    }

    public void setInfo(DrugInfo info) {
        this.info = info;
    }

    public List<DrugParam> getDrugParamList() {
        return drugParamList;
    }

    public void setDrugParamList(List<DrugParam> drugParamList) {
        this.drugParamList = drugParamList;
    }
}
