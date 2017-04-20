package cn.ml_tech.mx.mlservice;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 *
 CREATE TABLE user
 (
 id integer  primary key AUTOINCREMENT not null,
 userId TEXT not null unique,
 userName TEXT not null ,
 userPassword TEXT not null,
 userPermission numeric not null,
 userEnable numeric not null

 );

 */

public class User extends DataSupport {
    @Column(unique = true, nullable = false)
    private  String userId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String userPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(int userPermission) {
        this.userPermission = userPermission;
    }

    public int getUserEnable() {
        return userEnable;
    }

    public void setUserEnable(int userEnable) {
        this.userEnable = userEnable;
    }

    private int userPermission;
    private int userEnable;

}