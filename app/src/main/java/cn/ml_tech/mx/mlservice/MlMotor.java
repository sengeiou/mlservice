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

    public class ReportDataReg {
        private int usReg;
        private int ucRegValue;
        private int dataType;
    }

    public class ReportDataState {
        private int[] ucMotorState;
        private int dataType;
    }

    public static native void motorControl(ReportDataVal reportDataVal);
}
