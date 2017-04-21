package cn.ml_tech.mx.mlservice;

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
    private int displayId;
    @Column(unique = true, nullable = false)
    private String icId;
    @Column(nullable = false)
    private float innerDiameter;

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

    public float getInnerDiameter() {
        return innerDiameter;
    }

    public void setInnerDiameter(float innerDiameter) {
        this.innerDiameter = innerDiameter;
    }

    public float getEternalDiameter() {
        return eternalDiameter;
    }

    public void setEternalDiameter(float eternalDiameter) {
        this.eternalDiameter = eternalDiameter;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Column(nullable = false)

    private float eternalDiameter;
    @Column(nullable = false)
    private float diameter;
    @Column(nullable = false)
    private String desc;
}
