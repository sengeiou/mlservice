package cn.ml_tech.mx.mlservice.Util;

import java.util.Date;
import java.util.List;

import cn.ml_tech.mx.mlservice.DAO.LoginLog;
import cn.ml_tech.mx.mlservice.DAO.User;

import static org.litepal.crud.DataSupport.where;

/**
 * Created by zhongwang on 2018/1/23.
 */

public class DataUtil {
    private static DataUtil dataUtil;
    private String user_id;
    private long userid;
    private long typeId;

    private DataUtil(){}
    public static DataUtil getInstance(){
        if(dataUtil==null)
            dataUtil = new DataUtil();
        return dataUtil;
    }
    public boolean checkAuthority(String name, String password){

        List<User> users = where("userName = ? and userPassword = ?", name, password).find(cn.ml_tech.mx.mlservice.DAO.User.class);
         if (users.size() != 0) {
            user_id = users.get(0).getUserId();
            userid = users.get(0).getId();
            typeId = users.get(0).getUsertype_id();
        }
        LoginLog loginLog = new LoginLog();
        loginLog.setUser_id(userid);
        loginLog.setLoginDateTime(new Date());
        loginLog.save();
        return users.size() == 0 ? false : true;
    }
}
