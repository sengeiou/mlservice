package cn.ml_tech.mx.mlservice.Util;

import cn.ml_tech.mx.mlservice.MlMotor;

/**
 * Created by zhongwang on 2017/8/23.
 */

public class MlMotorUtil {
    private static MlMotorUtil mlMotorUtil;
    private MlMotor mlMotor;
    private MlMotor.ReportDataReg reportDataReg;
    private MlMotor.ReportDataState reportDataState;
    private MlMotor.ReportDataVal reportDataVal;
    public static final double WAVEPERMM = 0.0079375;
    private int code = 0;

    public void operateMlMotor(int type, int dir, double avgspeed, int distance) {
        reportDataVal.setNum(type);
        reportDataVal.setDir(dir);
        reportDataVal.setAcc_time((int) ((distance / avgspeed) / 2));
        reportDataVal.setU16WaveNum((int) (distance / WAVEPERMM));
        reportDataVal.setSpeed(0x200);
        if (type == CommonUtil.Device_OutPut)
            reportDataVal.setSpeed(0x250);
        if (type == CommonUtil.Device_Rotate)
            reportDataVal.setSpeed(0x300);
        reportDataVal.setDataType(1);
        mlMotor.motorControl(reportDataVal);
    }

    private MlMotorUtil() {
        mlMotor = new MlMotor();
        code = mlMotor.initMotor();
        reportDataReg = new MlMotor.ReportDataReg(0, 0, 0);
        reportDataState = new MlMotor.ReportDataState(new int[]{0}, 1);
        reportDataVal = new MlMotor.ReportDataVal(0, 0, 0, 0, 0, 0);
    }

    public static MlMotorUtil getInstance() {
        if (mlMotorUtil == null)
            mlMotorUtil = new MlMotorUtil();
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
}
