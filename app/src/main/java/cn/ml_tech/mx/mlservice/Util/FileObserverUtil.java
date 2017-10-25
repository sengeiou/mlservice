package cn.ml_tech.mx.mlservice.Util;


import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zhongwang on 2017/8/28.
 */

public abstract class FileObserverUtil {
    private String path;
    private BufferedReader reader;
    private String value;
    private int i;

    public FileObserverUtil(String path) {
        this.path = path;

    }

    public abstract void doInModity();

    public abstract void doInCreate();

    public abstract void doInDelete();

    public void startWatch() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    i = 0;
                    while (true) {
                        File file = new File(path);
                        FileInputStream fileInputStream = new FileInputStream(file);
                        reader = new BufferedReader(new InputStreamReader(fileInputStream));
                        String content = reader.readLine();
                        reader.close();
                        fileInputStream.close();
                        if (i != 0 && !value.trim().equals(content)) {
                            Log.d("zw", "old " + value + " new  " + content);

                        }
                        value = content;

                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
