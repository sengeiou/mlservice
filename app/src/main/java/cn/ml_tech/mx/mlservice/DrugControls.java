package cn.ml_tech.mx.mlservice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mx on 2017/3/23.
 */

public class DrugControls implements Parcelable {
    private String drugName;
    private String drugBottleType;
    private String drugFactory;
    private String pinyin;
    private String enname;

    public DrugControls(String drugName, String drugBottleType, String drugFactory, String pinyin, String enname) {
        this.drugName = drugName;
        this.drugBottleType = drugBottleType;
        this.drugFactory = drugFactory;
        this.pinyin = pinyin;
        this.enname = enname;
    }


    /**
     * Created by ml on 2017/4/28.
     */

    public static class User implements Parcelable {
        private int userLogicId;
        private String userName;
        private int userType;
        private boolean userEnable;

        public int getUserLogicId() {
            return userLogicId;
        }

        public void setUserLogicId(int userLogicId) {
            this.userLogicId = userLogicId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public boolean isUserEnable() {
            return userEnable;
        }

        public void setUserEnable(boolean userEnable) {
            this.userEnable = userEnable;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userLogicId);
            dest.writeString(this.userName);
            dest.writeInt(this.userType);
            dest.writeByte(this.userEnable ? (byte) 1 : (byte) 0);
        }

        public User() {
        }

        protected User(Parcel in) {
            this.userLogicId = in.readInt();
            this.userName = in.readString();
            this.userType = in.readInt();
            this.userEnable = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.drugName);
        dest.writeString(this.drugBottleType);
        dest.writeString(this.drugFactory);
        dest.writeString(this.pinyin);
        dest.writeString(this.enname);
    }

    public DrugControls() {
    }

    protected DrugControls(Parcel in) {
        this.drugName = in.readString();
        this.drugBottleType = in.readString();
        this.drugFactory = in.readString();
        this.pinyin = in.readString();
        this.enname = in.readString();
    }

    public static final Parcelable.Creator<DrugControls> CREATOR = new Parcelable.Creator<DrugControls>() {
        @Override
        public DrugControls createFromParcel(Parcel source) {
            return new DrugControls(source);
        }

        @Override
        public DrugControls[] newArray(int size) {
            return new DrugControls[size];
        }
    };
}

