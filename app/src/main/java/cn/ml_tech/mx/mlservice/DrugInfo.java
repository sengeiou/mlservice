package cn.ml_tech.mx.mlservice;

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

public class DrugInfo extends DataSupport {
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String enName;
    @Column(nullable = false)
    String pinYin;
    @Column(nullable = false)
    int containterId;
    @Column(nullable = false)
    int factoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public int getContainterId() {
        return containterId;
    }

    public void setContainterId(int containterId) {
        this.containterId = containterId;
    }

    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    public DrugContainer getDrugContainer() {
        return drugContainer;
    }

    public void setDrugContainer(DrugContainer drugContainer) {
        this.drugContainer = drugContainer;
    }

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    DrugContainer drugContainer;
    Factory factory;
}
