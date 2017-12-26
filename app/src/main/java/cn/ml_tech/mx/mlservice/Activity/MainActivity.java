package cn.ml_tech.mx.mlservice.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import cn.ml_tech.mx.mlservice.MlMotor;
import cn.ml_tech.mx.mlservice.R;

public class MainActivity extends AppCompatActivity {
    private TextView ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ids = (TextView) findViewById(R.id.ids);
        MlMotor mlMotor = new MlMotor();
        mlMotor.initMotor();
        int reg = 0x100;
        MlMotor.ReportDataReg reportDataReg = new MlMotor.ReportDataReg(reg, 0, 32);
        mlMotor.motorReadReg(reportDataReg);
        Log.d("zww", "res " + reportDataReg.getUcRegValue());
        Toast.makeText(this, "res " + reportDataReg.getUcRegValue(), Toast.LENGTH_SHORT).show();
    }

}




