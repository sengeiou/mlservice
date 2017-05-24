package cn.ml_tech.mx.mlservice.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


/**
 * Created by ml on 2017/5/15.
 */

public class User implements Parcelable {
    private String userId;
    private String userName;
    private String userPassword;
    private boolean isEnable;
    private UserType userType;

    private boolean isDeparecate;
    private Date loginDate;

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }



    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isDeparecate() {
        return isDeparecate;
    }

    public void setDeparecate(boolean deparecate) {
        isDeparecate = deparecate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userPassword);
        dest.writeByte(this.isEnable ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.userType, flags);
        dest.writeByte(this.isDeparecate ? (byte) 1 : (byte) 0);
        dest.writeLong(this.loginDate != null ? this.loginDate.getTime() : -1);
    }

    protected User(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.userPassword = in.readString();
        this.isEnable = in.readByte() != 0;
        this.userType = in.readParcelable(UserType.class.getClassLoader());
        this.isDeparecate = in.readByte() != 0;
        long tmpLoginDate = in.readLong();
        this.loginDate = tmpLoginDate == -1 ? null : new Date(tmpLoginDate);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
