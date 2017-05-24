package cn.ml_tech.mx.mlservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import cn.ml_tech.mx.mlservice.DAO.DrugInfo;
import cn.ml_tech.mx.mlservice.Bean.User;
import cn.ml_tech.mx.mlservice.DAO.UserType;

import static android.content.ContentValues.TAG;


public class MotorServices extends Service {
    private void log(String message) {
        Log.v("MotorServices", message);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("Received start command.");
        return START_STICKY;
    }
    public List<DrugControls> mDrugControls = new ArrayList<>();
    private final IMlService.Stub mBinder = new IMlService.Stub() {
        @Override
        public void addMotorControl(MotorControl mControl) throws RemoteException {
            log("Received addMotorControl.");
        }
        @Override
        public boolean checkAuthority(String name, String password) throws RemoteException{
            log(name);
            log(password);
            List<User> users = DataSupport.where("userName = ? and userPassword = ?", name, password).find(User.class);
            return !users.isEmpty();
        }
        @Override
        public boolean addDrugInfo(String name, String enName, String pinYin, int containterId, int factoryId) throws RemoteException {
            DrugInfo drugInfo = new DrugInfo();
            drugInfo.setName(name);
            drugInfo.setEnname(enName);
            drugInfo.setPinyin(pinYin);
            drugInfo.setCreatedate(new Date());
            drugInfo.save();
            log("add Drug info....");
            return true;
        }
        @Override
        public List<DrugControls> queryDrugControl() throws RemoteException {
            List<DrugInfo> mDrugInfo = DataSupport.findAll(DrugInfo.class);
            for (DrugInfo drugInfo:mDrugInfo) {
                DrugControls drugControls = new DrugControls(drugInfo.getName(), drugInfo.getEnname(), drugInfo.getPinyin());
                mDrugControls.add(drugControls);
            }
            return mDrugControls;
        }

        @Override
        public List<cn.ml_tech.mx.mlservice.Bean.User> getUserList() throws RemoteException {
            List<User>list= new ArrayList<User>();
            Connector.getDatabase();
            List<cn.ml_tech.mx.mlservice.DAO.User>listDao=  DataSupport.
                    select(new String[]{"id","userid","username","usertype_id","userenable"}).
                    where("isdeprecated=?","0")
                    .find(cn.ml_tech.mx.mlservice.DAO.User.class);
            for(cn.ml_tech.mx.mlservice.DAO.User user:listDao)
            {
                User userBean=new User();
                if(user.getUserEnable()==0)
                    userBean.setEnable(false);
                else
                    userBean.setEnable(true);
                userBean.setUserName(user.getUserName());
                userBean.setUserId(user.getUserId());
                userBean.setDeparecate(user.isDeprecated());
                cn.ml_tech.mx.mlservice.Bean.UserType userType=new cn.ml_tech.mx.mlservice.Bean.UserType((int) user.getUsertype_id(),"");
                userBean.setUserType(userType);
                list.add(userBean);
            }
            Log.d(TAG, "getUserList: "+String.valueOf(listDao.size()));
            Log.d(TAG, "getUserList: "+String.valueOf(list.size()));

            return list;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        log("Received binding.");
        Connector.getDatabase();
        if(!DataSupport.isExist(UserType.class))
        {
            UserType userType=new UserType();
            userType.setId(0);
            userType.setName("超级管理员");
            userType.save();
            userType.clearSavedState();
            userType.setId(1);
            userType.setName("管理员");
            userType.save();
            userType.clearSavedState();
            userType.setId(2);
            userType.setName("操作员");
            userType.save();
        }
        if(!DataSupport.isExist(cn.ml_tech.mx.mlservice.DAO.User.class))
        {
            cn.ml_tech.mx.mlservice.DAO.User user=new cn.ml_tech.mx.mlservice.DAO.User();
            user.setUsertype_id(1);
            user.setUserId("Admin");
            user.setUserEnable(1);
            user.setUserName("AdminName");
            user.setUserPassword("Admin");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setCreateDate(simpleDateFormat.format(new Date()));
            user.save();

        }

        return mBinder;
    }



}
