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

public class DrugInfo extends DataSupport {
    @Column(unique = true,nullable = false)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String enName;
    @Column(nullable = false)
    private String pinYin;
    @Column(nullable = false)
    private int containterId;
    @Column(nullable = false)
    private int factoryId;
    @Column(nullable = false)
    private int createUserId;
    @Column(nullable = false)
    private boolean deprecate;
    private   DrugContainer drugContainer;
    private  Factory factory;
    private   User user;
    private List<DrugParam>drugParamList=new ArrayList<DrugParam>();

    public boolean isDeprecate() {
        return deprecate;
    }

    public List<DrugParam> getDrugParamList() {
        return drugParamList;
    }

    public void setDrugParamList(List<DrugParam> drugParamList) {
        this.drugParamList = drugParamList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }


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


}
