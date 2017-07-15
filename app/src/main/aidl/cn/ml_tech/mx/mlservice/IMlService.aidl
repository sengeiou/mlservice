    // IMlService.aidl
    package cn.ml_tech.mx.mlservice;
    import cn.ml_tech.mx.mlservice.MotorControl;
    import cn.ml_tech.mx.mlservice.DrugControls;
    import cn.ml_tech.mx.mlservice.FactoryControls;
    import cn.ml_tech.mx.mlservice.listener.IMlServiceChangeListener;
    import cn.ml_tech.mx.mlservice.DAO.DevParam;
    import cn.ml_tech.mx.mlservice.DAO.DevUuid;
    import cn.ml_tech.mx.mlservice.DAO.Tray;
    import cn.ml_tech.mx.mlservice.DAO.SystemConfig;
    import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
    import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
    import cn.ml_tech.mx.mlservice.BottlePara;
    import cn.ml_tech.mx.mlservice.SpecificationType;
    import cn.ml_tech.mx.mlservice.DAO.CameraParams;
    import cn.ml_tech.mx.mlservice.DAO.AuditTrail;
    import cn.ml_tech.mx.mlservice.DAO.AuditTrailEventType;
    import cn.ml_tech.mx.mlservice.DAO.AuditTrailInfoType;
    import cn.ml_tech.mx.mlservice.DAO.DrugContainer;
    import cn.ml_tech.mx.mlservice.DAO.DrugParam;
    import cn.ml_tech.mx.mlservice.DAO.DrugInfo;
    import cn.ml_tech.mx.mlservice.DAO.ResultModule;
    import cn.ml_tech.mx.mlservice.DAO.UserType;
    import cn.ml_tech.mx.mlservice.DAO.User;
    // Declare any non-default types here with import statements
    interface IMlService {
        /**
         * Demonstrates some basic types that you can use as parameters
         * and return values in AIDL.
         */
        void addMotorControl(in MotorControl mControl);
        boolean checkAuthority(String name, String password);
        boolean addDrugInfo(String name, String enName, String pinYin, int containterId, int factoryId,String id);
        boolean addFactory(String name, String address, String phone, String fax, String mail, String contactName, String contactPhone, String webSite, String province_code, String city_code, String area_code);
        List<DrugControls> queryDrugControl();
        List<FactoryControls> queryFactoryControl();
        List<User>getUserList();
        List<SpecificationType>getSpecificationTypeList();
        void saveBottlePara(in BottlePara bottlepara);
       List<DevParam>getDeviceParamList(in int type);
       void setDeviceParamList(in List<DevParam>list);
       void setDrugParamList(in List<DrugParam> list);
        double getDeviceParams(in String paramName,in int type);
        DevUuid getDeviceManagerInfo();
        boolean setDeviceManagerInfo(in DevUuid info);
        String getTrayIcId();
        List<Tray> getTrayList();
        Tray getTray(in int id);
        boolean setTray(in Tray tray);
        boolean delTray(in Tray tray);
        int setSystemConfig(in List<SystemConfig>list);
        int setCameraParam(in CameraParams config);
        List<SystemConfig>getSystemConfig();
        List<DetectionReport>getDetectionReportList(in int reportId);
        List<CameraParams>getCameraParams();
        List<AuditTrailInfoType>getAuditTrailInfoType();
        List<AuditTrailEventType>getAuditTrailEventType();
        List<AuditTrail>getAuditTrail(String starttime,String stoptime,String user,in int event_id,in int info_id);
        List<DrugControls> queryDrugControlByInfo(String drugname,String pinyin,String enname,in int page);
        void deleteDrugInfoById(in int id);
        List<DrugContainer>getDrugContainer();
        List<DrugParam> getDrugParamById(in int id);
        void Validate(in int drug_id,in int location);
        List<DetectionReport> queryDetectionReport(String detectionSn,String drugInfo,String factoryName,String detectionNumber,String detectionBatch,String startTime,String stopTime,in int page);
        void enterBottle();
        void bottleTest(int num);
        void leaveBottle();
        void deteleDetectionInfoById(long id);
        void deleteDrugParamById(in int id);
        void startCheck(in int drug_id,in int checkNum, in int rotateNum,String detectionNumber,String detectionBatch,boolean isFirst,String detectionSn);
        String getDetectionSn();
        DetectionDetail getLastDetail();
        List<DetectionDetail> queryDetectionDetailByReportId(long id);
        DetectionReport getLastReport();
        DrugControls queryDrugControlsById(long id);
        DevUuid getDevUuidInfo();
        List<DetectionReport> getAllDetectionReports();
        List<UserType> getAllUserType();
        void updateUser(in User user);
        UserType getUserTypeById(long id);
        void deleteUserById(in long id);
    }
