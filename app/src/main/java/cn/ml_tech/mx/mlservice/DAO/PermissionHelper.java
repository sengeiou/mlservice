package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间: 2017/11/10
 * 创建人: Administrator
 * 功能描述:
 */

public class PermissionHelper implements Parcelable {
    private int userTypeId;
    private P_SourceOperator p_sourceOperator;
    private boolean canOperate;

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public P_SourceOperator getP_sourceOperator() {
        return p_sourceOperator;
    }

    public void setP_sourceOperator(P_SourceOperator p_sourceOperator) {
        this.p_sourceOperator = p_sourceOperator;
    }

    public boolean isCanOperate() {
        return canOperate;
    }

    public void setCanOperate(boolean canOperate) {
        this.canOperate = canOperate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userTypeId);
        dest.writeParcelable(this.p_sourceOperator, flags);
        dest.writeByte(this.canOperate ? (byte) 1 : (byte) 0);
    }

    public PermissionHelper() {
    }

    protected PermissionHelper(Parcel in) {
        this.userTypeId = in.readInt();
        this.p_sourceOperator = in.readParcelable(P_SourceOperator.class.getClassLoader());
        this.canOperate = in.readByte() != 0;
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
