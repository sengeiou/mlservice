package cn.ml_tech.mx.mlservice.DAO;

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
/*
*
*@author wl
*create at  2017/5/24 13:28
CREATE TABLE [tray](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [deprecate] integer NOT NULL DEFAULT false,
    [diameter] real NOT NULL,
    [displayid] integer NOT NULL UNIQUE,
    [externaldiameter] real NOT NULL,
    [icid] text NOT NULL UNIQUE,
    [innerdiameter] real NOT NULL,
    [mark] text NOT NULL);


*/

public class Tray extends DataSupport {
    @Column(unique = true, nullable = false)
    private long id;
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
    private String mark;
    @Column(nullable = false, defaultValue = "false")
    private boolean deprecate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }
}
