package cn.ml_tech.mx.mlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

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
            drugInfo.setEnName(enName);
            drugInfo.setPinYin(pinYin);
            drugInfo.setContainterId(containterId);
            drugInfo.setFactoryId(factoryId);
            drugInfo.save();
            log("add Drug info....");
            return true;
        }

        @Override
        public List<DrugControls> queryDrugControl() throws RemoteException {
            List<DrugInfo> mDrugInfo = DataSupport.findAll(DrugInfo.class);
            for (DrugInfo drugInfo:mDrugInfo) {
                DrugControls drugControls = new DrugControls(drugInfo.getName(), drugInfo.getEnName(), drugInfo.getPinYin());
                mDrugControls.add(drugControls);
            }
            return mDrugControls;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        log("Received binding.");
        Connector.getDatabase();
        User defaultUser = new User();
        defaultUser.setUserEnable(1);
        defaultUser.setUserName("Name");
        defaultUser.setUserPassword("Password");
        defaultUser.setUserId("1");
        defaultUser.save();
        return mBinder;
    }



}
