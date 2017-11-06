package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ml on 2017/6/6.
 */

public class UtilsHelper {

    public static final String DebugTag="Debug";
    public static int String2Int(String string)
    {
        int number=0;
        if(!TextUtils.isEmpty(string))
        {
            try {
                number= Integer.parseInt(string);
            }catch (Exception e)
            {
            }finally {
                return number;
            }
        }else return number;
    }
    public static String Int2String(int a)
    {
        return String.valueOf(a);
    }

    public static float String2Flaot(String string)
    {
        float number=0;
        if(!TextUtils.isEmpty(string))
        {
            try {
                number= Float.parseFloat(string);
            }catch (Exception e)
            {
            }finally {
                return number;
            }
        }else return number;
    }
    public static String Float2String(float f){
        return String.valueOf(f);
    }
    public static double String2Double(String string)
    {
        double number=0;
        if(!TextUtils.isEmpty(string))
        {
            try {
                number= Double.parseDouble(string);
            }catch (Exception e)
            {
            }finally {
                return number;
            }
        }else return number;
    }
    public static String Double2String(double f){
        return String.valueOf(f);
    }
    public static Date GetDateFromString(String sdate,String format)
    {
        Date date=new Date();
        try {
            date=new SimpleDateFormat(format).parse(sdate);
            return new SimpleDateFormat(format).parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            return date;
        }
    }
    private static Toast toast=null;
    public static void ShowToast(Context context,CharSequence charSequence,int duration)
    {
     if(null==toast)
     {
         toast=Toast.makeText(context,charSequence,duration);
     }else
     {
         toast.setDuration(duration);
         toast.setText(charSequence);
     }
     toast.show();
    }

}
