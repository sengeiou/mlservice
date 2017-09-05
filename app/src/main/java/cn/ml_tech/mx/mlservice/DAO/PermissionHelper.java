package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 创建时间: 2017/7/19
 * 创建人: zhongwang
 * 功能描述:权限工具类 绑定Operate 和 SourceOperateId
 */

public class PermissionHelper implements Parcelable {

    LinkedHashMap<Long, P_Operator> p_operatorMap;

    public LinkedHashMap<Long, P_Operator> getP_operatorMap() {
        return p_operatorMap;
    }

    public void setP_operatorMap(LinkedHashMap<Long, P_Operator> p_operatorMap) {
        this.p_operatorMap = p_operatorMap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.p_operatorMap.size());
        for (Map.Entry<Long, P_Operator> entry : this.p_operatorMap.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    public PermissionHelper() {
    }

    protected PermissionHelper(Parcel in) {
        int p_operatorMapSize = in.readInt();
        this.p_operatorMap = new LinkedHashMap<Long, P_Operator>(p_operatorMapSize);
        for (int i = 0; i < p_operatorMapSize; i++) {
            Long key = (Long) in.readValue(Long.class.getClassLoader());
            P_Operator value = in.readParcelable(P_Operator.class.getClassLoader());
            this.p_operatorMap.put(key, value);
        }
    }

    public static final Parcelable.Creator<PermissionHelper> CREATOR = new Parcelable.Creator<PermissionHelper>() {
        @Override
        public PermissionHelper createFromParcel(Parcel source) {
            return new PermissionHelper(source);
        }

        @Override
        public PermissionHelper[] newArray(int size) {
            return new PermissionHelper[size];
        }
    };
}
