// IMlService.aidl
package cn.ml_tech.mx.mlservice;
import cn.ml_tech.mx.mlservice.MotorControl;
import cn.ml_tech.mx.mlservice.DrugControls;
import cn.ml_tech.mx.mlservice.FactoryControls;
import cn.ml_tech.mx.mlservice.listener.IMlServiceChangeListener;
import cn.ml_tech.mx.mlservice.Bean.User;
import cn.ml_tech.mx.mlservice.Bean.UserType;
import cn.ml_tech.mx.mlservice.DAO.DevParam;
import cn.ml_tech.mx.mlservice.DAO.DevUuid;
import cn.ml_tech.mx.mlservice.DAO.Tray;
import cn.ml_tech.mx.mlservice.DAO.SystemConfig;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.BottlePara;
// Declare any non-default types here with import statements

interface IMlService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void addMotorControl(in MotorControl mControl);
    boolean checkAuthority(String name, String password);
    boolean addDrugInfo(String name, String enName, String pinYin, int containterId, int factoryId);
    boolean addFactory(String name, String address, String phone, String fax, String mail, String contactName, String contactPhone, String webSite, String province_code, String city_code, String area_code);
    List<DrugControls> queryDrugControl();
    List<FactoryControls> queryFactoryControl();
    List<User>getUserList();
    void saveBottlePara(in BottlePara bottlepara);
   List<DevParam>getDeviceParamList(in int type);
   void setDeviceParamList(in List<DevParam>list);
    double getDeviceParams(in String paramName,in int type);
    DevUuid getDeviceManagerInfo();
    boolean setDeviceManagerInfo(in DevUuid info);
    String getTrayIcId();
    List<Tray> getTrayList();
    Tray getTray(in int id);
    boolean setTray(in Tray tray);
    boolean delTray(in Tray tray);
    int setSystemConfig(in List<SystemConfig>list);
    List<SystemConfig>getSystemConfig();
    List<DetectionReport>getDetectionReportList(in int reportId);

}
