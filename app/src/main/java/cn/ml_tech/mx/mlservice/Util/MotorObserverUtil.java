package cn.ml_tech.mx.mlservice.Util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import cn.ml_tech.mx.mlservice.DAO.DevParam;
import cn.ml_tech.mx.mlservice.MlMotor;

/**
 * 创建时间: 2017/10/23
 * 创建人: Administrator
 * 功能描述:
 */

public class MotorObserverUtil {
    public MlMotorUtil mlMotorUtil;
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
                    double distance = DataSupport.where("paramname = ?", "MachineHandDistance1").find(DevParam.class).get(0).getParamValue();
                    double speed = DataSupport.where("paramname = ?", "MachineHandSpeed").find(DevParam.class).get(0).getParamValue();
                    Log.d("zw", "motor finish distance " + distance + " speed " + speed);
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
                    int distance = 5;
                    double catchSpeed = DataSupport.where("paramname = ?", "CatchHandSpeed").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_CatchHand, 1, catchSpeed, distance, CATCHFINISH, stateHandler);
                    break;
                case CATCHFINISH:
                    double machineHandMotorCatchLocation = DataSupport.where("paramname = ?", "MachineHandDistance1").find(DevParam.class).get(0).getParamValue();
                    double machineHandMotorDetLocation = DataSupport.where("paramname = ?", "MachineHandDistance2").find(DevParam.class).get(0).getParamValue();
                    double machineHandMotorWaitLocation = DataSupport.where("paramname = ?", "MachineHandDistance3").find(DevParam.class).get(0).getParamValue();
                    distance = (int) (machineHandMotorCatchLocation - (machineHandMotorWaitLocation + machineHandMotorDetLocation));
                    operateMotor(CommonUtil.Device_MachineHand, 0, machineSpeed, distance, ChECKSTATE, stateHandler);
                    break;
                case ChECKSTATE:
                    double catchParam1 = DataSupport.where("paramname = ?", "CatchHandDistance1").find(DevParam.class).get(0).getParamValue();
                    double speed = DataSupport.where("paramname = ?", "CatchHandSpeed").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_CatchHand, 0, speed, (int) catchParam1, LETBOTTLE, stateHandler);
                    break;
                case LETBOTTLE:
                    double pressedSpeed = DataSupport.where("paramname = ?", "PressedSpeed").find(DevParam.class).get(0).getParamValue();
                    double pressedDistance = DataSupport.where("paramname = ?", "PressedDistance1").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_Pressed, 1, pressedSpeed, (int) pressedDistance, PRESSEDFINISH, stateHandler);
                    break;
                case PRESSEDFINISH:
                    // TODO: 2017/10/24 压瓶到位后
                    double catchspeed = DataSupport.where("paramname = ?", "CatchHandSpeed").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_CatchHand, 0, catchspeed, (int) 3, CATCHRESET, stateHandler);
                    break;
                case CATCHRESET:
                    // TODO: 2017/10/24 抓瓶手完全放开后
                    Log.d("zw", "抓瓶手完全放开后");
                    int dis = 40;
//                    double maincheSpeed = DataSupport.where("paramname = ?", "MachineHandSpeed").find(DevParam.class).get(0).getParamValue();
                    operateMotor(CommonUtil.Device_MachineHand, 0, 0.02, dis, MACHINEWAIT, stateHandler);
                    break;
                case MACHINEWAIT:
                    operateMotor(CommonUtil.Device_ShadeLight, 1,
                            DataSupport.where("paramname = ?", "ShadeLightSpeed").find(DevParam.class).get(0).getParamValue(),
                            (int) DataSupport.where("paramname = ?", "ShadeLightDistance").find(DevParam.class).get(0).getParamValue(),
                            SHADEFINISH, stateHandler);
                    break;
                case SHADEFINISH:
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
                    operateMotor(CommonUtil.Device_MachineHand, 1, 0.02, 40, CHECKLOCATION, stateHandler);
                    break;
                case CHECKLOCATION:
                    operateMotor(CommonUtil.Device_CatchHand, 1, 0.015, 3, CATCHLOCATION, stateHandler);
                    break;
                case CATCHLOCATION:
                    motorReset(CommonUtil.Device_Pressed, stateHandler);
                    break;
                case CommonUtil.Device_Pressed:
                    motorReset(CommonUtil.Device_ShadeLight, stateHandler);
                    break;
                case CommonUtil.Device_ShadeLight:
                    operateMotor(CommonUtil.Device_MachineHand, 0, 0.02, 55, WAITLOCATION, stateHandler);
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
                                    handler.sendEmptyMessage(1);
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

    public MotorObserverUtil(final MlMotorUtil mlMotorUtil) {
        this.mlMotorUtil = mlMotorUtil;


    }

    private void startWatch(final int type, final int motorState, final int state, final Handler handler) {
        new Thread() {

            public void run() {
                super.run();
                int b = 0;
                while (true) {

                    if (reportDataState == null) {
                        reportDataState = new MlMotor.ReportDataState(i, type);
                    }
                    mlMotorUtil.getMlMotor().motorQueryState(reportDataState);
                    if (b != i[type]) {
                        Log.d("zw", "state " + i[type]);
                    }
                    if (motorState == i[type]) {
                        handler.sendEmptyMessage(state);
                        break;
                    }
                }

            }
        }.start();
    }

    public void autoCheck(Handler handler, int num) {
        this.num = num;
        this.handler = handler;
        mlMotorUtil.getMlMotor().motorLightOff();
        motorReset(CommonUtil.Device_MachineHand, ressetHandler);
    }

    private void motorReset(final int type, final Handler handlers) {
        mlMotorUtil.motorReset(type);

        new Thread() {
            public void run() {
                super.run();
                int i = 0;
                content = "";
                while (true) {

                    boolean res = isMotorReset(type, i);
                    i++;
                    if (res) {
                        try {
                            Thread.sleep(500);
                            Log.d("zw", "res " + res + "type " + type);
                            handlers.sendEmptyMessage(type);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                }
            }
        }.start();
    }

    private boolean isMotorReset(int type, int i) {
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
    public static final int ERROR = 0x00;
    /**
     * 起点
     */
    public static final int STARTPORT = 0x01;
    /**
     * 送瓶电机空瓶位
     */
    public static final int EMPTYBOTTLE = 0x02;
    /**
     * 终点位置
     */
    public static final int STOPPORT = 0x04;
    /**
     * 出瓶位太多瓶子
     */
    public static final int MUCHBOTTLE = 0x08;
    /**
     * 运行中
     */
    public static final int RUNNING = 0x10;
    /**
     * 压瓶到位
     */
    public static final int PRESSFINISH = 0x20;
    /**
     * 复位中
     */
    public static final int RESETTING = 0x40;
    /**
     * 送瓶到位
     */
    public static final int SENDBOTTLEFINISH = 0x80;

    /**
     * 电机没有运动
     */
    public static final int NORUNNING = 0xEF;
    /**
     * 复位成功
     */
    public static final int RESETSUCESS = 133;

    /**
     * 机械手到位
     */
    public static final int MOTORCATCH = 324;
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
     * 等待位置
     */
    private static final int WAITLOCATION = 336;


}
