package cn.ml_tech.mx.mlservice.Ado;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE tray (
 "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 "displayId" INTEGER UNIQUE NOT NULL,
 "icId" TEXT UNIQUE  NOT NULL,
 "innerDiameter" REAL NOT NULL,
 "externalDiameter" REAL NOT NULL,
 "diameter" REAL NOT NULL,
 "desc" TEXT NOT NULL
 );
 */

public class Tray extends DataSupport {
    @Column(unique = true, nullable = false)
    private int id;
    @Column(unique = true, nullable = false)
    private int displayId;
    @Column(unique = true, nullable = false)
    private String icId;
    @Column(nullable = false)
    private double innerDiameter;
    @Column(nullable = false)
    private double externalDiameter;
    @Column(nullable = false)
    private double diameter;
    @Column(nullable = false)
    private String desc;
    @Column(nullable = false,defaultValue = "false")
    private boolean deprecate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDisplayId() {
        return displayId;
    }

    public void setDisplayId(int displayId) {
        this.displayId = displayId;
    }

    public String getIcId() {
        return icId;
    }

    public void setIcId(String icId) {
        this.icId = icId;
    }

    public double getInnerDiameter() {
        return innerDiameter;
    }

    public void setInnerDiameter(double innerDiameter) {
        this.innerDiameter = innerDiameter;
    }

    public double getExternalDiameter() {
        return externalDiameter;
    }

    public void setExternalDiameter(double externalDiameter) {
        this.externalDiameter = externalDiameter;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }
}
