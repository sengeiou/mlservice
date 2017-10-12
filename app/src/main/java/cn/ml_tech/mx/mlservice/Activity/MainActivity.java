package cn.ml_tech.mx.mlservice.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

import cn.ml_tech.mx.mlservice.MlMotor;
import cn.ml_tech.mx.mlservice.R;
import cn.ml_tech.mx.mlservice.Util.AlertDialog;
import cn.ml_tech.mx.mlservice.Util.CommonUtil;
import cn.ml_tech.mx.mlservice.Util.FileObserverUtil;
import cn.ml_tech.mx.mlservice.Util.MlMotorUtil;
import cn.ml_tech.mx.mlservice.Util.VerSionUtil;

public class MainActivity extends AppCompatActivity {
    static TextView textView;
    VerSionUtil verSionUtil;
    private FileObserverUtil fileObserverUtil;

    MlMotorUtil mlMotorUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog alertDialog = new AlertDialog();
        Intent intent = new Intent();
        textView = (TextView) findViewById(R.id.tv);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    32);
        } else {
            verSionUtil = new VerSionUtil(this);
            verSionUtil.updateVersion();
        }
        searchFile();

        mlMotorUtil = MlMotorUtil.getInstance(this);
        mlMotorUtil.getMlMotor().motorReset(new MlMotor.ReportDataVal(CommonUtil.Device_MachineHand, 0, (int) (((6350) / ((0.02) * 32 * 8)) - 1), 0,
                (int) mlMotorUtil.WAVEPERMM, 0));
    }

    private void searchFile() {
        String result = "";
        File[] files = new File("/sys/class/switch/motor_switch").listFiles();
        for (File file : files) {

            result += file.getPath() + "\n";

        }
        if (result.equals("")) {
            result = "找不到文件!!";
        }
        Log.d("zw", result);
    }

    private String intToString(int[] a) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            stringBuilder.append(a[i]);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public byte[] intToByte(int value) {
        byte[] src = new byte[12];
        int[] array = new int[]{0x55, ((value & 0xff00) >> 8), (value & 0xff00)};
        //byte[] src = new byte[4];(array[0] >> 24) & 0xFF
        src[0] = (byte) ((array[0] >> 24) & 0xFF);
        src[1] = (byte) ((array[0] >> 16) & 0xFF);
        src[2] = (byte) ((array[0] >> 8) & 0xFF);
        src[3] = (byte) (array[0] & 0xFF);
        src[4] = (byte) ((array[1] >> 24) & 0xFF);
        src[5] = (byte) ((array[1] >> 16) & 0xFF);
        src[6] = (byte) ((array[1] >> 8) & 0xFF);
        src[7] = (byte) (array[1] & 0xFF);
        src[8] = (byte) ((array[2] >> 24) & 0xFF);
        src[9] = (byte) ((array[2] >> 16) & 0xFF);
        src[10] = (byte) ((array[2] >> 8) & 0xFF);
        src[11] = (byte) (array[2] & 0xFF);
        return src;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();

//        MlMotor.ReportDataVal reportDataVal = new MlMotor.ReportDataVal(2, 1, 0x200, 20000, 4000, 1);
        //MlMotor.ReportDataReg reportDataReg = new MlMotor.ReportDataReg(0,0,1);
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceHashMap = usbManager.getDeviceList();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 32: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new VerSionUtil(this).updateVersion();

                } else {
                    Toast.makeText(this, "没有获得权限", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


}
