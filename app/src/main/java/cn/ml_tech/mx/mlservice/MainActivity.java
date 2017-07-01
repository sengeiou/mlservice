package cn.ml_tech.mx.mlservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static TextView textView;
    MlMotor mlMotor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv);
        mlMotor = new MlMotor();

    }

    @Override
    protected void onStart() {
        super.onStart();
        textView.setText(mlMotor.getCLanguageString());
    }
}
