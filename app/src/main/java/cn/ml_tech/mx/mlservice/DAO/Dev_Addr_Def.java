package cn.ml_tech.mx.mlservice.DAO;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE drugContainer
 (
 id integer primary key AUTOINCREMENT   not null,
 name text not null unique,
 type  INTEGER not null,
 specification INTEGER not null,
 diameter REAL not null,
 height REAL,
 trayID INTEGER not null,
 srcTime REAL not null,
 stpTime REAL not null,
 channelValue1 REAL not null,
 channelValue2 REAL not null,
 channelValue3 REAL not null,
 channelValue4 REAL not null,
 shadeParam REAL not null,
 rotateSpeed INTEGER NOT NULL DEFAULT 4500,
 sendParam REAL not null,
 foreign key (specification) REFERENCES specificationType(id),
 foreign key (trayID) REFERENCES tray(id)
 );
 */
/*
*
*@author wl
*create at  2017/5/24 13:03
CREATE TABLE [dev_addr_def](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [access] integer NOT NULL,
    [address] integer NOT NULL,
    [convert] integer NOT NULL DEFAULT 0,
    [name] text NOT NULL,
    [size] integer NOT NULL);


*/

public class Dev_Addr_Def extends DataSupport {
    @Column(unique = true,nullable = false)
   private long id;
    @Column( nullable = false)
    private  String name;
    @Column( nullable = false)
    private  int size;
    @Column( nullable = false)
    private int address;
    @Column(nullable = false)
    private int access;
    @Column( nullable = false,defaultValue = "0")
    private  int convert;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public int getConvert() {
        return convert;
    }

    public void setConvert(int convert) {
        this.convert = convert;
    }
}
