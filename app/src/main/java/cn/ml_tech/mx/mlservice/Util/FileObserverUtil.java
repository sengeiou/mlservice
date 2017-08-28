package cn.ml_tech.mx.mlservice.Util;


import android.os.FileObserver;
import android.util.Log;

/**
 * Created by zhongwang on 2017/8/28.
 */

public abstract class FileObserverUtil extends FileObserver {
    public FileObserverUtil(String path) {
        super(path);
    }

    public abstract void doInModity();

    public abstract void doInCreate();

    public abstract void doInDelete();

    @Override
    public void onEvent(int event, String path) {
        switch (event) {
            case FileObserver.CREATE:
                Log.d("ww", "path:" + path);
                // TODO: 2017/8/22 创建文件 之后要做的操作
                doInCreate();
                break;
            case FileObserver.MODIFY:
                Log.d("ww", "modify" + "path:" + path);
                // TODO: 2017/8/22  修改文件之后要做的操作
                doInModity();
                break;
            case FileObserver.DELETE:
                Log.d("ww", "delete" + "path:" + path);
                // TODO: 2017/8/22 删除文件之后要做的操作
                doInDelete();
                break;
        }
    }
}
