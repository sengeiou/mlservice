package cn.ml_tech.mx.mlservice.Util;

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
    private SerialPort rotaleBottlePort, readInfoPort;
    private OutputStream rotaleOutputStream;
    private InputStream readInputStream;
    public static final double WAVEPERMM = 0.0079375;
    private byte[] rotale = null;
    private int code = 0;

    public void operateMlMotor(int type, double dir, double avgspeed, double distance) {
        reportDataVal.setNum(type);
        reportDataVal.setDir((int) dir);
        reportDataVal.setAcc_time((int) ((distance / avgspeed) / 2));
        reportDataVal.setU16WaveNum((int) (distance / WAVEPERMM));
        reportDataVal.setSpeed((int) (((6350) / ((avgspeed) * 32 * 8)) - 1));
        reportDataVal.setDataType(1);
        mlMotor.motorControl(reportDataVal);

    }

    public void operateRotale(int speed) {
        try {
            if (rotaleBottlePort == null) {
                rotaleBottlePort = new SerialPort(new File("/dev/ttymxc2"), 19200, 1);
                rotaleOutputStream = rotaleBottlePort.getOutputStream();
            }
            if (rotale == null) {
                rotale = new byte[]{0, 0, 0};
            }
            if (speed != 0) {
//                rotale[0] = 0x55;
//                rotale[1] = (byte) ((speed & 0xff00) >> 8);
//                rotale[2] = (byte) (260 & 0xff00);
                rotaleOutputStream.write(intToBytes(0x55));
                rotaleOutputStream.write(intToBytes((speed & 0xff00) >> 8));
                rotaleOutputStream.write(intToBytes(speed & 0x00ff));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTrayId() {
        int trayId = 0;
        operateRotale(50);
        if (readInfoPort == null) {
            try {
                readInfoPort = new SerialPort(new File("/dev/ttymxc2"), 19200, 1);
                readInputStream = readInfoPort.getInputStream();
                int i = 0;
                while ((i = readInputStream.read()) != -1) {
                    // TODO: 2017/8/29 获取trayId
                    trayId = 123456;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return trayId;
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

    public static byte[] intToBytes(int value) {
        byte[] byte_src = new byte[4];
        byte_src[3] = (byte) ((value & 0xFF000000) >> 24);
        byte_src[2] = (byte) ((value & 0x00FF0000) >> 16);
        byte_src[1] = (byte) ((value & 0x0000FF00) >> 8);
        byte_src[0] = (byte) ((value & 0x000000FF));
        return byte_src;
    }
}
