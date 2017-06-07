package cn.ml_tech.mx.mlservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.SimpleFormatter;

import cn.ml_tech.mx.mlservice.DAO.DevParam;
import cn.ml_tech.mx.mlservice.DAO.DevUuid;
import cn.ml_tech.mx.mlservice.DAO.DrugInfo;
import cn.ml_tech.mx.mlservice.Bean.User;
import cn.ml_tech.mx.mlservice.DAO.Tray;
import cn.ml_tech.mx.mlservice.DAO.UserType;

import static android.R.id.list;
import static android.R.id.primary;
import static android.content.ContentValues.TAG;


public class MotorServices extends Service {
    private List<DevParam>devParamList;

    MotorServices()
    {
        initMemberData();
        getDevParams();
        Log.d(TAG, "MotorServices: "+String.valueOf(devParamList.size()));
    }
    private void initMemberData()
    {
        devParamList=new ArrayList<DevParam>();
    }
    public List<DevParam>getDevParams()
    {
        devParamList.clear();
        devParamList=DataSupport.select("id","paramName","paramValue","type")
                // .where("paramName=?",paramName)
                // .where("type=?",String.valueOf(type))
                .order(" id asc")
                .find(DevParam.class);
        return devParamList;
    }
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

        @Override
        public List<DevParam> getDeviceParamList(int type) throws RemoteException {
            List<DevParam> list =new ArrayList<DevParam>();
            for(DevParam param:devParamList)
            {
                if(type==param.getType())list.add(param);
            }
            return list;

        }

        @Override
        public void setDeviceParamList(List<DevParam> list) throws RemoteException {
            for (DevParam param:list
                    ) {
                param.saveOrUpdate("paramName=?",param.getParamName());
            }
            getDevParams();
        }

        @Override
        public double getDeviceParams(String paramName,int type) throws RemoteException {
            for(DevParam param:devParamList)
            {
                if(paramName.equals(param.getParamName())&&type==param.getType())
                    return param.getParamValue();
            }
            return 0;
        }

        @Override
        public DevUuid getDeviceManagerInfo() throws RemoteException {
            DevUuid devUuid=new DevUuid();
            devUuid=DataSupport.findFirst(DevUuid.class);
            return devUuid;
        }

        @Override
        public boolean setDeviceManagerInfo(DevUuid info) throws RemoteException {
            boolean flag=true;
            info.saveOrUpdate("id=?", String.valueOf(info.getId()));
            return flag;
        }

        @Override
        public String getTrayIcId() throws RemoteException {
            Random random = new Random();
            String string = String.valueOf(Math.abs(random.nextInt()));
            return string;
        }

        @Override
        public List<Tray> getTrayList() throws RemoteException {
            List<Tray>trayList=  DataSupport.findAll(Tray.class);
            return trayList;
        }

        @Override
        public Tray getTray(int id) throws RemoteException {
            Tray tray= DataSupport.find(Tray.class,id);
            return tray;
        }
        @Override
        public boolean setTray(Tray tray) throws RemoteException {
            tray.saveOrUpdate("displayId=?", String.valueOf(tray.getDisplayId()));
            return tray.isSaved();
        }

        @Override
        public boolean delTray(Tray tray) throws RemoteException {
            int r=0;
            r=DataSupport.deleteAll(Tray.class,"displayId=? and icId=?", String.valueOf(tray.getDisplayId()),tray.getIcId());
            if(r>0)return true;
            else return false;
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
