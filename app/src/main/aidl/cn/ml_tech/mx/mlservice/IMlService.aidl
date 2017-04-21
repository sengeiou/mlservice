// IMlService.aidl
package cn.ml_tech.mx.mlservice;
import cn.ml_tech.mx.mlservice.MotorControl;
import cn.ml_tech.mx.mlservice.DrugControls;

// Declare any non-default types here with import statements

interface IMlService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void addMotorControl(in MotorControl mControl);
    boolean checkAuthority(String name, String password);
    boolean addDrugInfo(String name, String enName, String pinYin, int containterId, int factoryId);
    List<DrugControls> queryDrugControl();
}
