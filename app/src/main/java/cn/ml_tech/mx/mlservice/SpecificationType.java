package cn.ml_tech.mx.mlservice;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by mx on 2017/4/21.
 */

public class SpecificationType extends DataSupport {
    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
