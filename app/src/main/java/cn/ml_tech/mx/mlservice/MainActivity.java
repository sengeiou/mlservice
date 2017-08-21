package cn.ml_tech.mx.mlservice;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cn.ml_tech.mx.mlservice.Util.VerSionUtil;

public class MainActivity extends AppCompatActivity {
    static TextView textView;
    MlMotor mlMotor = null;
    VerSionUtil verSionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv);
        mlMotor = new MlMotor();
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

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        textView.setText(mlMotor.getCLanguageString());
        MlMotor.ReportDataVal reportDataVal = new MlMotor.ReportDataVal(2, 1, 0x200, 20000, 4000, 1);
        mlMotor.motorControl(reportDataVal);
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
