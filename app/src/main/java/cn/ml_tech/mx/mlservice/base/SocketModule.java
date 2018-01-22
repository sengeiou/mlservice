package cn.ml_tech.mx.mlservice.base;

/**
 * Created by zhongwang on 2018/1/22.
 */

public class SocketModule {
    private String operateType;
    private Object baseModule;

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Object getBaseModule() {
        return baseModule;
    }

    public void setBaseModule(Object baseModule) {
        this.baseModule = baseModule;
    }
}
