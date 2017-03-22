package cn.ml_tech.mx.mlservice;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.ml_tech.mx.mlservice.Bean.User;
import cn.ml_tech.mx.mlservice.DAO.AuditTrail;
import cn.ml_tech.mx.mlservice.DAO.AuditTrailEventType;
import cn.ml_tech.mx.mlservice.DAO.AuditTrailInfoType;
import cn.ml_tech.mx.mlservice.DAO.CameraParams;
import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DevParam;
import cn.ml_tech.mx.mlservice.DAO.DevUuid;
import cn.ml_tech.mx.mlservice.DAO.DrugContainer;
import cn.ml_tech.mx.mlservice.DAO.DrugInfo;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;
import cn.ml_tech.mx.mlservice.DAO.Factory;
import cn.ml_tech.mx.mlservice.DAO.SpecificationType;
import cn.ml_tech.mx.mlservice.DAO.SystemConfig;
import cn.ml_tech.mx.mlservice.DAO.Tray;
import cn.ml_tech.mx.mlservice.DAO.UserType;
import cn.ml_tech.mx.mlservice.Util.LogUtil;

import static android.content.ContentValues.TAG;
import static org.litepal.crud.DataSupport.find;
import static org.litepal.crud.DataSupport.findAll;
import static org.litepal.crud.DataSupport.where;


public class MotorServices extends Service {
    private List<DevParam> devParamList;
    AlertDialog alertDialog;
    private Random random;
    private Intent intent;
    private String user_id = "";
    private long userid;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private DetectionReport detectionReport;

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
        Log.v("MotorServices", message);
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

        /**
         * 设置检测对象相关参数
         * @param bottlepara
         * @throws RemoteException
         */
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
            user_id = users.get(0).getUserId();
            userid = users.get(0).getId();
            return !users.isEmpty();
        }

        /**
         * @param name
         * @param enName
         * @param pinYin
         * @param containterId
         * @param factoryId
         * @param id
         * @return
         * @throws RemoteException
         */
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
                drugInfo.setId(Long.parseLong(id));
                drugInfo.saveOrUpdate("id = ?", String.valueOf(drugInfo.getId()));
            }
            log("add Drug info....");
            return true;
        }

        @Override
        public boolean addFactory(String name, String address, String phone, String fax, String mail, String contactName, String contactPhone, String webSite, String province_code, String city_code, String area_code) throws RemoteException {
            Log.d("ZW", name + address + phone);
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
                Log.d("ZW", factory.getCity_code());
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


        /**
         * @return
         * @throws RemoteException
         */
        @Override
        public List<DrugControls> queryDrugControl() throws RemoteException {
            mDrugControls.clear();
            List<DrugInfo> mDrugInfo = DataSupport.findAll(DrugInfo.class);

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
        public List<cn.ml_tech.mx.mlservice.Bean.User> getUserList() throws RemoteException {
            List<User> list = new ArrayList<User>();
            Connector.getDatabase();
            List<cn.ml_tech.mx.mlservice.DAO.User> listDao = DataSupport.
                    select(new String[]{"id", "userid", "username", "usertype_id", "userenable"}).
                    where("isdeprecated=?", "0")
                    .find(cn.ml_tech.mx.mlservice.DAO.User.class);
            for (cn.ml_tech.mx.mlservice.DAO.User user : listDao) {
                User userBean = new User();
                if (user.getUserEnable() == 0)
                    userBean.setEnable(false);
                else
                    userBean.setEnable(true);
                userBean.setUserName(user.getUserName());
                userBean.setUserId(user.getUserId());
                userBean.setDeparecate(user.isDeprecated());
                cn.ml_tech.mx.mlservice.Bean.UserType userType = new cn.ml_tech.mx.mlservice.Bean.UserType((int) user.getUsertype_id(), "");
                userBean.setUserType(userType);
                list.add(userBean);
            }
            Log.d(TAG, "getUserList: " + String.valueOf(listDao.size()));
            Log.d(TAG, "getUserList: " + String.valueOf(list.size()));
            return list;
        }

        @Override
        public List<cn.ml_tech.mx.mlservice.SpecificationType> getSpecificationTypeList() throws RemoteException {
            List<cn.ml_tech.mx.mlservice.SpecificationType> typeList = new ArrayList<>();
            Connector.getDatabase();
            List<SpecificationType> types = findAll(SpecificationType.class);
            for (SpecificationType type : types) {
                cn.ml_tech.mx.mlservice.SpecificationType specificationType = new cn.ml_tech.mx.mlservice.SpecificationType();
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

        /**
         * @param list
         * @throws RemoteException
         */
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
        public String getTrayIcId() throws RemoteException {
            Random random = new Random();
            String string = String.valueOf(Math.abs(random.nextInt()));
            return string;
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
            auditTrails = findAll(AuditTrail.class);
            log(auditTrails.size() + "auditr");
            return auditTrails;
        }

        @Override
        public List<DrugControls> queryDrugControlByInfo(String drugname, String pinyin, String enname, int page) throws RemoteException {
            mDrugControls.clear();
            List<DrugInfo> drugInfos = new ArrayList<>();
            log("page" + page);
            Cursor c = null;
            c = DataSupport.findBySQL("select * from druginfo where name like ? and enname like ? and pinyin like ?"
                    , drugname + "%", enname + "%", pinyin + "%");
            if (page != -1) {
                c = DataSupport.findBySQL("select * from druginfo where name like ? and enname like ? and pinyin like ? limit 20 offset " + (page - 1) * 20
                        , drugname + "%", enname + "%", pinyin + "%");

            }

            while (c.moveToNext()) {
                DrugInfo drugInfo = new DrugInfo();
                drugInfo.setFactory_id(c.getLong(c.getColumnIndex("factory_id")));
                drugInfo.setDrugcontainer_id(c.getLong(c.getColumnIndex("drugcontainer_id")));
                drugInfo.setPinyin(c.getString(c.getColumnIndex("pinyin")));
                drugInfo.setEnname(c.getString(c.getColumnIndex("enname")));
                drugInfo.setName(c.getString(c.getColumnIndex("name")));
                drugInfo.setId(c.getLong(c.getColumnIndex("id")));
                drugInfos.add(drugInfo);
                log(drugInfo.toString());
            }
            log(drugInfos.size() + "size");
            for (int i = 0; i < drugInfos.size(); i++) {
                log(drugInfos.get(i).toString());
                List<DrugContainer> list = DataSupport.select(new String[]{"id", "name"}).where("id=?", String.valueOf(drugInfos.get(i).getDrugcontainer_id())).find(DrugContainer.class);
                String drugBottleType = list.get(0).getName();
                List<Factory> lists = DataSupport.select(new String[]{"*"}).where("id=?", String.valueOf(drugInfos.get(i).getFactory_id())).find(Factory.class);
                String factory_name = lists.get(0).getName();
                DrugControls drugControls = new DrugControls(drugInfos.get(i).getName(), drugBottleType, factory_name, drugInfos.get(i).getPinyin()
                        , drugInfos.get(i).getEnname(), drugInfos.get(i).getId());
                mDrugControls.add(drugControls);
            }
            log("query" + mDrugControls.size());
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
            Log.d("zw", "queryDetectionReport");
            List<DetectionReport> detectionReports = new ArrayList<>();
            detectionReports = DataSupport.where("drugName like ? and factoryName like ? and detectionSn like ? and detectionNumber like ? and detectionBatch like ?", drugInfo + "%", factoryName + "%", detectionSn + "%", detectionNumber + "%", detectionBatch + "%").find(DetectionReport.class);
//            Cursor c = null;
//            c = DataSupport.findBySQL("select * from DetectionReport where drugName like ? and factoryName like ? and detectionSn like ? " +
//                    "and detectionNumber like ? and detectionBatch like ?", drugInfo + "%", factoryName + "%", detectionSn + "%", detectionNumber + "%", detectionBatch + "%");
//            while (c.moveToNext()) {
//                DetectionReport detectionReport = new DetectionReport();
//                detectionReport.setId(c.getLong(c.getColumnIndex("id")));
//                detectionReport.setUser_id(c.getLong(c.getColumnIndex("user_id")));
//                detectionReport.setDruginfo_id(c.getLong(c.getColumnIndex("druginfo_id")));
//                detectionReport.setDetectionBatch(c.getString(c.getColumnIndex("detectionBatch")));
//                detectionReport.setDetectionNumber(c.getString(c.getColumnIndex("detectionNumber")));
//                detectionReport.setDetectionFirstCount(c.getInt(c.getColumnIndex("detectionFirstCount")));
//                detectionReport.setDetectionCount(c.getInt(c.getColumnIndex("detectionCount")));
//                detectionReport.setDetectionSecondCount(c.getInt(c.getColumnIndex("detectionSecondCount")));
//                detectionReport.setFactoryName(c.getString(c.getColumnIndex("factoryName")));
//                detectionReport.setDrugName(c.getString(c.getColumnIndex("drugName")));
//                detectionReport.setUserName(c.getString(c.getColumnIndex("userName")));
//
//                detectionReports.add(detectionReport);
//            }
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
                    detectionReport = new DetectionReport();
                    detectionReport.setDetectionSn(getDetectionSn());
                    detectionReport.setDetectionBatch(detectionBatch);
                    detectionReport.setDetectionNumber(detectionNumber);
                    detectionReport.setUser_id(userid);
                    detectionReport.setUserName(user_id);
                    detectionReport.setDate(new Date());
                    detectionReport.setDetectionCount(checkNum);
                    detectionReport.setDruginfo_id(drug_id);
                    detectionReport.setDrugName(DataSupport.find(DrugInfo.class, drug_id).getName());
                    detectionReport.setFactoryName(DataSupport.find(Factory.class, DataSupport.find(DrugInfo.class, drug_id).getFactory_id()).getName());

                } else {
                    detectionReport = DataSupport.findLast(DetectionReport.class);
                }
                new Thread() {
                    /**
                     *
                     */
                    @Override
                    public void run() {
                        super.run();
                        for (int i = 0; i < checkNum; i++) {
                            try {
                                Thread.sleep(1000);
                                simulatieDate(i, checkNum, isFirst, detectionReport, detectionSn);
                                if (isFirst) {
                                    detectionReport.setDetectionFirstCount(i + 1);
                                } else {
                                    detectionReport.setDetectionSecondCount(i + 1);
                                }
                                if ((i == 0) && (detectionSn.equals("")) && isFirst) {
                                    Log.d("zw", "saveCheckDate");
                                    saveCheckDate();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

            } else {
                List<DetectionReport> detectionReports = DataSupport.where("detectionSn = ?", detectionSn).find(DetectionReport.class);
                final DetectionReport detectionReport = detectionReports.get(0);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(500);
                            if (detectionReport.getDetectionSecondCount() == 0) {
                                for (int i = detectionReport.getDetectionFirstCount(); i < checkNum; i++) {
                                    simulatieDate(i, checkNum, isFirst, detectionReport, detectionSn);
                                }
                            } else {
                                for (int i = detectionReport.getDetectionSecondCount(); i < checkNum; i++) {
                                    simulatieDate(i, checkNum, isFirst, detectionReport, detectionSn);
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

        /**
         * @param id
         * @return
         * @throws RemoteException
         */
        @Override
        public List<DetectionDetail> queryDetectionDetailByReportId(long id) throws RemoteException {
            List<DetectionDetail> detectionDetails = new ArrayList<>();
            detectionDetails = DataSupport.where("detectionreport_id = ?", id + "").find(DetectionDetail.class);
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


    };

    @Override
    public IBinder onBind(Intent intent) {
        log("Received binding.");
        alertDialog = new AlertDialog();
        alertDialog.setContext(this);
        log(AlertDialog.getStringFromNative());
        Connector.getDatabase();

        if (!DataSupport.isExist(UserType.class)) {
            UserType userType = new UserType();
            userType.setType_id(0);
            userType.setName("超级管理员");
            userType.save();
            userType.clearSavedState();
            userType.setType_id(1);
            userType.setName("管理员");
            userType.save();
            userType.clearSavedState();
            userType.setType_id(2);
            userType.setName("操作员");
            userType.save();
        }
        if (!DataSupport.isExist(cn.ml_tech.mx.mlservice.DAO.User.class)) {
            cn.ml_tech.mx.mlservice.DAO.User user = new cn.ml_tech.mx.mlservice.DAO.User();
            user.setUsertype_id(1);
            user.setUserId("Admin");
            user.setUserEnable(1);
            user.setUserName("AdminName");
            user.setUserPassword("Admin");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setCreateDate(simpleDateFormat.format(new Date()));
            user.save();
        }
        //测试


        if (LogUtil.isApkInDebug(this)) {
            if (!DataSupport.isExist(CameraParams.class)) {
                CameraParams cameraParams = new CameraParams();
                cameraParams.setParamName("AGC");
                cameraParams.setParamValue(1);
                cameraParams.save();
            }
            if (!DataSupport.isExist(AuditTrailEventType.class)) {
                AuditTrailEventType eventType = new AuditTrailEventType();
                eventType.setName("add");
                eventType.save();
                eventType.clearSavedState();
                eventType.setName("update");
                eventType.save();
                eventType.clearSavedState();
                eventType.setName("delete");
                eventType.save();
                eventType.clearSavedState();
                eventType.setName("select");
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

            }
            if (!DataSupport.isExist(AuditTrailInfoType.class)) {
                AuditTrailInfoType infoType = new AuditTrailInfoType();
                infoType.setName("information");
                infoType.save();
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
                drugContainer.setTray_id(9);
                drugContainer.setSrctime(5.0);
                drugContainer.setStptime(3.0);
                drugContainer.setChannelvalue1(50);
                drugContainer.setChannelvalue2(2.5);
                drugContainer.setChannelvalue3(1.5);
                drugContainer.setChannelvalue4(2.7);
                drugContainer.setShadeparam(18.0);
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
        //测试数据

        return mBinder;
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
     * 模拟检测到药品的数据
     *
     * @param i
     * @param isFirst
     * @param detectionDetails
     * @param detectionReport
     * @throws JSONException
     */
    public synchronized void simulatieDate(int i, int checkNum, boolean isFirst, DetectionReport detectionReport, String detectionSn) throws JSONException {
        DetectionDetail detectionDetail = new DetectionDetail();
        detectionDetail.setPositive(true);
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
        detectionReport.save();
        detectionDetail.setDetectionreport_id(DataSupport.findLast(DetectionReport.class).getId());
        detectionDetail.save();
        Log.d("zw", DataSupport.findLast(DetectionReport.class).getId() + "id");
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
            glassprecent.put("result", "阴性");
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
            jsonObject.put("supers", supers);
            jsonObject.put("statistics40", statistics40);
            jsonObject.put("statistics50", statistics50);
            jsonObject.put("statistics60", statistics60);
            jsonObject.put("statistics70", statistics70);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
