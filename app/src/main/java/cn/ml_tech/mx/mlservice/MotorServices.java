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
