/**
 * Created by mx on 01/07/2017.
 */
package cn.ml_tech.mx.mlservice;

public class MlMotor {
    static {
        System.loadLibrary("JniTest");
    }

    private int mid;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public MlMotor() {
    }

    public static native String getCLanguageString();

    public static native int initMotor();

    public static native void uninitMotor(int fd);

    public static class ReportDataVal {
        private int num;
        private int dir;
        private int speed;
        private int acc_time;
        private int u16WaveNum;
        private int dataType;

        public ReportDataVal(int num, int dir, int speed, int acct, int wavenum, int datatype) {
            this.num = num;
            this.dir = dir;
            this.speed = speed;
            this.acc_time = acct;
            this.u16WaveNum = wavenum;
            this.dataType = datatype;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getDir() {
            return dir;
        }

        public void setDir(int dir) {
            this.dir = dir;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getAcc_time() {
            return acc_time;
        }

        public void setAcc_time(int acc_time) {
            this.acc_time = acc_time;
        }

        public int getU16WaveNum() {
            return u16WaveNum;
        }

        public void setU16WaveNum(int u16WaveNum) {
            this.u16WaveNum = u16WaveNum;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }
    }

    public static class ReportDataReg {
        public int usReg;
        public int ucRegValue;
        public int dataType;

        public ReportDataReg(int usreg, int ucregv, int datatype) {
            this.usReg = usreg;
            this.ucRegValue = ucregv;
            this.dataType = datatype;
        }

        public int getUsReg() {
            return usReg;
        }

        public void setUsReg(int usReg) {
            this.usReg = usReg;
        }

        public int getUcRegValue() {
            return ucRegValue;
        }

        public void setUcRegValue(int ucRegValue) {
            this.ucRegValue = ucRegValue;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }
    }

    public static class ReportDataState {
        private int[] ucMotorState;
        private int dataType;

        public ReportDataState(int[] ucMotorState, int dataType) {
            this.ucMotorState = ucMotorState;
            this.dataType = dataType;
        }

        public int[] getUcMotorState() {
            return ucMotorState;
        }

        public void setUcMotorState(int[] ucMotorState) {
            this.ucMotorState = ucMotorState;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }
    }

    public static native void motorControl(ReportDataVal reportDataVal);// TODO: 2017/8/23 电机控制

    public static native void motorReadReg(ReportDataReg reportDataReg);//// TODO: 2017/8/23 都寄存器

    public static native void motorWriteReg(ReportDataReg reportDataReg);// TODO: 2017/8/23 写寄存器

    public static native void motorQueryState(ReportDataState reportDataState);// TODO: 2017/8/23 查询状态

    public static native void motorReset(ReportDataVal ReportDataVal);// TODO: 2017/8/23  复位某个电机

    public static native void motorLightOn();// TODO: 2017/8/23 开激光

    public static native void motorLightOff();// TODO: 2017/8/23 关机光

    public static native void motorClientPID(ReportDataReg reportDataReg);// TODO: 2017/8/23 将进程pid号写入驱动

    public static native void motorQueryAutoCarreyConnected(ReportDataReg reportDataReg);// TODO: 2017/8/23 自动上瓶器是否连接

    public static native void motorQueryWaveNum(ReportDataReg reportDataReg);// TODO: 2017/8/23 查询电机脉冲数

    public static native void motorCatchMotor();// TODO: 2017/8/23 抓瓶电机

    public static native void motorReleaseMotor();// TODO: 2017/8/23 释放电机（不带电）

    public static native void motorLightSignalOn();// TODO: 2017/8/23  打开光电传感器检测

    public static native void motorLightSignalOff();

    public static native void motorQueryTooManyBottle(ReportDataReg reportDataReg);// TODO: 2017/8/23 查询是否瓶满

    public static native void motorLedSpec();// TODO: 2017/8/23 规格led unuseless

    public static native void motorLedCarreyHaveBottle();// TODO: 2017/8/23 瓶子到位led

    public static native void motorLedRobotMove();// TODO: 2017/8/23 机械手移动led

    public static native void motorLedCatchBottle();// TODO: 2017/8/23 抓瓶led

    public static native void motorLedPressBottle();// TODO: 2017/8/23 压屏led

    public static native void motorLedPressFinish();// TODO: 2017/8/23 压屏到位led

    public static native void motorLedCarreyBottleConnect();// TODO: 2017/8/23 自动上瓶器是否连接led

    public static native void motorLedPushBottle();// TODO: 2017/8/23 出瓶led

    public static native void motorLedRotate();// TODO: 2017/8/23 旋屏led

    public static native void motorLedStopBottle();// TODO: 2017/8/23 停瓶led

    public static native void motorLedLight();// TODO: 2017/8/23 激光led

    public static native void motorLedCheck();// TODO: 2017/8/23 检测位led

    public static native void motorLedCarreyStart();// TODO: 2017/8/23 送瓶开始

    public static native void motorLedCarreyEmpty();// TODO: 2017/8/23 自动上瓶器为空led

    public static native void motorLedComputerRunning();// TODO: 2017/8/23 计算机正在运行led

    public static native void motorLedPower();// TODO: 2017/8/23 开机常亮

    public static native void motorLedSpecOff();// TODO: 2017/8/23 规格led关闭

    public static native void motorLedCarreyHaveBottleOff();// TODO: 2017/8/23 上瓶到位关闭

    public static native void motorLedRobotMoveOff();// TODO: 2017/8/23 机械手移动led关闭

    public static native void motorLedCatchBottleOff();// TODO: 2017/8/23 抓瓶灯关

    public static native void motorLedPressBottleOff();// TODO: 2017/8/23 压屏灯关

    public static native void motorLedPressFinishOff();// TODO: 2017/8/23 压屏到位led

    public static native void motorLedCarreyBottleConnectOff();// TODO: 2017/8/23 自动上瓶器以连接led

    public static native void motorLedPushBottleOff();// TODO: 2017/8/23 出瓶led关

    public static native void motorLedRotateOff();// TODO: 2017/8/23 旋屏灯

    public static native void motorLedStopBottleOff();// TODO: 2017/8/23 停瓶led off

    public static native void motorLedLightOff();// TODO: 2017/8/23 激光led off

    public static native void motorLedCheckOff();// TODO: 2017/8/23 检测led off

    public static native void motorLedCarreyStartOff();// TODO: 2017/8/23 自动上瓶器开始 off

    public static native void motorLedCarreyEmptyOff();// TODO: 2017/8/23 自动上瓶器未检测到瓶子

    public static native void motorLedComputerRunningOff();// TODO: 2017/8/23 计算器运行led off

    public static native void motorLedPowerOff();// TODO: 2017/8/23  仪器电源led灯

    public static native void motorGetVersion(ReportDataReg reportDataReg);
}
