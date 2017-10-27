package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import cn.ml_tech.mx.mlservice.DAO.DevParam;
import cn.ml_tech.mx.mlservice.DAO.Tray;
import cn.ml_tech.mx.mlservice.MlMotor;
import cn.ml_tech.mx.mlservice.SerialPort;

import static cn.ml_tech.mx.mlservice.Util.CommonUtil.AUTOEBUG_CALIBRATE;
import static cn.ml_tech.mx.mlservice.Util.CommonUtil.TRAYID_ID;
import static cn.ml_tech.mx.mlservice.Util.CommonUtil.TRAYID_RADIO;

/**
 * Created by zhongwang on 2017/8/23.
 */

public class MlMotorUtil {

    private static final double WAVEPERMM = 0.0079375;
    private static MlMotorUtil mlMotorUtil;
    private static MlMotor mlMotor;
    private MlMotor.ReportDataReg reportDataReg;
    private MlMotor.ReportDataVal reportDataVal;
    private static SerialPort rotaleBottlePort, readInfoPort;
    private InputStream readInputStream;
    private Context context;
    private byte[] rotale = null;
    private int code = 0;
    private Handler handler;
    private MlMotor.ReportDataState reportDataState;
    private int[] i = new int[8];
    private InputStreamReader inputStreamReader;
    private FileInputStream fileInputStream;
    private String content = "";
    private int num;
    private Handler ressetHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CommonUtil.Device_MachineHand:
                    // TODO: 2017/10/26 自动上瓶未有，复位时假设为自动上瓶
                    if (type == AUTOEBUG_CALIBRATE) {
                        Intent intent = new Intent("com.calibration");
                        intent.putExtra("state", 0);
                        context.sendBroadcast(intent);
                    }
                    Log.d("zw", "reset Device_Pressed");
                    motorReset(CommonUtil.Device_Pressed, ressetHandler);
                    break;
                case CommonUtil.Device_Pressed:
                    Log.d("zw", "reset Device_CatchHand");
                    motorReset(CommonUtil.Device_CatchHand, ressetHandler);
                    break;
                case CommonUtil.Device_CatchHand:
                    motorReset(CommonUtil.Device_ShadeLight, ressetHandler);
                    break;
                case CommonUtil.Device_ShadeLight:
                    // TODO: 2017/10/24 电机全部复位， 开始自动调试
                    if (type == AUTOEBUG_CALIBRATE) {
                        Intent intent = new Intent("com.calibration");
                        intent.putExtra("state", 1);
                        context.sendBroadcast(intent);
                    }
                    double distance = DataSupport.where("paramname = ?", "MachineHandDistance1").find(DevParam.class).get(0).getParamValue();
                    double speed = DataSupport.where("paramname = ?", "MachineHandSpeed").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_MachineHand, 1, speed, (int) distance, MOTORCATCH, stateHandler);
                    break;
            }
        }
    };

    public Handler getStateHandler() {
        return stateHandler;
    }

    private Handler stateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            double machineSpeed = DataSupport.where("paramname = ?", "MachineHandSpeed").find(DevParam.class).get(0).getParamValue();

            switch (msg.what) {
                case MOTORCATCH:
                    // TODO: 2017/10/24 抓瓶到位
                    int distance = (int) DataSupport.where("paramname = ?", "CatchHandDistance2").find(DevParam.class).get(0).getParamValue();
                    double catchSpeed = DataSupport.where("paramname = ?", "CatchHandSpeed").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_CatchHand, 1, catchSpeed, distance, CATCHFINISH, stateHandler);
                    break;
                case CATCHFINISH:
                    double machineHandMotorDetLocation = DataSupport.where("paramname = ?", "MachineHandDistance2").find(DevParam.class).get(0).getParamValue();
                    distance = (int) machineHandMotorDetLocation;
                    operateMotor(CommonUtil.Device_MachineHand, 0, machineSpeed, distance, ChECKSTATE, stateHandler);
                    break;
                case ChECKSTATE:
                    double catchParam1 = DataSupport.where("paramname = ?", "CatchHandDistance1").find(DevParam.class).get(0).getParamValue();
                    double speed = DataSupport.where("paramname = ?", "CatchHandSpeed").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_CatchHand, 0, speed, (int) catchParam1, LETBOTTLE, stateHandler);
                    break;
                case LETBOTTLE:
                    // TODO: 2017/10/26 发送广播通知界面更新 /固定样品
                    if (type == AUTOEBUG_CALIBRATE) {
                        Intent intent = new Intent("com.calibration");
                        intent.putExtra("state", 2);
                        context.sendBroadcast(intent);
                    }
                    double pressedSpeed = DataSupport.where("paramname = ?", "PressedSpeed").find(DevParam.class).get(0).getParamValue();
                    double pressedDistance = DataSupport.where("paramname = ?", "PressedDistance1").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_Pressed, 1, pressedSpeed, (int) pressedDistance, PRESSEDFINISH, stateHandler);
                    break;
                case PRESSEDFINISH:
                    // TODO: 2017/10/24 压瓶到位后
                    double catchspeed = DataSupport.where("paramname = ?", "CatchHandSpeed").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_CatchHand, 0, catchspeed,
                            (int) DataSupport.where("paramname = ?", "CatchHandDistance2").find(DevParam.class).get(0).getParamValue(),
                            CATCHRESET, stateHandler);
                    break;
                case CATCHRESET:
                    // TODO: 2017/10/24 抓瓶手完全放开后
                    Log.d("zw", "抓瓶手完全放开后");
                    int dis = (int) DataSupport.where("paramname = ?", "MachineHandDistance3").find(DevParam.class).get(0).getParamValue();
                    double maincheSpeed = DataSupport.where("paramname = ?", "MachineHandSpeed").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_MachineHand, 0, 0.02, dis, MACHINEWAIT, stateHandler);
                    break;
                case MACHINEWAIT:
                    operateMotor(CommonUtil.Device_ShadeLight, 1,
                            DataSupport.where("paramname = ?", "ShadeLightSpeed").find(DevParam.class).get(0).getParamValue(),
                            (int) DataSupport.where("paramname = ?", "ShadeLightDistance").find(DevParam.class).get(0).getParamValue(),
                            SHADEFINISH, stateHandler);
                    break;
                case SHADEFINISH:
                    if (type == AUTOEBUG_CALIBRATE) {
                        Intent intent = new Intent("com.calibration");
                        intent.putExtra("state", 3);
                        context.sendBroadcast(intent);
                    }
                    new Thread() {
                        public void run() {
                            super.run();
                            mlMotorUtil.getMlMotor().motorLightOn();
                            mlMotorUtil.operateRotale(260);
                            try {
                                Thread.sleep(3000);
                                mlMotorUtil.operateRotale(0);
                                mlMotorUtil.getMlMotor().motorLightOff();

                                stateHandler.sendEmptyMessage(ROTALEFINISH);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                case ROTALEFINISH:
                    if (type == AUTOEBUG_CALIBRATE) {
                        Intent intent = new Intent("com.calibration");
                        intent.putExtra("state", 5);
                        intent.putExtra("standard40", "4.82px");
                        intent.putExtra("standard50", "4.82px");
                        intent.putExtra("standard60", "4.82px");
                        intent.putExtra("variance40", "3.29");
                        intent.putExtra("variance60", "18.29");
                        intent.putExtra("statime", "0.00s");
                        intent.putExtra("stotime", "0.00s");
                        intent.putExtra("stpstate", "normal");
                        intent.putExtra("stostate", "normal");
                        intent.putExtra("colorcoefficient", "18.29");
                        context.sendBroadcast(intent);
                    }
                    operateMotor(CommonUtil.Device_MachineHand, 1,
                            DataSupport.where("paramname = ?", "MachineHandSpeed").find(DevParam.class).get(0).getParamValue(),
                            (int) DataSupport.where("paramname = ?", "MachineHandDistance3").find(DevParam.class).get(0).getParamValue(), CHECKLOCATION, stateHandler);
                    break;
                case CHECKLOCATION:
                    if (type == AUTOEBUG_CALIBRATE) {
                        Intent intent = new Intent("com.calibration");
                        intent.putExtra("state", 6);
                        context.sendBroadcast(intent);
                    }
                    operateMotor(CommonUtil.Device_CatchHand, 1,
                            DataSupport.where("paramname = ?", "CatchHandSpeed").find(DevParam.class).get(0).getParamValue(),
                            (int) DataSupport.where("paramname = ?", "CatchHandDistance2").find(DevParam.class).get(0).getParamValue()
                            , CATCHLOCATION, stateHandler);
                    break;
                case CATCHLOCATION:
                    motorReset(CommonUtil.Device_Pressed, stateHandler);
                    break;
                case CommonUtil.Device_Pressed:
                    motorReset(CommonUtil.Device_ShadeLight, stateHandler);
                    break;
                case CommonUtil.Device_ShadeLight:
                    operateMotor(CommonUtil.Device_MachineHand, 0,
                            DataSupport.where("paramname = ?", "MachineHandSpeed").find(DevParam.class).get(0).getParamValue(),
                            (int) DataSupport.where("paramname = ?", "MachineHandDistance4").find(DevParam.class).get(0).getParamValue(), CATCHWAIT, stateHandler);
                    break;
                case CATCHWAIT:
                    operateMotor(CommonUtil.Device_CatchHand, 0,
                            DataSupport.where("paramname = ?", "CatchHandSpeed").find(DevParam.class).get(0).getParamValue(),
                            (int) DataSupport.where("paramname = ?", "CatchHandDistance1").find(DevParam.class).get(0).getParamValue(), WAITLOCATION, stateHandler);

                    break;
                case WAITLOCATION:
                    motorReset(CommonUtil.Device_CatchHand, stateHandler);
                    break;
                case CommonUtil.Device_CatchHand:
                    motorReset(CommonUtil.Device_MachineHand, stateHandler);
                    num--;
                    if (num == 0) {
                        new Thread() {
                            public void run() {
                                super.run();
                                try {
                                    Thread.sleep(2000);
                                    if (type == AUTOEBUG_CALIBRATE) {
                                        Intent intent = new Intent("com.calibration");
                                        intent.putExtra("state", 7);
                                        context.sendBroadcast(intent);
                                    } else {
                                        handler.sendEmptyMessage(1);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    } else {
                        motorReset(CommonUtil.Device_MachineHand, ressetHandler);
                    }
                    break;
            }
        }
    };
    private int type;

    /**
     * exampleCommonUtil.Device_Pressed, 0, 0.02, 50
     *
     * @param type     电机号
     * @param dir      方向
     * @param avgspeed 平均速度
     * @param distance 距离
     */
    public void operateMlMotor(int type, int dir, double avgspeed, int distance) {
        if (mlMotor == null) {
            mlMotor = new MlMotor();
            code = mlMotor.initMotor();
        }
        reportDataVal.setNum(type);
        reportDataVal.setDir(dir);
        reportDataVal.setAcc_time((int) ((distance / avgspeed) / 2));
        reportDataVal.setU16WaveNum((int) (distance / WAVEPERMM));
        reportDataVal.setSpeed((int) (((6350) / ((avgspeed) * 32 * 8)) - 1));
        reportDataVal.setDataType(1);
        mlMotor.motorControl(reportDataVal);

    }

    public void realease() {
        mlMotor.uninitMotor(code);
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

    public void motorReset(int num) {
        if (mlMotor == null) {
            mlMotor = new MlMotor();
            code = mlMotor.initMotor();
        }
        mlMotor.motorReset(new MlMotor.ReportDataVal(num, 0, 0x410, 0, 0, 0));
    }

    /**
     * @param alertDialog 用来通知客户端 检测托环信息失败
     * @param intent      用来发送广播给客户端
     * @param type        参数为 TRAY_ID 获取芯片号 TRAY_RADIO 获取直径
     */
    public void getTrayId(final AlertDialog alertDialog, final Intent intent, final int type) {
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

                        if (tray[0] == -86 && tray[1] == -69
                                && tray[2] > 5 && tray[3] == 32) {
                            res = formatByte(tray[4]) + "" + formatByte(tray[5])
                                    + "" + formatByte(tray[6])
                                    + "" + formatByte(tray[7]);
                            operateRotale(0);
                            try {
                                readInputStream.close();
                                readInfoPort.close();
                                switch (type) {
                                    case TRAYID_ID:
                                        intent.setAction("com.trayid");
                                        intent.putExtra("info", res);
                                        context.sendBroadcast(intent);
                                        break;
                                    case TRAYID_RADIO:
                                        List<Tray> trayList = DataSupport.where("icid=?", res + "").find(Tray.class);
                                        if (trayList == null || trayList.size() == 0) {
                                            alertDialog.callback("托环信息不匹配");
                                        } else {
                                            intent.setAction("com.trayradio");
                                            intent.putExtra("info", (int) trayList.get(0).getDiameter() + "");
                                            context.sendBroadcast(intent);
                                        }
                                        break;
                                }

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
        if (mlMotor == null) {
            mlMotor = new MlMotor();
            code = mlMotor.initMotor();
        }
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

    public void autoCheck(Handler handler, int type, int num) {
        this.num = num;
        this.type = type;
        this.handler = handler;
        mlMotorUtil.getMlMotor().motorLightOff();
        motorReset(CommonUtil.Device_MachineHand, ressetHandler);
    }

    private void motorReset(final int type, final Handler handlers) {
        mlMotorUtil.motorReset(type);

        new Thread() {
            public void run() {
                super.run();
                content = "";
                while (true) {

                    boolean res = isMotorReset(type);

                    if (res) {


                        Log.d("zw", "res " + res + "type " + type);
                        handlers.sendEmptyMessage(type);

                        break;
                    }

                }
            }
        }.start();
    }

    private boolean isMotorReset(int type) {
//        boolean res = false;
//        String value = "";
//        PrintWriter printWriter;
//        Writer writer;
//        File file = new File("/sys/class/switch/motor_switch/state");
//        try {
//            fileInputStream = new FileInputStream(file);
//            inputStreamReader = new InputStreamReader(fileInputStream);
//            BufferedReader reader = new BufferedReader(inputStreamReader);
//            value = reader.readLine();
//            if (value.trim().equals("7")) {
//                reader.close();
//                inputStreamReader.close();
//                fileInputStream.close();
//                writer = new FileWriter(file);
//                printWriter = new PrintWriter(writer);
//                printWriter.println("-1");
//                printWriter.flush();
//                printWriter.close();
//                writer.close();
//                Log.d("zw", "state 7");
//                return false;
//            } else {
//                if (i != 0 && !content.trim().equals(value)) {
//                    return true;
//                }
//                content = value;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        reportDataState = new MlMotor.ReportDataState(new int[8], type);
        mlMotorUtil.getMlMotor().motorQueryState(reportDataState);
        if (reportDataState.getUcMotorState()[type] == 0) {
            return true;
        }
        return reportDataState.getUcMotorState()[type] == STARTPORT;

    }

    public void operateMotor(final int type, int dir, double avgspeed, int distance, final int state, final Handler handler) {
        mlMotorUtil.operateMlMotor(type, dir, avgspeed, distance);
        Log.d("zw", "type " + type + " state " + state);
        new Thread() {
            public void run() {
                super.run();
                while (true) {
                    reportDataState = new MlMotor.ReportDataState(new int[8], type);
                    mlMotorUtil.getMlMotor().motorQueryState(reportDataState);

                    if (reportDataState.getUcMotorState()[type] == STOPPORT) {
                        try {
                            Thread.sleep(500);
                            handler.sendEmptyMessage(state);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    if (state == CATCHRESET && reportDataState.getUcMotorState()[type] == 1) {
                        try {
                            Thread.sleep(500);
                            handler.sendEmptyMessage(state);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                }
            }
        }.start();
    }

    /**
     * 错误
     */
    private static final int ERROR = 0x00;
    /**
     * 起点
     */
    private static final int STARTPORT = 0x01;
    /**
     * 送瓶电机空瓶位
     */
    private static final int EMPTYBOTTLE = 0x02;
    /**
     * 终点位置
     */
    private static final int STOPPORT = 0x04;
    /**
     * 出瓶位太多瓶子
     */
    private static final int MUCHBOTTLE = 0x08;
    /**
     * 运行中
     */
    private static final int RUNNING = 0x10;
    /**
     * 压瓶到位
     */
    private static final int PRESSFINISH = 0x20;
    /**
     * 复位中
     */
    private static final int RESETTING = 0x40;
    /**
     * 送瓶到位
     */
    private static final int SENDBOTTLEFINISH = 0x80;

    /**
     * 电机没有运动
     */
    private static final int NORUNNING = 0xEF;
    /**
     * 复位成功
     */
    private static final int RESETSUCESS = 133;

    /**
     * 机械手到位
     */
    private static final int MOTORCATCH = 324;
    /**
     * 抓瓶机械手准备
     */
    private static final int CATCHPREPARE = 325;
    /**
     * 抓瓶机械手完成
     */
    private static final int CATCHFINISH = 326;
    /**
     * 检测位置
     */
    private static final int ChECKSTATE = 327;
    /**
     * 松瓶
     */
    private static final int LETBOTTLE = 328;
    /**
     * 压瓶到位
     */
    private static final int PRESSEDFINISH = 329;
    /**
     * 抓瓶手复位
     */
    private static final int CATCHRESET = 330;
    /**
     * 等待位置
     */
    private static final int MACHINEWAIT = 331;

    /**
     * 遮光完成
     */
    private static final int SHADEFINISH = 332;
    /**
     * 旋瓶完成
     */
    private static final int ROTALEFINISH = 333;
    /**
     * 检测位置
     */
    private static final int CHECKLOCATION = 334;
    /**
     * 抓瓶位置
     */
    private static final int CATCHLOCATION = 335;
    /**
     * 抓瓶等待
     */
    private static final int CATCHWAIT = 336;
    /**
     * 等待位置
     */

    private static final int WAITLOCATION = 337;
}
