package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.ml_tech.mx.mlservice.MlMotor;
import cn.ml_tech.mx.mlservice.SerialPort;

/**
 * Created by zhongwang on 2017/8/23.
 */

public class MlMotorUtil {
    private static MlMotorUtil mlMotorUtil;
    private MlMotor mlMotor;
    private MlMotor.ReportDataReg reportDataReg;
    private MlMotor.ReportDataState reportDataState;
    private MlMotor.ReportDataVal reportDataVal;
    private static SerialPort rotaleBottlePort, readInfoPort;
    private InputStream readInputStream;
    Context context;
    public static final double WAVEPERMM = 0.0079375;
    private byte[] rotale = null;
    private int code = 0;

    /**
     * exampleCommonUtil.Device_Pressed, 0, 0.02, 50
     *
     * @param type     电机号
     * @param dir      方向
     * @param avgspeed 平均速度
     * @param distance 距离
     */
    public void operateMlMotor(int type, double dir, double avgspeed, double distance) {
        reportDataVal.setNum(type);
        reportDataVal.setDir((int) dir);
        reportDataVal.setAcc_time((int) ((distance / avgspeed) / 2));
        reportDataVal.setU16WaveNum((int) (distance / WAVEPERMM));
        reportDataVal.setSpeed((int) (((6350) / ((avgspeed) * 32 * 8)) - 1));
        reportDataVal.setDataType(1);
        mlMotor.motorControl(reportDataVal);

    }

    /**
     * @param speed 旋瓶速度
     */
    public void operateRotale(final int speed) {
        try {
            rotaleBottlePort = new SerialPort(new File("/dev/ttymxc2"), 19200, 1);
            final OutputStream rotaleOutputStream = rotaleBottlePort.getOutputStream();
            final byte[] bytes = {0x55, (byte) ((speed & 0xff00) >> 8), (byte) (speed & 0xff00)};
            rotaleOutputStream.write(bytes);
            rotaleOutputStream.close();
            rotaleBottlePort.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getTrayId(final AlertDialog alertDialog, final Intent intent) {
        operateRotale(260);

        try {
            readInfoPort = new SerialPort(new File("/dev/ttymxc1"), 19200, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread() {
            @Override
            public void run() {
                super.run();
                String res = "";
                int trayId = 0;
                byte[] tray = new byte[8];
                readInputStream = readInfoPort.getInputStream();
                int i = 0;
                while (true) {
                    i++;
                    Log.d("zw", "i = " + i);
                    if (i > 20000) {
                        operateRotale(0);
                        alertDialog.callback("读取托环失败");
                        break;
                    }
                    try {
                        trayId = readInputStream.read(tray);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (trayId > 0) {
                        Log.d("zw", "sucessful");
                        Log.d("zw", tray[0] + " " + tray[1] + " " + tray[2] + " " + tray[3] + " " + tray[4] + " " + tray[5] + " "
                                + tray[6] + " " + tray[7]);
                        if (tray[0] == -86 && tray[1] == -69
                                && tray[2] > 5 && tray[3] == 32) {
                            res = formatByte(tray[4]) + "" + formatByte(tray[5])
                                    + "" + formatByte(tray[6])
                                    + "" + formatByte(tray[7]);
                            operateRotale(0);
                            try {
                                readInputStream.close();
                                readInfoPort.close();
                                intent.setAction("com.trayid");
                                intent.putExtra("info", res);
                                context.sendBroadcast(intent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            break;
                        }

                    }

                }

            }
        }.start();


    }

    private MlMotorUtil() {
    }

    private MlMotorUtil(Context context) {
        mlMotor = new MlMotor();
        code = mlMotor.initMotor();
        reportDataReg = new MlMotor.ReportDataReg(0, 0, 0);
        reportDataState = new MlMotor.ReportDataState(new int[]{0}, 1);
        reportDataVal = new MlMotor.ReportDataVal(0, 0, 0, 0, 0, 0);
        this.context = context;
    }

    public static MlMotorUtil getInstance(Context context) {
        if (mlMotorUtil == null)
            mlMotorUtil = new MlMotorUtil(context);
        return mlMotorUtil;
    }

    public MlMotor getMlMotor() {
        return mlMotor;
    }

    public void setMlMotor(MlMotor mlMotor) {
        this.mlMotor = mlMotor;
    }

    public MlMotor.ReportDataReg getReportDataReg() {
        return reportDataReg;
    }

    public void setReportDataReg(MlMotor.ReportDataReg reportDataReg) {
        this.reportDataReg = reportDataReg;
    }

    public MlMotor.ReportDataState getReportDataState() {
        return reportDataState;
    }

    public void setReportDataState(MlMotor.ReportDataState reportDataState) {
        this.reportDataState = reportDataState;
    }

    public MlMotor.ReportDataVal getReportDataVal() {
        return reportDataVal;
    }

    public void setReportDataVal(MlMotor.ReportDataVal reportDataVal) {
        this.reportDataVal = reportDataVal;
    }

    public String format(byte b) {
        StringBuilder stringBuilder = new StringBuilder();
//        for (int j = 31; j > 0; j--) {
//            int t = (b & 0x80000000 >>> j) >>> (31 - j);
//
//                stringBuilder.append(t);
//
//        }

        return stringBuilder.toString();
    }

    public static String formatByte(byte a) {
        String res = Integer.toHexString(a);
        Log.d("Zw", "shu " + res);
        if (a < 0) {
            return res.substring(res.length() - 3);
        } else {
            return res;
        }
    }

    public static byte[] intToBytes(int value) {
        byte[] byte_src = new byte[4];
        byte_src[3] = (byte) ((value & 0xFF000000) >> 24);
        byte_src[2] = (byte) ((value & 0x00FF0000) >> 16);
        byte_src[1] = (byte) ((value & 0x0000FF00) >> 8);
        byte_src[0] = (byte) ((value & 0x000000FF));
        return byte_src;
    }

}
