package cn.ml_tech.mx.mlservice;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by mx on 2017/3/23.
 */

public   class DrugControls implements Parcelable {

    private String drugName;
    private String drugBottleType;
    private String drugFactory;
    public DrugControls(String name, String type, String factoryName) {
        drugName = name;
        drugBottleType = type;
        drugFactory = factoryName;
    }

    protected DrugControls(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<DrugControls> CREATOR = new Creator<DrugControls>() {
        @Override
        public DrugControls createFromParcel(Parcel in) {
            return new DrugControls(in);
        }
        @Override
        public DrugControls[] newArray(int size) {
            return new DrugControls[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(drugName);
        dest.writeString(drugBottleType);
        dest.writeString(drugFactory);
    }
    public void readFromParcel(Parcel in) {
        drugName = in.readString();
        drugBottleType = in.readString();
        drugFactory = in.readString();
    }
    public String toString() {
        return String.format(Locale.ENGLISH, "DrugControl[ %s, %s, %s]", drugName, drugBottleType, drugFactory);
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugBottleType() {
        return drugBottleType;
    }

    public void setDrugBottleType(String drugBottleType) {
        this.drugBottleType = drugBottleType;
    }

    public String getDrugFactory() {
        return drugFactory;
    }

    public void setDrugFactory(String drugFactory) {
        this.drugFactory = drugFactory;
    }

    /**
     * Created by ml on 2017/4/28.
     */

    public static class User implements Parcelable {
        private int userLogicId;
        private String userName;
        private int userType;
        private  boolean userEnable;

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
}

