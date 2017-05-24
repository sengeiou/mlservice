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
/*
*
*@author wl
*create at  2017/5/24 13:23
CREATE TABLE [p_source](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [title] text NOT NULL,
    [url] text NOT NULL,
    [p_module_id] integer);


*/

public class P_Source extends DataSupport {
    @Column(unique = true,nullable = false)
    private   long id;
    @Column(nullable = false)
    private  String url;
    @Column(nullable = false)
    private  String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}