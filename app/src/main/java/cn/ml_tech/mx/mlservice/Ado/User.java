package cn.ml_tech.mx.mlservice.Ado;

import android.support.annotation.Nullable;

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
    private  int id;
    @Column(unique = true, nullable = false)
    private  String userId;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Column(nullable = false)
    private String userPassword;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private int userPermission;
    @Column(nullable = false)
    private int userEnable;
    @Column(nullable = false,defaultValue="false")
    private boolean isDeprecated;
    private  UserType userType;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public void setDeprecated(boolean deprecated) {
        isDeprecated = deprecated;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}