package cn.ml_tech.mx.mlservice.Ado;

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

public class DrugStandardParam extends DataSupport {
    @Column(unique = true,nullable = false)
    private int id;
    @Column(nullable = false)
    private int standardId;
    @Column(nullable = false)
    private String paramName;
    @Column(nullable = false)
    private double paramValue;
    @Column(nullable = false,defaultValue = "0")
    private int type;
    private DrugStandard drugStandard;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
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

    public DrugStandard getDrugStandard() {
        return drugStandard;
    }

    public void setDrugStandard(DrugStandard drugStandard) {
        this.drugStandard = drugStandard;
    }
}
