package cn.ml_tech.mx.mlservice.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import cn.ml_tech.mx.mlservice.MlMotor;
import cn.ml_tech.mx.mlservice.R;
import cn.ml_tech.mx.mlservice.Util.CommonUtil;
import cn.ml_tech.mx.mlservice.Util.MlMotorUtil;
import cn.ml_tech.mx.mlservice.Util.MotorObserverUtil;
import cn.ml_tech.mx.mlservice.Util.VerSionUtil;

public class MainActivity extends AppCompatActivity {
    static TextView textView;
    VerSionUtil verSionUtil;
    MlMotorUtil mlMotorUtil;
    MotorObserverUtil motorObserverUtil;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(MainActivity.this, "完成", Toast.LENGTH_SHORT).show();
            }
        };
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

//        mlMotorUtil = MlMotorUtil.getInstance(this);
//        mlMotorUtil.motorReset(CommonUtil.Device_OutPut);
//        motorObserverUtil = new MotorObserverUtil(mlMotorUtil);
//        motorRet();
//        autoDebug();
//    z    test();
}

    private void test() {
        mlMotorUtil.operateMlMotor(CommonUtil.Device_MachineHand, 1, 0.02, 40);
        new Thread() {
            public void run() {
                super.run();
                while (true) {
                    String value;
                    String content = "";

                    File file = new File("/sys/class/switch/motor_switch/state");
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        value = reader.readLine();
                        if (!content.trim().equals(value.trim())) {
                            Log.d("Zw", "old " + content + " new " + value);
                        }
                        content = value;


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void autoDebug() {
        new Thread() {
            public void run() {
                super.run();
                motorObserverUtil.autoCheck(handler, 3);

            }
        }.start();

    }

    private void motorRet() {
        new Thread() {
            public void run() {
                super.run();
                mlMotorUtil.motorReset(CommonUtil.Device_MachineHand);
                try {
                    Thread.sleep(2000);
                    mlMotorUtil.motorReset(CommonUtil.Device_CatchHand);
                    Thread.sleep(2000);
                    mlMotorUtil.motorReset(CommonUtil.Device_ShadeLight);
                    Thread.sleep(2000);
                    mlMotorUtil.motorReset(CommonUtil.Device_Pressed);
                    Thread.sleep(5000);
                    MlMotor.ReportDataState reportDataState = new MlMotor.ReportDataState(new int[8], CommonUtil.Device_CatchHand);
                    mlMotorUtil.getMlMotor().motorQueryState(reportDataState);
                    Log.d("zw", "state  ss " + reportDataState.getUcMotorState()[CommonUtil.Device_CatchHand]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
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
                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlMotorUtil.realease();
        finish();
    }
}
