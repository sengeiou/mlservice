package cn.ml_tech.mx.mlservice;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.litepal.crud.DataSupport;

import cn.ml_tech.mx.mlservice.Util.PermissionUtil;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
//        assertEquals("cn.ml_tech.mx.mlservice", appContext.getPackageName());
        PermissionUtil permissionUtil = PermissionUtil.getInstance(appContext);
        permissionUtil.operatePermission(1, 8, 0, PermissionUtil.TYPE.DELETE);
    }


    @Test
    public void format() {
        Cursor cursor = DataSupport.findBySQL("select * from " + "druginfo");
        Log.d("zw", "druginfo size " + cursor.getCount());
    }
}
