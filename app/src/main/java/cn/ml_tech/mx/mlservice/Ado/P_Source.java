package cn.ml_tech.mx.mlservice.Ado;

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

public class P_Source extends DataSupport {
    @Column(unique = true,nullable = false)
    private   String id;
    @Column(nullable = false)
    private  String url;
    @Column(nullable = false)
    private  String title;
    @Column(nullable = false)
    private  int parentId;
    private  P_Module p_module;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public P_Module getP_module() {
        return p_module;
    }

    public void setP_module(P_Module p_module) {
        this.p_module = p_module;
    }
}
