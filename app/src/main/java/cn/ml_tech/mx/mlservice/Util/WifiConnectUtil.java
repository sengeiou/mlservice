package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.ml_tech.mx.mlservice.DAO.MobileUser;
import cn.ml_tech.mx.mlservice.DAO.User;
import cn.ml_tech.mx.mlservice.base.MlServerApplication;
import cn.ml_tech.mx.mlservice.base.SocketModule;

/**
 * 创建时间: 2017/12/26
 * 创建人: Administrator
 * 功能描述:
 */

public class WifiConnectUtil {
    private static WifiConnectUtil wifiConnectUtil;
    private MlServerApplication mlServerApplication;
    private String ipAddress = "";
    private ExecutorService threadPool;//线程池
    private ServerSocket serverSocket;
    private ToastUtil toastUtil;
    private Context context;
    private PrintWriter printWriter;
    private InputStream inputStream;
    private Gson gson;
    private Operate operate;
    public WifiConnectUtil(Context context) {
        this.context = context;
        mlServerApplication = (MlServerApplication) context.getApplicationContext();
        gson = mlServerApplication.getGson();
        toastUtil = ToastUtil.getInstance(context);
        ipAddress = "192.168.3.135";
        threadPool = Executors.newCachedThreadPool();
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(8888);
                    while (true) {
                        LogUtil.out(LogUtil.Debug, "等待连接");
                        Socket server = serverSocket.accept();
                        printWriter = new PrintWriter(server.getOutputStream(), true);
                        LogUtil.out(LogUtil.Debug, "链接成功");
                        printWriter.println(MlConCommonUtil.CONNECTSUCESS);
                        inputStream = server.getInputStream();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.out(LogUtil.Debug, "io异常");
                }
            }
        });

    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public static WifiConnectUtil getWifiConnectUtil(Context context) {
        if (wifiConnectUtil == null)
            wifiConnectUtil = new WifiConnectUtil(context);
        return wifiConnectUtil;
    }

    public void startObserver(Operate operate) {
        this.operate = operate;
        LogUtil.out(LogUtil.Debug, "startObserver");
        new Thread() {
            @Override
            public void run() {
                super.run();
                int num, te = 0;
                while (true) {
                    try {
                        if (inputStream != null) {
                            if (inputStream.available() != 0) {
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                String res = bufferedReader.readLine();
                                SocketModule socketModule = gson.fromJson(res, SocketModule.class);
                               handlerOperate(socketModule);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogUtil.out(LogUtil.Debug, "startObserver IOException");
                    }
                }
            }
        }.start();
    }


    private void handlerOperate(SocketModule socketModule) {
       String operateType = socketModule.getOperateType();
        switch (operateType){
            case MlConCommonUtil.LOGIN:
                socketModule =  operate.login(socketModule);
        }
      String res =  gson.toJson(socketModule);
        printWriter.println(res);
    }

    public interface Operate{
         SocketModule login(SocketModule socketModule);
    }
}
