package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间: 2017/7/24
 * 创建人: zhongwang
 * 功能描述:
 */

public class Permission implements Parcelable {
    private Map<String, Boolean> permissiondata;

    public Map<String, Boolean> getPermissiondata() {
        return permissiondata;
    }

    public void setPermissiondata(Map<String, Boolean> permissiondata) {
        this.permissiondata = permissiondata;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.permissiondata.size());
        for (Map.Entry<String, Boolean> entry : this.permissiondata.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeValue(entry.getValue());
        }
    }

    public Permission() {
    }

    protected Permission(Parcel in) {
        int permissiondataSize = in.readInt();
        this.permissiondata = new HashMap<String, Boolean>(permissiondataSize);
        for (int i = 0; i < permissiondataSize; i++) {
            String key = in.readString();
            Boolean value = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.permissiondata.put(key, value);
        }
    }

    public static final Parcelable.Creator<Permission> CREATOR = new Parcelable.Creator<Permission>() {
        @Override
        public Permission createFromParcel(Parcel source) {
            return new Permission(source);
        }

        @Override
        public Permission[] newArray(int size) {
            return new Permission[size];
        }
    };
}
