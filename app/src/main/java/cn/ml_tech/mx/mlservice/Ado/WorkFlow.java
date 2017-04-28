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

public class WorkFlow extends DataSupport {
    @Column(nullable = false,unique = true)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String desc;
    @Column(nullable = false)
    private String prompt;
    @Column(nullable = false)
    private String begin_state;
    @Column(nullable = false)
    private  String end_state;
    @Column(nullable = false,defaultValue = "0")
    private  String parent;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getBegin_state() {
        return begin_state;
    }

    public void setBegin_state(String begin_state) {
        this.begin_state = begin_state;
    }

    public String getEnd_state() {
        return end_state;
    }

    public void setEnd_state(String end_state) {
        this.end_state = end_state;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
