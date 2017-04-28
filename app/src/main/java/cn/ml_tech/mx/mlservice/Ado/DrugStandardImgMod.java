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

public class DrugStandardImgMod extends DataSupport {
    @Column(unique = true,nullable = false)
    private int id;
    @Column(nullable = false)
    private String imgMod0;
    @Column(nullable = false)
    private String imgMod1;
    @Column(nullable = false)
    private String imgMod2;
    @Column(nullable = false)
    private String imgMod3;
    @Column(nullable = false)
    private String imgMod4;
    @Column(nullable = false)
    private String imgMod5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgMod0() {
        return imgMod0;
    }

    public void setImgMod0(String imgMod0) {
        this.imgMod0 = imgMod0;
    }

    public String getImgMod1() {
        return imgMod1;
    }

    public void setImgMod1(String imgMod1) {
        this.imgMod1 = imgMod1;
    }

    public String getImgMod2() {
        return imgMod2;
    }

    public void setImgMod2(String imgMod2) {
        this.imgMod2 = imgMod2;
    }

    public String getImgMod3() {
        return imgMod3;
    }

    public void setImgMod3(String imgMod3) {
        this.imgMod3 = imgMod3;
    }

    public String getImgMod4() {
        return imgMod4;
    }

    public void setImgMod4(String imgMod4) {
        this.imgMod4 = imgMod4;
    }

    public String getImgMod5() {
        return imgMod5;
    }

    public void setImgMod5(String imgMod5) {
        this.imgMod5 = imgMod5;
    }
}
