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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.ml_tech.mx.mlservice.Bean.UserType;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
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

    @Test
    public void AddDetectionReport() {
//        @Column(nullable = false)
//        private long user_id;
//        @Column(nullable = false)
//        private long druginfo_id;
//        @Column(nullable = false)
//        private String detectionSn;
//        @Column( nullable = false)
//        private String detectionNumber;
//        @Column(nullable = false)
//        private  String detectionBatch;
//        @Column( nullable = false)
//        private  int detectionCount;
//        @Column(nullable = false)
//        private  int detectionFirstCount;
//        @Column( nullable = false)
//        private  int detectionSecondCount;
//        @Column(nullable = false)
//        private  Date date;
//        @Column( nullable = false,defaultValue = "false")
//        private boolean deprecate;
//        @Column( nullable = false,defaultValue = "false")
//        private boolean ispdfdown;
        List<DetectionReport> listReport = new ArrayList<DetectionReport>();
        for (int i = 0; i < 100; i++) {
            DetectionReport report = new DetectionReport();
            report.setDetectionSn("Sn" + String.valueOf(i));
            report.setDetectionBatch("Batch" + String.valueOf(i));
            report.setDetectionNumber("Number" + String.valueOf(i));
            report.setDruginfo_id(i);
            report.setDetectionFirstCount(i);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                report.setDate(dateFormat.parse("2016-07-02"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            report.setDetectionSecondCount(i);
            report.setDetectionCount(i);
            report.setUser_id(i);
            listReport.add(report);
        }
        DataSupport.saveAll(listReport);
        assertTrue(DataSupport.findAll(DetectionReport.class).size() > 0);
    }

    @Test
    public void InitUserType() {
        cn.ml_tech.mx.mlservice.DAO.UserType Type = new cn.ml_tech.mx.mlservice.DAO.UserType();

        Type.setType_id(0);
        Type.setName("超级管理员");
        Type.save();
        Type.clearSavedState();
        Type.setType_id(1);
        Type.setName("管理员");
        Type.save();
        Type.clearSavedState();
        Type.setType_id(2);
        Type.setName("操作员");
        Type.save();
        Type.clearSavedState();
        assertTrue(DataSupport.findAll(cn.ml_tech.mx.mlservice.DAO.UserType.class).size() >= 3);

    }

    @Test
    public void TestAsysncFun() {


        DataSupport.deleteAll(cn.ml_tech.mx.mlservice.DAO.UserType.class);
        List<cn.ml_tech.mx.mlservice.DAO.UserType> list = new ArrayList<cn.ml_tech.mx.mlservice.DAO.UserType>();
        for (int i = 5; i < 10; i++) {
            cn.ml_tech.mx.mlservice.DAO.UserType type = new cn.ml_tech.mx.mlservice.DAO.UserType();
            type.setName(String.format("name%d", i));
            type.setType_id(i);
            list.add(type);
        }
        DataSupport.saveAllAsync(list).listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                assertTrue(false);
                assertTrue(DataSupport.findAll(cn.ml_tech.mx.mlservice.DAO.UserType.class).size() > 0);
            }
        });
    }
}
