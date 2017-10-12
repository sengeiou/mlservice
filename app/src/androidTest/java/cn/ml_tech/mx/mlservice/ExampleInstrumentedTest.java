package cn.ml_tech.mx.mlservice;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

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

    }

    @Test
    public void format() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 7; j < 0; j--) {
            if (((1 << j) & -86) != 0) {
                stringBuilder.append(1);
            } else {
                stringBuilder.append(0);
            }
        }
        System.out.print(stringBuilder.toString());
    }
}
