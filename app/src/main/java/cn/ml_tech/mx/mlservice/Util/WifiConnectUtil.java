package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建时间: 2017/12/26
 * 创建人: Administrator
 * 功能描述:
 */

public class WifiConnectUtil {
    private static WifiConnectUtil wifiConnectUtil;
    private String ipAddress = "";
    private ExecutorService threadPool;//线程池
    private ServerSocket serverSocket;
    private ToastUtil toastUtil;
    private Context context;
    private PrintWriter printWriter;

    public WifiConnectUtil(Context context) {
        this.context = context;
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
                        printWriter = new PrintWriter(server.getOutputStream());
                        LogUtil.out(LogUtil.Debug, "链接成功");
                        printWriter.println("connect sucess");
                        printWriter.flush();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.out(LogUtil.Debug, "io异常");
                }
            }
        });

    }

    public static WifiConnectUtil getWifiConnectUtil(Context context) {
        if (wifiConnectUtil == null)
            wifiConnectUtil = new WifiConnectUtil(context);
        return wifiConnectUtil;
    }
}
