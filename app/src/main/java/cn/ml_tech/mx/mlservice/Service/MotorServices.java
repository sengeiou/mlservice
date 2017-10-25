package cn.ml_tech.mx.mlservice.Service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.ml_tech.mx.mlservice.DAO.AuditTrail;
import cn.ml_tech.mx.mlservice.DAO.AuditTrailEventType;
import cn.ml_tech.mx.mlservice.DAO.AuditTrailInfoType;
import cn.ml_tech.mx.mlservice.DAO.BottlePara;
import cn.ml_tech.mx.mlservice.DAO.CameraParams;
import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DevDynamicParams;
import cn.ml_tech.mx.mlservice.DAO.DevParam;
import cn.ml_tech.mx.mlservice.DAO.DevUuid;
import cn.ml_tech.mx.mlservice.DAO.DrugContainer;
import cn.ml_tech.mx.mlservice.DAO.DrugControls;
import cn.ml_tech.mx.mlservice.DAO.DrugInfo;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;
import cn.ml_tech.mx.mlservice.DAO.Factory;
import cn.ml_tech.mx.mlservice.DAO.FactoryControls;
import cn.ml_tech.mx.mlservice.DAO.LoginLog;
import cn.ml_tech.mx.mlservice.DAO.Modern;
import cn.ml_tech.mx.mlservice.DAO.MotorControl;
import cn.ml_tech.mx.mlservice.DAO.P_Module;
import cn.ml_tech.mx.mlservice.DAO.P_Operator;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.P_SourceOperator;
import cn.ml_tech.mx.mlservice.DAO.P_UserTypePermission;
import cn.ml_tech.mx.mlservice.DAO.Permission;
import cn.ml_tech.mx.mlservice.DAO.PermissionHelper;
import cn.ml_tech.mx.mlservice.DAO.SpecificationType;
import cn.ml_tech.mx.mlservice.DAO.SystemConfig;
import cn.ml_tech.mx.mlservice.DAO.Tray;
import cn.ml_tech.mx.mlservice.DAO.User;
import cn.ml_tech.mx.mlservice.DAO.UserType;
import cn.ml_tech.mx.mlservice.IMlService;
import cn.ml_tech.mx.mlservice.Util.AlertDialog;
import cn.ml_tech.mx.mlservice.Util.LogUtil;
import cn.ml_tech.mx.mlservice.Util.MlMotorUtil;
import cn.ml_tech.mx.mlservice.Util.MotorObserverUtil;

import static android.content.ContentValues.TAG;
import static java.lang.Long.parseLong;
import static org.litepal.crud.DataSupport.find;
import static org.litepal.crud.DataSupport.findAll;
import static org.litepal.crud.DataSupport.findBySQL;
import static org.litepal.crud.DataSupport.where;

public class MotorServices extends Service {
    private List<DevParam> devParamList;
    private AlertDialog alertDialog;
    private MlMotorUtil mlMotorUtil;
    private Random random;
    private Intent intent;
    private String user_id = "";
    private long userid;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat audittraformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DetectionDetail detectionDetail;
    private long typeId;
    private long reportid;
    private DetectionReport detectionReport;
    private MotorObserverUtil motorObserverUtil;
    private Handler handler;

    public MotorServices() {
        initMemberData();
        getDevParams();
        Log.d(TAG, "MotorServices: " + String.valueOf(devParamList.size()));
    }

    private void initMemberData() {
        devParamList = new ArrayList<DevParam>();
    }

    public List<DevParam> getDevParams() {
        devParamList.clear();
        devParamList = DataSupport.select("id", "paramName", "paramValue", "type")
                // .where("paramName=?",paramName)
                // .where("type=?",String.valueOf(type))
                .order(" id asc")
                .find(DevParam.class);
        return devParamList;
    }

    private void log(String message) {
        Log.v("zw", message);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("Received start command.");
        return START_STICKY;
    }

    public List<DrugControls> mDrugControls = new ArrayList<>();

    private final IMlService.Stub mBinder = new IMlService.Stub() {
        @Override
        public void addMotorControl(MotorControl mControl) throws RemoteException {
            log("Received addMotorControl.");
        }

        @Override
        public void saveBottlePara(BottlePara bottlepara) throws RemoteException {
            //之后的代码有待完成
        }

        @Override
        public boolean checkAuthority(String name, String password) throws RemoteException {
            log(name);
            log(password);
            List<cn.ml_tech.mx.mlservice.DAO.User> users = where("userName = ? and userPassword = ?", name, password).find(cn.ml_tech.mx.mlservice.DAO.User.class);
            log(users.size() + "userssize");
            if (users.size() != 0) {
                user_id = users.get(0).getUserId();
                userid = users.get(0).getId();
                typeId = users.get(0).getUsertype_id();
                Log.d("zw", "typeId " + typeId);
            }

            LoginLog loginLog = new LoginLog();
            loginLog.setUser_id(userid);
            loginLog.setLoginDateTime(new Date());
            loginLog.save();

            return users.size() == 0 ? false : true;
        }

        @Override
        public void startCalibration() throws RemoteException {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    for (int i = 0; i < 8; i++) {
                        try {
                            Thread.sleep(2000);
                            intent = new Intent();
                            intent.setAction("com.calibration");
                            intent.putExtra("state", i);
                            if (i == 5) {
                                intent.putExtra("standard40", "4.82px");
                                intent.putExtra("standard50", "4.82px");
                                intent.putExtra("standard60", "4.82px");
                                intent.putExtra("variance40", "3.29");
                                intent.putExtra("variance60", "18.29");
                                intent.putExtra("statime", "0.00s");
                                intent.putExtra("stotime", "0.00s");
                                intent.putExtra("stpstate", "normal");
                                intent.putExtra("stostate", "normal");
                                intent.putExtra("colorcoefficient", "18.29");
                            }
                            sendBroadcast(intent);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        @Override
        public boolean addDrugInfo(String name, String enName, String pinYin, int containterId, int factoryId, String id) throws RemoteException {
            DrugInfo drugInfo = new DrugInfo();
            log(name + " " + enName + " " + pinYin + " containerid" + containterId + " factoryId" + factoryId);
            drugInfo.setName(name.trim());
            drugInfo.setEnname(enName.trim());
            drugInfo.setPinyin(pinYin.trim());
            drugInfo.setDrugcontainer_id(containterId);
            drugInfo.setFactory_id(factoryId);
            drugInfo.setCreatedate(new Date());
            if (Integer.parseInt(id) == 0) {
                drugInfo.save();
            } else {
                drugInfo.setId(parseLong(id));
                drugInfo.saveOrUpdate("id = ?", String.valueOf(drugInfo.getId()));
            }
            log("add Drug info....");
            return true;
        }

        @Override
        public boolean addFactory(String name, String address, String phone, String fax, String mail, String contactName, String contactPhone, String webSite, String province_code, String city_code, String area_code) throws RemoteException {

            Factory factory = new Factory();
            factory.setName(name);
            factory.setAddress(address);
            factory.setPhone(phone);
            factory.setFax(fax);
            factory.setMail(mail);
            factory.setContactName(contactName);
            factory.setContactPhone(contactPhone);
            factory.setProvince_code(province_code);
            factory.setCity_code(city_code);
            factory.setArea_code(area_code);
            factory.save();
            return true;
        }

        @Override
        public List<FactoryControls> queryFactoryControl() throws RemoteException {
            List<FactoryControls> factoryControlses = new ArrayList<>();
            List<Factory> factories = findAll(Factory.class);
            for (Factory factory : factories) {
                FactoryControls factoryControls = new FactoryControls();
                factoryControls.setId(factory.getId());
                factoryControls.setName(factory.getName());
                factoryControls.setAddress(factory.getAddress());
                factoryControls.setPhone(factory.getPhone());
                factoryControls.setFax(factory.getFax());
                factoryControls.setMail(factory.getMail());
                factoryControls.setContactName(factory.getContactName());
                factoryControls.setContactPhone(factory.getContactPhone());
                factoryControls.setProvince_code(factory.getProvince_code());
                factoryControls.setCity_code(factory.getCity_code());
                factoryControls.setArea_code(factory.getArea_code());
                factoryControlses.add(factoryControls);
            }
            return factoryControlses;
        }


        @Override
        public List<DrugControls> queryDrugControl() throws RemoteException {
            mDrugControls.clear();
            List<DrugInfo> mDrugInfo = DataSupport.order("id desc").find(DrugInfo.class);

            for (int i = 0; i < mDrugInfo.size(); i++) {
                log(mDrugInfo.get(i).toString());
                List<DrugContainer> list = DataSupport.select(new String[]{"id", "name"}).where("id=?", String.valueOf(mDrugInfo.get(i).getDrugcontainer_id())).find(DrugContainer.class);
                String drugBottleType = list.get(0).getName();
                List<Factory> lists = DataSupport.select(new String[]{"*"}).where("id=?", String.valueOf(mDrugInfo.get(i).getFactory_id())).find(Factory.class);
                String factory_name = lists.get(0).getName();
                DrugControls drugControls = new DrugControls(mDrugInfo.get(i).getName(), drugBottleType, factory_name, mDrugInfo.get(i).getPinyin()
                        , mDrugInfo.get(i).getEnname(), mDrugInfo.get(i).getId());
                mDrugControls.add(drugControls);
            }
            return mDrugControls;
        }

        @Override
        public List<User> getUserList() throws RemoteException {
            List<User> list = new ArrayList<User>();
            Connector.getDatabase();
            List<User> listDao = DataSupport
                    .findAll(User.class);
            return listDao;
        }

        @Override
        public List<SpecificationType> getSpecificationTypeList() throws RemoteException {
            List<SpecificationType> typeList = new ArrayList<>();
            Connector.getDatabase();
            List<SpecificationType> types = findAll(SpecificationType.class);
            for (SpecificationType type : types) {
                SpecificationType specificationType = new SpecificationType();
                specificationType.setId(type.getId());
                specificationType.setName(type.getName());
                typeList.add(specificationType);
            }
            return typeList;
        }

        @Override
        public List<DevParam> getDeviceParamList(int type) throws RemoteException {
            List<DevParam> list = new ArrayList<DevParam>();
            for (DevParam param : devParamList) {
                if (type == param.getType()) list.add(param);
            }
            return list;

        }

        @Override
        public void setDeviceParamList(List<DevParam> list) throws RemoteException {
            for (DevParam param : list
                    ) {
                param.saveOrUpdate("paramName=?", param.getParamName());
            }
            getDevParams();
        }

        @Override
        public void setDrugParamList(List<DrugParam> list) throws RemoteException {
            log("add drugParas");
            for (DrugParam drugParam :
                    list
                    ) {
                log(drugParam.toString());
            }
            for (DrugParam drugParam :
                    list) {
                drugParam.saveOrUpdate("druginfo_id = ? and paramname = ?", String.valueOf(drugParam.getDruginfo_id()), drugParam.getParamname());
            }
        }

        @Override
        public double getDeviceParams(String paramName, int type) throws RemoteException {
            for (DevParam param : devParamList) {
                if (paramName.equals(param.getParamName()) && type == param.getType())
                    return param.getParamValue();
            }
            return 0;
        }

        @Override
        public DevUuid getDeviceManagerInfo() throws RemoteException {
            DevUuid devUuid = new DevUuid();
            devUuid = DataSupport.findFirst(DevUuid.class);
            return devUuid;
        }

        @Override
        public boolean setDeviceManagerInfo(DevUuid info) throws RemoteException {
            boolean flag = true;
            info.saveOrUpdate("id=?", String.valueOf(info.getId()));
            return flag;
        }

        @Override
        public void getTrayIcId() throws RemoteException {
            // TODO: 2017/8/28 获取托环编号
            Log.d("zw", "读托环");
            if (intent == null)
                intent = new Intent();
            mlMotorUtil.getTrayId(alertDialog, intent);

        }

        @Override
        public List<Tray> getTrayList() throws RemoteException {
            List<Tray> trayList = findAll(Tray.class);
            return trayList;
        }

        @Override
        public Tray getTray(int id) throws RemoteException {
            Tray tray = find(Tray.class, id);
            return tray;
        }

        @Override
        public boolean setTray(Tray tray) throws RemoteException {
            tray.saveOrUpdate("displayId=?", String.valueOf(tray.getDisplayId()));
            return tray.isSaved();
        }

        @Override
        public boolean delTray(Tray tray) throws RemoteException {
            int r = 0;
            r = DataSupport.deleteAll(Tray.class, "displayId=? and icId=?", String.valueOf(tray.getDisplayId()), tray.getIcId());
            if (r > 0) return true;
            else return false;
        }

        @Override
        public int setSystemConfig(List<SystemConfig> list) throws RemoteException {
            int count = 0;
            for (SystemConfig config : list
                    ) {
                if (config.saveOrUpdate("paramName=?", config.getParamName())) count++;
            }
            return count;
        }

        @Override
        public int setCameraParam(CameraParams config) throws RemoteException {
            int count = 0;
            log(config.getParamName() + config.getParamValue());
            if (config.saveOrUpdate("paramName=?", config.getParamName())) count++;
            return count;
        }

        @Override
        public List<SystemConfig> getSystemConfig() throws RemoteException {
            List<SystemConfig> listConfig = findAll(SystemConfig.class);
            return listConfig;
        }

        @Override
        public List<DetectionReport> getDetectionReportList(int reportId) throws RemoteException {
//            List<DetectionReport>list=DataSupport.findBySQL("");

            return null;
        }

        @Override
        public List<CameraParams> getCameraParams() throws RemoteException {
            List<CameraParams> listConfig = findAll(CameraParams.class);

            return listConfig;
        }

        @Override
        public List<AuditTrailInfoType> getAuditTrailInfoType() throws RemoteException {
            List<AuditTrailInfoType> eventTypes = new ArrayList<>();
            eventTypes = findAll(AuditTrailInfoType.class);
            return eventTypes;
        }

        @Override
        public List<AuditTrailEventType> getAuditTrailEventType() throws RemoteException {
            List<AuditTrailEventType> eventTypes = new ArrayList<>();
            eventTypes = findAll(AuditTrailEventType.class);
            return eventTypes;
        }

        @Override
        public List<AuditTrail> getAuditTrail(String starttime, String stoptime, String user, int event_id, int info_id) throws RemoteException {
            List<AuditTrail> auditTrails = new ArrayList<>();
            auditTrails = DataSupport.where("event_id = ? and info_id = ? and username = ?", event_id + "", info_id + "", user).find(AuditTrail.class);

            try {
                long start = Long.parseLong(dateFormat.format(format.parse(starttime)));
                long end = Long.parseLong(dateFormat.format(format.parse(stoptime)));
                Log.d("zw", "start " + start + " end " + end);
                for (int i = 0; i < auditTrails.size(); i++) {
                    AuditTrail auditTrail = auditTrails.get(i);
                    long current = Long.parseLong(dateFormat.format(format.parse(auditTrail.getTime())));
                    Log.d("zw", "current " + current);
                    if (current < start || current > end) {
                        auditTrails.remove(i);
                        i--;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return auditTrails;
        }

        @Override
        public List<DrugControls> queryDrugControlByInfo(String drugname, String pinyin, String enname, int page) throws RemoteException {
            mDrugControls.clear();
            List<DrugInfo> mDrugInfo = new ArrayList<>();
            log("page" + page);
            mDrugInfo = DataSupport.where("name like ? and enname like ? and pinyin like ?", drugname + "%", enname + "%", pinyin + "%").order("id desc").find(DrugInfo.class);
            if (page != -1) {
                mDrugInfo = DataSupport.where("name like ? and enname like ? and pinyin like ?", drugname + "%", enname + "%", pinyin + "%").limit(20).offset((page - 1) * 20).order("id desc").find(DrugInfo.class);
            }
            for (int i = 0; i < mDrugInfo.size(); i++) {
                log(mDrugInfo.get(i).toString());
                List<DrugContainer> list = DataSupport.select(new String[]{"id", "name"}).where("id=?", String.valueOf(mDrugInfo.get(i).getDrugcontainer_id())).find(DrugContainer.class);
                String drugBottleType = list.get(0).getName();
                List<Factory> lists = DataSupport.select(new String[]{"*"}).where("id=?", String.valueOf(mDrugInfo.get(i).getFactory_id())).find(Factory.class);
                String factory_name = lists.get(0).getName();
                DrugControls drugControls = new DrugControls(mDrugInfo.get(i).getName(), drugBottleType, factory_name, mDrugInfo.get(i).getPinyin()
                        , mDrugInfo.get(i).getEnname(), mDrugInfo.get(i).getId());
                mDrugControls.add(drugControls);
            }
            return mDrugControls;
        }

        @Override
        public void deleteDrugInfoById(int id) throws RemoteException {
            log(id + "id");
            DataSupport.delete(DrugInfo.class, id);
        }

        @Override
        public List<DrugContainer> getDrugContainer() throws RemoteException {
            List<DrugContainer> drugContainers = new ArrayList<>();
            drugContainers = DataSupport.findAll(DrugContainer.class);
            return drugContainers;
        }


        @Override
        public List<DrugParam> getDrugParamById(int id) throws RemoteException {
            List<DrugParam> drugParams = new ArrayList<>();
            drugParams = DataSupport.where("druginfo_id = ?", String.valueOf(id)).find(DrugParam.class);
            for (DrugParam drugParam :
                    drugParams
                    ) {
                log(drugParam.toString());
            }
            return drugParams;
        }

        /**
         * 遮光验证
         * @param drug_id 药品id 为零表示新建药品
         * @param location 遮光位置
         * @throws RemoteException
         */
        @Override
        public void Validate(int drug_id, int location) throws RemoteException {
            log("Validate location" + location + " drug_id" + drug_id);
            //调用底层jni 以下代码为模拟数据
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(500);
                        //模拟操作随机数被二整除为遮光验证成功
                        log("sucess");
                        intent = new Intent();
                        intent.setAction("com.enterbottle");
                        intent.putExtra("state", "Validate");
                        intent.putExtra("paratype", 2);//参数类型
                        intent.putExtra("colornum", 20);//色差系数
                        sendBroadcast(intent);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }

        @Override
        public List<DetectionReport> queryDetectionReport(String detectionSn, String drugInfo, String factoryName, String detectionNumber, String detectionBatch, String startTime, String stopTime, int page) throws RemoteException {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long start = 0, end = 0, current = 0;
//            Log.d("zw", "detectionSn " + detectionSn + " drugInfo " + drugInfo + " factoryName " + factoryName + " detectionNumber " + detectionNumber + " detectionBatch " + detectionBatch + " startTime " + startTime + " stopTime " + stopTime);
            List<DetectionReport> detectionReports = new ArrayList<>();
            detectionReports = DataSupport.where("drugName like ? and factoryName like ? and detectionSn like ? and drugBottleType = ? and detectionBatch like ?", drugInfo + "%", factoryName + "%", detectionSn + "%", detectionNumber.trim(), detectionBatch + "%").find(DetectionReport.class);
            if (page != -1) {
                detectionReports = DataSupport.where("drugName like ? and factoryName like ? and detectionSn like ? and drugBottleType = ? and detectionBatch like ?", drugInfo + "%", factoryName + "%", detectionSn + "%", detectionNumber.trim(), detectionBatch + "%").limit(20).offset(((page - 1) * 20)).find(DetectionReport.class);

            }
//            Log.d("zw", "detectionReports size " + detectionReports.size());
            try {
                for (int i = 0; i < detectionReports.size(); i++) {
                    DetectionReport detectionReport = detectionReports.get(i);
                    start = Long.parseLong(dateFormat.format(simpleDateFormat.parse(startTime)));
                    end = Long.parseLong(dateFormat.format(simpleDateFormat.parse(stopTime)));
                    current = Long.parseLong(dateFormat.format(detectionReport.getDate()));
                    if (current < start || current > end) {
                        detectionReports.remove(i);
                        i--;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            return detectionReports;
        }

        @Override
        public void enterBottle() throws RemoteException {
            //调用jni进瓶，以下代码为模拟操作
            random = new Random();
            log("enterBottle");
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        //模拟操作随机数被二整除为进瓶成功
                        log("sucess");
                        intent = new Intent();
                        intent.setAction("com.enterbottle");
                        intent.putExtra("state", "sucess");
                        sendBroadcast(intent);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        /**旋瓶测试
         * @param num 每分钟转数
         * @throws RemoteException
         */
        @Override
        public void bottleTest(int num) throws RemoteException {
            //jni层调用
            log("每分钟转数" + num);
            alertDialog.callback("托环不匹配");
        }

        @Override
        public void leaveBottle() throws RemoteException {
            //jni层 出瓶
            log("出瓶");
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //模拟操作随机数被二整除为进瓶成功
                    log("sucess");
                    intent = new Intent();
                    intent.setAction("com.enterbottle");
                    intent.putExtra("state", "leavebottlesucess");
                    sendBroadcast(intent);

                }
            }.start();
        }

        @Override
        public void deteleDetectionInfoById(long id) throws RemoteException {
            DataSupport.delete(DetectionReport.class, id);
            DataSupport.deleteAll(DetectionDetail.class, "detectionreport_id = ?", id + "");
        }

        @Override
        public void deleteDrugParamById(int id) throws RemoteException {
            DataSupport.deleteAllAsync(DrugParam.class, "druginfo_id = ? ", String.valueOf(id));
        }

        /**
         * @param drug_id
         * @param checkNum
         * @param rotateNum
         * @param detectionNumber
         * @param detectionBatch
         * @param isFirst
         * @param detectionSn
         * @throws RemoteException
         */
        @Override
        public void startCheck(int drug_id, final int checkNum, int rotateNum, final String detectionNumber, String detectionBatch, final boolean isFirst, final String detectionSn) throws RemoteException {
            Log.d("zw", drug_id + "check " + checkNum + "rotateNum " + rotateNum + "detectionBatch " + detectionBatch + isFirst);
            if (detectionSn.equals("")) {
                if (isFirst) {
                    String sn = getDetectionSn();
                    MotorServices.this.addAudittrail(1, 1, sn, "Save the new information");
                    detectionReport = new DetectionReport();
                    detectionReport.setDetectionSn(sn);
                    detectionReport.setDetectionBatch(detectionBatch);
                    detectionReport.setDetectionNumber(detectionNumber);
                    detectionReport.setDrugBottleType(DataSupport.find(DrugContainer.class, DataSupport.find(DrugInfo.class, drug_id).getDrugcontainer_id()).getName());
                    detectionReport.setUser_id(userid);
                    detectionReport.setUserName(user_id);
                    detectionReport.setDate(new Date());
                    detectionReport.setDetectionCount(checkNum);
                    detectionReport.setDruginfo_id(drug_id);
                    detectionReport.setDetectionCount(checkNum);
                    detectionReport.setDrugName(DataSupport.find(DrugInfo.class, drug_id).getName());
                    detectionReport.setFactoryName(DataSupport.find(Factory.class, DataSupport.find(DrugInfo.class, drug_id).getFactory_id()).getName());
                } else {
                    detectionReport = DataSupport.findLast(DetectionReport.class);
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            for (int i = 0; i < checkNum; i++) {
                                Thread.sleep(500);
                                try {
                                    if (isFirst) {
                                        detectionReport.setDetectionFirstCount(i + 1);
                                    } else
                                        detectionReport.setDetectionSecondCount(i + 1);
                                    if (i == 0) {
                                        detectionReport.save();
                                        detectionReport.clearSavedState();
                                        reportid = detectionReport.getId();
                                    } else {
                                        detectionReport.update(detectionReport.getId());
                                    }
                                    simulatieDate(i, checkNum, isFirst, reportid, detectionSn);
                                    if ((i == 0) && (detectionSn.equals("")) && isFirst) {
                                        saveCheckDate();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } else {
                Log.d("zw", "detectionsn" + detectionSn);
                List<DetectionReport> detectionReports = DataSupport.where("detectionSn = ?", detectionSn).find(DetectionReport.class);
                final DetectionReport Report = detectionReports.get(0);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            try {
                                Thread.sleep(500);
                                Log.d("zw", "secondcount" + Report.getDetectionSecondCount());
                                if (Report.getDetectionSecondCount() == 0 && Report.getDetectionCount() > Report.getDetectionFirstCount()) {
                                    for (int i = Report.getDetectionFirstCount(); i < checkNum; i++) {
                                        reportid = Report.getId();
                                        Report.setDetectionFirstCount(Report.getDetectionFirstCount() + 1);
                                        Report.update(Report.getId());
                                        simulatieDate(i, checkNum, isFirst, reportid, detectionSn);
                                    }
                                } else {
                                    for (int i = Report.getDetectionSecondCount(); i < checkNum; i++) {
                                        Report.setDetectionSecondCount(Report.getDetectionSecondCount() + 1);
                                        Report.update(Report.getId());
                                        reportid = Report.getId();
                                        simulatieDate(i, checkNum, isFirst, reportid, detectionSn);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }


        @Override
        public String getDetectionSn() throws RemoteException {
            String result = "";
            String uuid = getDevUuid();
            String date = getDetectionSnDate();
            result = uuid + date;
            log(result);
            return result;
        }

        @Override
        public DetectionDetail getLastDetail() throws RemoteException {
            return DataSupport.findLast(DetectionDetail.class);
        }


        @Override
        public List<DetectionDetail> queryDetectionDetailByReportId(long id) throws RemoteException {
            List<DetectionDetail> detectionDetails = new ArrayList<>();
            detectionDetails = DataSupport.where("detectionreport_id = ?", id + "").order("id asc").find(DetectionDetail.class);
            Log.d("zw", detectionDetails.size() + "size");
            return detectionDetails;
        }

        @Override
        public DetectionReport getLastReport() throws RemoteException {
            DetectionReport detectionReport = null;
            detectionReport = DataSupport.findLast(DetectionReport.class);
            return detectionReport;
        }

        @Override
        public DrugControls queryDrugControlsById(long id) throws RemoteException {
            DrugInfo drugInfo = DataSupport.find(DrugInfo.class, id);
            List<DrugContainer> list = DataSupport.select(new String[]{"id", "name"}).where("id=?", String.valueOf(drugInfo.getDrugcontainer_id())).find(DrugContainer.class);
            String drugBottleType = list.get(0).getName();
            List<Factory> lists = DataSupport.select(new String[]{"*"}).where("id=?", String.valueOf(drugInfo.getFactory_id())).find(Factory.class);
            String factory_name = lists.get(0).getName();
            DrugControls drugControls = new DrugControls(drugInfo.getName(), drugBottleType, factory_name, drugInfo.getPinyin()
                    , drugInfo.getEnname(), drugInfo.getId());
            return drugControls;
        }

        @Override
        public DevUuid getDevUuidInfo() throws RemoteException {

            return DataSupport.find(DevUuid.class, 1);
        }

        @Override
        public List<DetectionReport> getAllDetectionReports(boolean isSelf) throws RemoteException {
            if (!isSelf) {
                return DataSupport.findAll(DetectionReport.class);
            } else {
                return DataSupport.where("user_id = ?", userid + "").find(DetectionReport.class);
            }
        }

        @Override
        public List<UserType> getAllUserType() throws RemoteException {
            return DataSupport.findAll(UserType.class);
        }

        @Override
        public void updateUser(User user) throws RemoteException {
            if (user.getId() != 0) {
                user.saveOrUpdate("id = ?", user.getId() + "");
            } else {
                String createdate = format.format(new Date());
                user.setCreateDate(createdate);
                user.save();
            }
            if (DataSupport.find(User.class, userid) != null)
                typeId = DataSupport.find(User.class, userid).getUsertype_id();
        }

        @Override
        public UserType getUserTypeById(long id) throws RemoteException {
            return DataSupport.find(UserType.class, id);
        }

        @Override
        public void deleteUserById(long id) throws RemoteException {
            Log.d("zw", "service delete user " + id);
            DataSupport.delete(User.class, id);
        }

        @Override
        public void addAudittrail(int event_id, int info_id, String value, String mark) throws RemoteException {
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setTime(audittraformat.format(new Date()));
            auditTrail.setUsername(user_id);
            auditTrail.setEvent_id(event_id);
            auditTrail.setInfo_id(info_id);
            auditTrail.setValue(value);
            auditTrail.setMark(mark);
            auditTrail.setUserauto_id(0);
            auditTrail.save();
        }

        @Override
        public List<String> getAllTableName() throws RemoteException {
            List<String> result = new ArrayList<>();
            Cursor cursor = findBySQL("SELECT name FROM sqlite_master WHERE type='table' order by name");
            while (cursor.moveToNext()) {
                if (!cursor.getString(cursor.getColumnIndex("name")).trim().equals("android_metadata"))
                    result.add(cursor.getString(cursor.getColumnIndex("name")));
            }
            return result;
        }

        @Override
        public List<String> getFieldByName(String name) throws RemoteException {
            List<String> result = new ArrayList<>();
            Cursor cursor = findBySQL("pragma table_info(" + name + ")");
            while (cursor.moveToNext()) {
                result.add(cursor.getString(cursor.getColumnIndex("name")));
            }
            return result;
        }

        @Override
        public Modern getDataByTableName(String tableName) throws RemoteException {
            Modern modern = new Modern();
            Map<Integer, List<String>> dataMap = new HashMap<>();
            List<String> field = new ArrayList<>();
            Cursor cursor = DataSupport.findBySQL("pragma table_info(" + tableName + ")");
            while (cursor.moveToNext()) {
                field.add(cursor.getString(cursor.getColumnIndex("name")));
            }
            cursor = DataSupport.findBySQL("select *from " + tableName);
            int i = 0;
            while (cursor.moveToNext()) {
                List<String> data = new ArrayList<>();
                for (int itme = 0; itme < field.size(); itme++) {
                    data.add(cursor.getString(cursor.getColumnIndex(field.get(itme))));
                }
                dataMap.put(i, data);
                Log.d("zw", " i = " + i);
                i++;
            }
            modern.setMap(dataMap);
            return modern;
        }

        /**
         * @param tableName
         * @param modern
         * @throws RemoteException
         */
        @Override
        public void updateData(String tableName, Modern modern) throws RemoteException {
            Log.d("zw", "updatedata");
            Map<Integer, List<String>> data = modern.getMap();
            List<String> field = new ArrayList<>();
            Cursor cursor = DataSupport.findBySQL("pragma table_info(" + tableName + ")");
            while (cursor.moveToNext()) {
                field.add(cursor.getString(cursor.getColumnIndex("name")));
            }
            for (int i = 0; i < data.size(); i++) {
                cursor = DataSupport.findBySQL("select * from " + tableName + " where id = " + data.get(i).get(0));
                if (cursor.moveToNext()) {
                    ContentValues values = new ContentValues();
                    for (int s = 0; s < field.size(); s++) {
                        values.put(field.get(s), data.get(i).get(s));
                    }
                    DataSupport.updateAll(tableName, values, field.get(0) + " = ?", data.get(i).get(0) + "");
                } else {
                    Log.d("zw", "insert into ");
                    SQLiteDatabase sqLiteDatabase = LitePal.getDatabase();
                    ContentValues value = new ContentValues();
                    for (int c = 0; c < field.size(); c++) {
                        value.put(field.get(c), data.get(i).get(c));
                    }
                    sqLiteDatabase.insert(tableName, null, value);
                }
            }
        }

        @Override
        public void deleteData(String tableName, List<String> id) throws RemoteException {
            Log.d("zw", "start deleteData");

            for (int i = 0; i < id.size(); i++) {
                Log.d("zw", " id " + id.get(i));
                DataSupport.deleteAll(tableName, "id = ?", id.get(i));
            }
        }

        @Override
        public List<P_Source> getRootP_Source() throws RemoteException {
            List<P_Source> p_sources = new ArrayList<>();
            p_sources = DataSupport.findAll(P_Source.class);
            for (int i = 0; i < p_sources.size(); i++) {
                P_Source p_source = p_sources.get(i);
                if (p_source.getUrl().contains("/")) {
                    p_sources.remove(i);
                    i--;
                }
            }
            return p_sources;
        }

        @Override
        public List<P_Source> getP_SourceByUrl(String url) throws RemoteException {
            List<P_Source> p_sources = new ArrayList<>();
            p_sources = DataSupport.findAll(P_Source.class);
            for (int i = 0; i < p_sources.size(); i++) {
                P_Source p_source = p_sources.get(i);
                if ((!p_source.getUrl().contains(url))) {
                    p_sources.remove(i);
                    i--;
                }
            }

            return p_sources;
        }

        @Override
        public PermissionHelper getP_OperatorBySourceId(long id) throws RemoteException {
            PermissionHelper permissionHelper = new PermissionHelper();
            LinkedHashMap<Long, P_Operator> pOperatorMap = new LinkedHashMap<>();
            List<P_SourceOperator> p_sourceOperators = DataSupport.where("p_source_id = ?", id + "").order("id asc").find(P_SourceOperator.class);
            for (P_SourceOperator p_sourceOperator :
                    p_sourceOperators) {
                Log.d("zw", "service sourceoperateid " + p_sourceOperator.getId());
                P_Operator p_operator = DataSupport.find(P_Operator.class, p_sourceOperator.getP_operator_id());
                pOperatorMap.put(p_sourceOperator.getId(), p_operator);
            }
            permissionHelper.setP_operatorMap(pOperatorMap);
            return permissionHelper;
        }

        @Override
        public boolean isOperate(long sourceoperateid, long userTypeId) throws RemoteException {
            List<P_UserTypePermission> p_userPermissions = DataSupport.where("p_sourceoperator_id = ? and usertype = ?", sourceoperateid + "", userTypeId + "").find(P_UserTypePermission.class);
            if (p_userPermissions.size() == 0) {
                return false;
            } else {
                if (p_userPermissions.get(0).getRighttype() == 1) {
                    return true;
                } else {
                    return false;
                }
            }

        }

        @Override
        public void deletePermission(long sourceoperateid, long userTypeId) throws RemoteException {
            Log.d("zw", "deletePermission sourceoperateid " + sourceoperateid + " typeId " + userTypeId);
            DataSupport.deleteAll(P_UserTypePermission.class, "p_sourceoperator_id = ? and usertype = ?", sourceoperateid + "", userTypeId + "");

        }

        @Override
        public void addPermission(long sourceoperateid, long userTypeId) throws RemoteException {
            P_UserTypePermission p_userTypePermission = new P_UserTypePermission();
            p_userTypePermission.setP_sourceoperator_id(sourceoperateid);
            p_userTypePermission.setUsertype(userTypeId);
            p_userTypePermission.setRighttype(1);
            p_userTypePermission.saveOrUpdate("p_sourceoperator_id = ? and usertype = ?", sourceoperateid + "", userTypeId + "");
        }

        @Override
        public boolean canAddType(String typeName) throws RemoteException {
            List<UserType> userTypes = DataSupport.where("typeName = ?", typeName.trim()).find(UserType.class);
            return userTypes.isEmpty();
        }

        @Override
        public void addUserType(String typeName, List<String> sourceoperateId) throws RemoteException {
            UserType userType = new UserType();
            userType.setTypeName(typeName);
            userType.setTypeId(DataSupport.findLast(UserType.class).getTypeId() + 1);
            userType.save();
            long typeId = userType.getTypeId();
            for (String soid :
                    sourceoperateId) {
                long id = Long.parseLong(soid);
                MotorServices.this.addPermission(id, typeId);
            }
        }

        @Override
        public Permission getPermissonByUrl(String url, boolean isRoot) throws RemoteException {
            Permission permission = new Permission();
            LinkedHashMap<String, Boolean> linkedHashMap = new LinkedHashMap<>();
            List<P_Source> p_sources = new ArrayList<>();
            p_sources = DataSupport.findAll(P_Source.class);
            for (int i = 0; i < p_sources.size(); i++) {
                P_Source p_source = p_sources.get(i);
                if (isRoot) {
                    if (p_source.getUrl().contains("/")) {
                        p_sources.remove(i);
                        i--;
                    }
                } else {
                    if (!p_source.getUrl().contains(url + "/")) {
                        p_sources.remove(i);
                        i--;
                    }
                }
            }
            for (P_Source p_source : p_sources
                    ) {
                List<P_SourceOperator> p_sourceOperators = DataSupport.where("p_source_id = ?", p_source.getId() + "").find(P_SourceOperator.class);
                for (P_SourceOperator sourceOperator : p_sourceOperators
                        ) {
                    List<P_Operator> p_operators = DataSupport.
                            where("id = ?", sourceOperator.getP_operator_id() + "").find(P_Operator.class);
                    List<P_UserTypePermission> p_userTypePermissions = DataSupport.where("p_sourceoperator_id = ? and usertype = ?", sourceOperator.getId() + "", typeId + "").find(P_UserTypePermission.class);
                    linkedHashMap.put(p_source.getTitle() + p_operators.get(0).getTitle(), !p_userTypePermissions.isEmpty());
                    Log.d("zw", p_source.getTitle() + " " + p_operators.get(0).getTitle() + " " + !p_userTypePermissions.isEmpty());

                }
            }
            permission.setPermissiondata(linkedHashMap);
            return permission;
        }

        @Override
        public List<P_Source> getAllP_Source() throws RemoteException {
            List<P_Source> p_sources = new ArrayList<>();
            p_sources = DataSupport.findAll(P_Source.class);
            return p_sources;
        }

        @Override
        public List<P_Operator> getAllP_Operator() throws RemoteException {
            List<P_Operator> p_operators = new ArrayList<>();
            p_operators = DataSupport.findAll(P_Operator.class);
            return p_operators;
        }

        @Override
        public List<DevParam> getDevParamByType(int type) throws RemoteException {
            List<DevParam> devParams = new ArrayList<>();
            devParams = DataSupport.where("type = ?", type + "").find(DevParam.class);
            return devParams;
        }

        @Override
        public void saveDevParam(List<DevParam> devParams) throws RemoteException {
            for (DevParam devParam :
                    devParams) {
                if (devParam.getId() == 0)
                    devParam.save();
                else
                    devParam.update(devParam.getId());
            }
        }

        @Override
        public void saveDetectionReport(DetectionReport detectionReport) throws RemoteException {
            detectionReport.update(detectionReport.getId());
//            Log.d("zw", "detectionReport id " + detectionReport.getId());
        }

        @Override
        public void deleteDevParamByIds(List<String> ids) throws RemoteException {
            for (String s :
                    ids) {
                DataSupport.delete(DevParam.class, Long.parseLong(s));
            }
        }

        @Override
        public void backUpDevParam() throws RemoteException {
            List<DevParam> devParams = DataSupport.findAll(DevParam.class);
            DataSupport.deleteAll(DevDynamicParams.class, "1=1");
            DevDynamicParams devDynamicParams = new DevDynamicParams();
            for (DevParam devParam :
                    devParams) {
                devDynamicParams.setId(devParam.getId());
                devDynamicParams.setParamName(devParam.getParamName());
                devDynamicParams.setParamValue(devParam.getParamValue());
                devDynamicParams.setType(devParam.getType());
                devDynamicParams.save();
                devDynamicParams.clearSavedState();
            }
        }

        @Override
        public void recoveryParam() throws RemoteException {
            List<DevDynamicParams> devParams = DataSupport.findAll(DevDynamicParams.class);
            DataSupport.deleteAll(DevParam.class, "1=1");
            DevParam devDynamicParams = new DevParam();
            for (DevDynamicParams devParam :
                    devParams) {
                devDynamicParams.setId(devParam.getId());
                devDynamicParams.setParamName(devParam.getParamName());
                devDynamicParams.setParamValue(devParam.getParamValue());
                devDynamicParams.setType(devParam.getType());
                devDynamicParams.save();
                devDynamicParams.clearSavedState();
            }
        }

        @Override
        public long getUserId() throws RemoteException {
            return userid;
        }

        @Override
        public long geTypeId() throws RemoteException {
//            Log.d("Zb", "service TypeId " + typeId);
            return typeId;
        }

        @Override
        public void deleteDetectionReportsById(List<String> ids) throws RemoteException {
            for (String id :
                    ids) {
//                Log.d("zw", "ddetectionReport id " + id);
                DataSupport.delete(DetectionReport.class, Long.parseLong(id.trim()));
                DataSupport.deleteAll(DetectionDetail.class, "detectionreport_id = ?", id);

            }
        }

        @Override
        public void operateMlMotor(int type, int dir, double avgspeed, int distance) throws RemoteException {
//            Log.d("zw", "type " + type + " dir" + dir + " avgspeed " + avgspeed + " distance " + distance);
            mlMotorUtil.operateMlMotor(type, dir, avgspeed, distance);
        }

        @Override
        public void operateLight(boolean isOn) throws RemoteException {
//            Log.d("TAGZW", "BOO " + isOn);
            if (isOn) {
                mlMotorUtil.getMlMotor().motorLightOn();
            } else {
                mlMotorUtil.getMlMotor().motorLightOff();
            }
        }

        @Override
        public void rotaleBottle(int speed) throws RemoteException {
            Log.d("Tagzw", "speed " + speed);
            mlMotorUtil.operateRotale(speed);
        }

        @Override
        public List<DevParam> getAllDevParam() throws RemoteException {
            List<DevParam> devParams = new ArrayList<>();
            devParams = DataSupport.findAll(DevParam.class);
            return devParams;
        }

        @Override
        public void saveAllDevParam(List<DevParam> devParams) throws RemoteException {
            for (DevParam devParam : devParams) {
                devParam.saveOrUpdate("paramname = ?", devParam.getParamName());
                Log.d("ww", "name " + devParam.getParamName() + " value " + devParam.getParamValue());
            }
        }

        @Override
        public void motorReset(int num) throws RemoteException {
            mlMotorUtil.motorReset(num);
        }

        @Override
        public void autoDebug(int num) throws RemoteException {
            Log.d("zw", "server autoDebug");
//            double distance = DataSupport.where("paramname = ?", "MachineHandDistance1").find(DevParam.class).get(0).getParamValue();
//            double speed = DataSupport.where("paramname = ?", "MachineHandSpeed").find(DevParam.class).get(0).getParamValue();
//            Log.d("zw", "motor finish distance " + distance + " speed " + speed);
//            motorObserverUtil.operateMotor(CommonUtil.Device_MachineHand, 1, speed, (int) distance, MotorObserverUtil.MOTORCATCH,
//                    motorObserverUtil.getStateHandler());
            motorObserverUtil.autoCheck(handler,num);

        }


    };

    @Override
    public IBinder onBind(Intent intent) {
        mlMotorUtil = MlMotorUtil.getInstance(this);
        alertDialog = new AlertDialog();
        alertDialog.setContext(this);
        motorObserverUtil = new MotorObserverUtil(mlMotorUtil);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                alertDialog.callback("自动检测完成");
            }
        };
        Connector.getDatabase();
        if (!DataSupport.isExist(UserType.class)) {
            UserType userType = new UserType();
            userType.setTypeId(0);
            userType.setTypeName("超级管理员");
            userType.save();
            userType.clearSavedState();
            userType.setTypeId(1);
            userType.setTypeName("管理员");
            userType.save();
            userType.clearSavedState();
            userType.setTypeId(2);
            userType.setTypeName("操作员");
            userType.save();
        }
        if (!DataSupport.isExist(User.class)) {
            User user = new User();
            user.setUsertype_id(1);
            user.setUserId("Admin");
            user.setIsEnable(1);
            user.setUserName("AdminName");
            user.setUserPassword("Admin");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setCreateDate(simpleDateFormat.format(new Date()));
            user.save();
            user.clearSavedState();
            user.setUsertype_id(0);
            user.setUserId("zw1025");
            user.setIsEnable(1);
            user.setUserName("zw1025");
            user.setUserPassword("zw1025");
            user.setCreateDate(simpleDateFormat.format(new Date()));
            user.save();
        }
        if (LogUtil.isApkInDebug(this)) {
            if (!DataSupport.isExist(AuditTrailEventType.class)) {
                AuditTrailEventType eventType = new AuditTrailEventType();
                eventType.setName("Add");
                eventType.save();
                eventType.clearSavedState();
                eventType.setName("Delete");
                eventType.save();
                eventType.clearSavedState();
                eventType.setName("Modify");
                eventType.save();
                eventType.clearSavedState();
                eventType.setName("Query");
                eventType.save();
                eventType.clearSavedState();
                eventType.setName("Other");
                eventType.save();
                eventType.clearSavedState();
            }
            if (!DataSupport.isExist(AuditTrail.class)) {
                AuditTrail auditTrail = new AuditTrail();
                auditTrail.setMark("testmark");
                auditTrail.setValue("testvalue");
                auditTrail.setTime("2016-06-29");
                auditTrail.setEvent_id(1);
                auditTrail.setInfo_id(1);
                auditTrail.setUsername("testusername");
                auditTrail.save();
                auditTrail.clearSavedState();
                auditTrail.setMark("admin");
                auditTrail.setValue("admin");
                auditTrail.setTime("2017-07-15");
                auditTrail.setEvent_id(2);
                auditTrail.setInfo_id(1);
                auditTrail.setUsername("zw1025");
                auditTrail.save();
            }
            if (!DataSupport.isExist(AuditTrailInfoType.class)) {
                AuditTrailInfoType infoType = new AuditTrailInfoType();
                infoType.setName("Information");
                infoType.save();
                infoType.clearSavedState();
                infoType.setName("Question");
                infoType.save();
                infoType.clearSavedState();
                infoType.setName("Warning");
                infoType.save();
                infoType.clearSavedState();
                infoType.setName("Critical");
                infoType.save();
                infoType.clearSavedState();
                infoType.setName("Other");
                infoType.save();
                infoType.clearSavedState();
            }
            if (!DataSupport.isExist(DrugContainer.class)) {
                DrugContainer drugContainer = new DrugContainer();
                drugContainer.setName("安瓿瓶1ml");
                drugContainer.setDiameter(0);
                drugContainer.setTray_id(8);
                drugContainer.setSrctime(4.0);
                drugContainer.setStptime(3.0);
                drugContainer.setChannelvalue1(50);
                drugContainer.setChannelvalue2(2.5);
                drugContainer.setChannelvalue3(1.5);
                drugContainer.setChannelvalue4(2.7);
                drugContainer.setDelaytime(0.11);
                drugContainer.setImagetime(0.01);
                drugContainer.setShadeparam(22.0);
                drugContainer.setRotatespeed(4500);
                drugContainer.setSendparam(2.0);
                drugContainer.save();
                drugContainer.clearSavedState();
                drugContainer.setName("安瓿瓶2ml");
                drugContainer.setDiameter(0);
                drugContainer.setTray_id(2);
                drugContainer.setSrctime(2.0);
                drugContainer.setStptime(2.0);
                drugContainer.setChannelvalue1(20);
                drugContainer.setChannelvalue2(2.2);
                drugContainer.setChannelvalue3(1.2);
                drugContainer.setChannelvalue4(2.2);
                drugContainer.setShadeparam(2.0);
                drugContainer.setRotatespeed(4500);
                drugContainer.setSendparam(2.0);
                drugContainer.setDelaytime(0.12);
                drugContainer.setImagetime(0.01);
                drugContainer.save();
            }
            if (!DataSupport.isExist(DrugInfo.class)) {
                DrugInfo drugInfo = new DrugInfo();
                for (int i = 0; i < 73; i++) {
                    drugInfo.setName("weiyi" + i);
                    drugInfo.setPinyin("piniyni" + i);
                    drugInfo.setEnname("enname" + i);
                    drugInfo.setCreatedate(new Date());
                    drugInfo.setFactory_id(1);
                    drugInfo.setUser_id(1);
                    drugInfo.setDrugcontainer_id(1);
                    drugInfo.save();
                    drugInfo.clearSavedState();
                }

            }
            if (!DataSupport.isExist(SystemConfig.class)) {
                SystemConfig config = new SystemConfig();
                config.setParamName("LastDetDate");
                config.setParamValue("20170619");
                config.save();
                config.clearSavedState();
                config.setParamName("LastDetNum");
                config.setParamValue("2");
                config.save();
                config.clearSavedState();
                config.setParamName("IpAddress");
                config.setParamValue("192.168.0.145");
                config.save();
                config.clearSavedState();
                config.setParamName("NetMask");
                config.setParamValue("255.255.255.0");
                config.save();
                config.clearSavedState();
                config.setParamName("DiskUsedMax");
                config.setParamValue("90.0");
                config.save();
                config.clearSavedState();
                config.setParamName("GlassTimeMax");
                config.setParamValue("3.0");
                config.save();
                config.clearSavedState();
                config.setParamName("GlassCountMax");
                config.setParamValue("20.0");
                config.save();
                config.clearSavedState();
                config.setParamName("FiberMax");
                config.setParamValue("2");
                config.save();
                config.clearSavedState();
                config.setParamName("FloatMax");
                config.setParamValue("2");
                config.save();
                config.clearSavedState();
                config.setParamName("StandardDrugRotateCount");
                config.setParamValue("3");
                config.save();
                config.clearSavedState();
                config.setParamName("userRemeber");
                config.setParamValue("0");
                config.save();
                config.clearSavedState();
                config.setParamName("userName");
                config.setParamValue("admin");
                config.save();
                config.clearSavedState();
                config.setParamName("programVersionNum");
                config.setParamValue("1000109");
                config.save();
                config.clearSavedState();
                config.setParamName("programVersionStr");
                config.setParamValue("1.0.1.11");
                config.save();
                config.clearSavedState();
                config.setParamName("databaseVersion");
                config.setParamValue("5");
                config.save();
                config.clearSavedState();
                config.setParamName("databaseVersion");
                config.setParamValue("5");
                config.save();
                config.clearSavedState();
                config.setParamName("userPass");
                config.setParamValue("#&/+,");
                config.save();
                config.clearSavedState();
                config.setParamName("LastStandardId");
                config.setParamValue("4");
                config.save();

            }
        }
        if (!DataSupport.isExist(Factory.class)) {
            Factory factory = new Factory();
            factory.setAddress("asdfgh");
            factory.setName("湖南药厂");
            factory.save();
        }

        if (!DataSupport.isExist(DevUuid.class)) {
            DevUuid devUuid = new DevUuid();
            devUuid.setUserAbbreviation("admin");
            devUuid.setUserName("admin");
            devUuid.setDevID("YA2C1CY00R03002");
            devUuid.setDevModel("ML-AMIXH-2.5");
            devUuid.setDevName("光散射法全自动可见异物检测仪");
            devUuid.setDevFactory("浙江猛凌机电科技有限公司");
            devUuid.setDevDateOfProduction(new Date());
            devUuid.save();
        }
        if (!DataSupport.isExist(CameraParams.class)) {
            CameraParams cameraParams = new CameraParams();
            cameraParams.setParamName("flashGain");
            cameraParams.setParamValue(4.0);
            cameraParams.save();
            cameraParams.clearSavedState();
            cameraParams.setParamName("x_addr_end");
            cameraParams.setParamValue(1280.0);
            cameraParams.save();
            cameraParams.clearSavedState();
            cameraParams.setParamName("y_addr_end");
            cameraParams.setParamValue(768.0);
            cameraParams.save();
            cameraParams.clearSavedState();
            cameraParams.setParamName("Exposure");
            cameraParams.setParamValue(4.0);
            cameraParams.save();
            cameraParams.clearSavedState();
            cameraParams.setParamName("fpgaGain");
            cameraParams.setParamValue(0.0);
            cameraParams.save();
            cameraParams.clearSavedState();
            cameraParams.setParamName("globalGain");
            ;
            cameraParams.clearSavedState();
            cameraParams.setParamName("digitalGain");
            cameraParams.setParamValue(86.0);
            cameraParams.setParamValue(1.0);
            cameraParams.save();
            cameraParams.save();
            cameraParams.clearSavedState();
            cameraParams.setParamName("fpgaFilter");
            cameraParams.setParamValue(0.0);
            cameraParams.save();
        }
        if (!DataSupport.isExist(P_Operator.class)) {
            SQLiteDatabase sqLiteDatabase = LitePal.getDatabase();
            String path = "data.txt";
            executeAssetsSQL(sqLiteDatabase, path);

        }
        if (!DataSupport.isExist(P_Module.class)) {
            P_Module p_module = new P_Module();
            p_module.setTitle("仪器参数标定");
            p_module.setUrl("btnStdDrugDecetion");
            p_module.save();
            p_module.clearSavedState();
            p_module.setTitle("样品检测");
            p_module.setUrl("btnDrugDecetion");
            p_module.save();
            p_module.clearSavedState();
            p_module.setUrl("btnResultDetail");
            p_module.setTitle("检测数据查询");
            p_module.save();
            p_module.clearSavedState();
            p_module.setTitle("系统参数维护");
            p_module.setUrl("btnSystemSetUp");
            p_module.save();
        }
        return mBinder;
    }

    private void executeAssetsSQL(SQLiteDatabase db, String schemaName) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(getAssets()
                    .open(schemaName)));
            String line;
            String buffer = "";
            while ((line = in.readLine()) != null) {
                buffer += line;
                if (line.trim().endsWith(";")) {
                    db.execSQL(buffer.replace(";", ""));
                    buffer = "";
                }
            }
        } catch (IOException e) {
            Log.e("db-error", e.toString());
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                Log.e("db-error", e.toString());
            }
        }
    }

    public void saveCheckDate() {
        List<SystemConfig> systemConfigs = DataSupport.where("paramName = ?", "LastDetDate").find(SystemConfig.class);
        List<SystemConfig> lastDetNums = DataSupport.where("paramName = ?", "LastDetNum").find(SystemConfig.class);
        SystemConfig lastDetNum = lastDetNums.get(0);
        SystemConfig config = systemConfigs.get(0);
        String currentTime = dateFormat.format(new Date());
        int lastDate = Integer.parseInt(config.getParamValue());
        int currentDate = Integer.parseInt(currentTime);
        Log.d("zw", "lastdate" + lastDate + " currentDate" + currentDate + " ");
        if (currentDate > lastDate) {
            config.setParamValue(currentTime);
            config.saveOrUpdate("paramName = ?", config.getParamName());
            lastDetNum.setParamValue("1");
            lastDetNum.saveOrUpdate("paramName = ?", lastDetNum.getParamName());
        } else if (currentDate == lastDate) {
            String res = lastDetNum.getParamValue();
            int i = Integer.parseInt(res) + 1;
            Log.d("zw", "res " + res + "resl" + i);
            lastDetNum.setParamValue(String.valueOf(i));
            lastDetNum.saveOrUpdate("paramName = ?", lastDetNum.getParamName());

        }
    }

    public String getDevUuid() {
        List<DevUuid> devUuid = DataSupport.findAll(DevUuid.class);
        return devUuid.get(0).getUserAbbreviation();
    }

    public String getDetectionSnDate() {
        String result = "";
        List<SystemConfig> systemConfigs = DataSupport.where("paramName = ?", "LastDetDate").find(SystemConfig.class);
        List<SystemConfig> lastDetNums = DataSupport.where("paramName = ?", "LastDetNum").find(SystemConfig.class);
        SystemConfig lastDetNum = lastDetNums.get(0);
        SystemConfig config = systemConfigs.get(0);
        Log.d("zw", lastDetNum.getParamValue() + config.getParamValue());
        String currentTime = dateFormat.format(new Date());
        int lastDate = Integer.parseInt(config.getParamValue());
        int currentDate = Integer.parseInt(currentTime);
        if (currentDate > lastDate) {
            result = currentTime + "001";
        } else if (currentDate == lastDate) {
            int current = Integer.parseInt(lastDetNum.getParamValue()) + 1;
            if (current < 10) {
                result = currentTime + "00" + current;
            } else if (current > 10 && current < 100) {
                result = currentTime + "0" + current;
            } else {
                result = currentTime + current;
            }
        }
        return result;
    }


    /**
     * 模拟检测药品数据
     *
     * @param i
     * @param checkNum
     * @param isFirst
     * @param reportid
     * @param detectionSn
     * @throws JSONException
     */
    public synchronized void simulatieDate(int i, int checkNum, boolean isFirst, long reportid, String detectionSn) throws JSONException {
        DetectionDetail detectionDetail = new DetectionDetail();
        detectionDetail.setDetectionreport_id(reportid);
        detectionDetail.setPositive(i % 2 == 0 ? true : false);
        detectionDetail.setColorFactor(150);
        detectionDetail.setScrTime(4.0);
        detectionDetail.setStpTime(3.0);
        detectionDetail.setScrTimeText("正常");
        detectionDetail.setStpTimeText("正常");
        detectionDetail.setData1(0.1);
        detectionDetail.setData2(0.3);
        detectionDetail.setData3(0.3);
        detectionDetail.setData4(0.3);
        detectionDetail.setVideo("test.mp4");
        detectionDetail.setVideoMd5("sadasdqqw");
        detectionDetail.setValid(false);
        if (isFirst) {
            detectionDetail.setDetIndex(i + 1);
        } else {
            detectionDetail.setRepIndex(i + 1);
        }
        JSONObject jsonObject = new JSONObject();
        setNodeInfo(jsonObject, i);
        detectionDetail.setNodeInfo(jsonObject.toString());
        Log.d("zw", "out " + detectionDetail.getDetectionreport_id());
        detectionDetail.save();

        if (intent == null) {
            intent = new Intent();
        }
        intent.setAction("com.checkfinsh");
        if ((checkNum - 1) == i) {
            if (isFirst) {
                intent.putExtra("state", "finish");
            } else {
                intent.putExtra("state", "secondfinish");
            }
        } else {
            intent.putExtra("state", "process");
        }
        sendBroadcast(intent);
    }

    private void setNodeInfo(JSONObject jsonObject, int i) {
        JSONObject floatdata = new JSONObject();
        JSONObject glassprecent = new JSONObject();
        JSONObject glasstime = new JSONObject();
        JSONObject max = new JSONObject();
        JSONObject min = new JSONObject();
        JSONObject supers = new JSONObject();
        JSONObject statistics40 = new JSONObject();
        JSONObject statistics50 = new JSONObject();
        JSONObject statistics60 = new JSONObject();
        JSONObject statistics70 = new JSONObject();
        try {
            floatdata.put("name", "漂浮物检出次数(次)");
            floatdata.put("data", i);
            floatdata.put("result", "阴性");
            glassprecent.put("name", "速降物检出率(%)");
            glassprecent.put("data", 0);
            glassprecent.put("result", "阳性");
            glasstime.put("name", "速降物时间比例(%)");
            glasstime.put("data", 1.62);
            max.put("name", "50-70um异物检出数(颗)");
            max.put("data", 2);
            min.put("name", "40-50um异物检出数(颗)");
            min.put("data", 3);
            min.put("result", "阴性");
            supers.put("name", "70um以上异物检出数(颗)");
            supers.put("data", 3);
            supers.put("result", "阴性");
            statistics40.put("name", "40-50um异物检出率(%)");
            statistics40.put("data", 2);
            statistics40.put("result", "阴性");
            statistics50.put("name", "50-60um异物检出率(%)");
            statistics50.put("data", 2);
            statistics60.put("name", "60-70um异物检出率(%)");
            statistics60.put("data", 2);
            statistics70.put("name", "70um以上异物检出率(%)");
            statistics70.put("data", 2);
            jsonObject.put("floatdta", floatdata);
            jsonObject.put("glassprecent", glassprecent);
            jsonObject.put("glasstime", glasstime);
            jsonObject.put("max", max);
            jsonObject.put("min", min);
            jsonObject.put("super", supers);
            jsonObject.put("statistics40", statistics40);
            jsonObject.put("statistics50", statistics50);
            jsonObject.put("statistics60", statistics60);
            jsonObject.put("statistics70", statistics70);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addPermission(long sourceoperateid, long userTypeId) throws RemoteException {
        P_UserTypePermission p_userTypePermission = new P_UserTypePermission();
        p_userTypePermission.setP_sourceoperator_id(sourceoperateid);
        p_userTypePermission.setUsertype(userTypeId);
        p_userTypePermission.setRighttype(1);
        p_userTypePermission.saveOrUpdate("p_sourceoperator_id = ? and usertype = ?", sourceoperateid + "", userTypeId + "");
    }

    public void addAudittrail(int event_id, int info_id, String value, String mark) throws RemoteException {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setTime(audittraformat.format(new Date()));
        auditTrail.setUsername(user_id);
        auditTrail.setEvent_id(event_id);
        auditTrail.setInfo_id(info_id);
        auditTrail.setValue(value);
        auditTrail.setMark(mark);
        auditTrail.setUserauto_id(0);
        auditTrail.save();
    }
}
