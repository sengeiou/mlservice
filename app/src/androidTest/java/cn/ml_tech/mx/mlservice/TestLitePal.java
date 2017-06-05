package cn.ml_tech.mx.mlservice;

import android.content.Context;
import android.graphics.Region;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.litepal.crud.ClusterQuery;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.ml_tech.mx.mlservice.DAO.DevParam;
import cn.ml_tech.mx.mlservice.DAO.Tray;
import cn.ml_tech.mx.mlservice.DAO.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestLitePal  {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("cn.ml_tech.mx.mlservice", appContext.getPackageName());
    }
    @Test
    public void Test()
    {
        //region string to double
        /*String str="";
        double dd=0;
        if(str!=null&&!TextUtils.equals(str,""))
            dd=Double.valueOf(str);
        System.out.println(dd);
        dd=Double.parseDouble(str);*/
        //endregion
        double dd=99;
        System.out.println(String.valueOf(dd));


    }
    @Test
    public  void FindUserAll()
    {
        List<User>userList=DataSupport.findAll(User.class);
        userList= (List<User>) DataSupport
                .select("id","userid","username","usertype_id","userenable")
                .where("isdeprecated=?","0")
                .find(User.class);
        assertTrue(userList.size()>0);
    }
    @Test
    public void GetDeviceParamList()
    {
        List<DevParam> list= DataSupport.select("paramName","paramValue")
                .where("type=?","0")
//                .where("paramName=?","pixTwoReg_sc")
                .find(DevParam.class);
        for (DevParam param:list
             ) {
            System.out.println(param.getParamName()+" "+String.valueOf(param.getParamValue()));
        }
        System.out.println(String.valueOf(list.size()));
        assertTrue(list.size()>0);
    }
    @Test
    public void SetDeviceParam()
    {
        List<DevParam>list=new ArrayList<DevParam>();
        for(int var=0;var<10;var++)
        {
            DevParam param=new DevParam();
            param.setParamName("param"+ String.valueOf(var+1));
            param.setParamValue(var+1);
            param.setType(0);
            list.add(param);
        }
        System.out.println(list.size());

        for ( DevParam param:list
             ) {
            //param.saveOrUpdate("paramName=?",param.getParamName());
           param.saveOrUpdateAsync("paramName=?",param.getParamName());
        }
    // assertTrue(  devParam.save());

    }


    @Test
    public void FindTrayAll()
    {
        List<Tray>trayList=  DataSupport.findAll(Tray.class);

        assertTrue(trayList.size()>0);
    }
    @Test
    public void FindTray()
    {
        List<Tray>trayList=  DataSupport.select("displayId","icid")
                .where("displayId>?","8").find(Tray.class);
        assertTrue("find displayId>8 size more than 1 ",trayList.size()>0);
    }
    @Test
    public  void SaveTray (){
        Tray tray=new Tray();
        tray.setMark("the new tray ");
        tray.setDiameter(18);
        tray.setDisplayId(10);
        tray.setExternalDiameter(20);
        tray.setIcId("0123456");
        tray.setInnerDiameter(15);
        assertTrue(tray.save());
    }
    @Test
    public void SaveAllTray(){
        List<Tray>trayList=new ArrayList<Tray>();
        for (int i=1;i<10;i++)
        {
            Tray tray=new Tray();
            tray.setIcId(UUID.randomUUID().toString());
            tray.setInnerDiameter(i);
            tray.setExternalDiameter(i+1);
            tray.setDiameter(i);
            tray.setMark("this display id is "+i);
            tray.setDisplayId(i);
            tray.setExternalDiameter(i+1);
            trayList.add(tray);
        }
        DataSupport.saveAll(trayList);
    }
    @Test
    public void  DeleteTrayById()
    {
        int id=1;
        int rows= DataSupport.delete(Tray.class,id);
        assertTrue(rows>=1);
    }
    @Test
    public  void DeleteTrayAll()
    {
        //delete the displayId>5 and id<7
        DataSupport.deleteAllAsync(Tray.class,"displayId>? and id<?","5","7");
    }

}
