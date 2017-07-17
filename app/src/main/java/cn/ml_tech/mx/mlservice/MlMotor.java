/**
 * Created by mx on 01/07/2017.
 */
package cn.ml_tech.mx.mlservice;

public class MlMotor {
    static {
        System.loadLibrary("JniTest");   //defaultConfig.ndk.moduleName
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
    }

    public static class ReportDataReg {
        private int usReg;
        private int ucRegValue;
        private int dataType;
        public ReportDataReg(int usreg, int ucregv, int datatype) {
            this.usReg = usreg;
            this.ucRegValue = ucregv;
            this.dataType = datatype;
        }
    }

    public class ReportDataState {
        private int[] ucMotorState;
        private int dataType;
    }

    public static native void motorControl(ReportDataVal reportDataVal);
    public static native void motorReadReg(ReportDataReg reportDataReg);
    public static native void motorWriteReg(ReportDataReg reportDataReg);
    public static native void motorQueryState(ReportDataState reportDataState);
    public static native void motorReset(ReportDataVal ReportDataVal);
    public static native void motorLightOn();
    public static native void motorLightOff();
    public static native void motorClientPID(ReportDataReg reportDataReg);
    public static native void motorQueryAutoCarreyConnected(ReportDataReg reportDataReg);
    public static native void motorQueryWaveNum(ReportDataReg reportDataReg);
    public static native void motorCatchMotor();
    public static native void motorReleaseMotor();
    public static native void motorLightSignalOn();
    public static native void motorLightSignalOff();
    public static native void motorQueryTooManyBottle(ReportDataReg reportDataReg);
    public static native void motorLedSpec();
    public static native void motorLedCarreyHaveBottle();
    public static native void motorLedRobotMove();
    public static native void motorLedCatchBottle();
    public static native void motorLedPressBottle();
    public static native void motorLedPressFinish();
    public static native void motorLedCarreyBottleConnect();
    public static native void motorLedPushBottle();
    public static native void motorLedRotate();
    public static native void motorLedStopBottle();
    public static native void motorLedLight();
    public static native void motorLedCheck();
    public static native void motorLedCarreyStart();
    public static native void motorLedCarreyEmpty();
    public static native void motorLedComputerRunning();
    public static native void motorLedPower();
    public static native void motorLedSpecOff();
    public static native void motorLedCarreyHaveBottleOff();
    public static native void motorLedRobotMoveOff();
    public static native void motorLedCatchBottleOff();
    public static native void motorLedPressBottleOff();
    public static native void motorLedPressFinishOff();
    public static native void motorLedCarreyBottleConnectOff();
    public static native void motorLedPushBottleOff();
    public static native void motorLedRotateOff();
    public static native void motorLedStopBottleOff();
    public static native void motorLedLightOff();
    public static native void motorLedCheckOff();
    public static native void motorLedCarreyStartOff();
    public static native void motorLedCarreyEmptyOff();
    public static native void motorLedComputerRunningOff();
    public static native void motorLedPowerOff();
    public static native void motorGetVersion(ReportDataReg reportDataReg);


}
