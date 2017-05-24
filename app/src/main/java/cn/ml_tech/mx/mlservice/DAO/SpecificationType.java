package cn.ml_tech.mx.mlservice.DAO;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by mx on 2017/4/21.
 */
/*
*
*@author wl
*create at  2017/5/24 13:27
CREATE TABLE [specificationtype](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [name] text NOT NULL);


*/

public class SpecificationType extends DataSupport {
    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}