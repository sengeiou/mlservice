package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE user
 * (
 * id integer  primary key AUTOINCREMENT not null,
 * userId TEXT not null unique,
 * userName TEXT not null ,
 * userPassword TEXT not null,
 * userPermission numeric not null,
 * userEnable numeric not null
 * <p>
 * );
 */
/*
*
*@author wl
*create at  2017/5/24 13:29
CREATE TABLE [user](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [createdate] text NOT NULL,
    [isdeprecated] integer NOT NULL DEFAULT false,
    [userenable] integer NOT NULL,
    [userid] text NOT NULL UNIQUE,
    [username] text NOT NULL,
    [userpassword] text NOT NULL,
    [usertype_id] integer);


*/

public class User extends DataSupport implements Parcelable {

    @Column(unique = true, nullable = false)
    private long id;
    @Column(unique = true, nullable = false)
    private String userId;
    @Column(nullable = false)
    private String userPassword;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private int userEnable;
    @Column(nullable = false)
    private long usertype_id;
    @Column(nullable = false, defaultValue = "false")
    private boolean isDeprecated;
    @Column(nullable = false)
    private String createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserEnable() {
        return userEnable;
    }

    public void setUserEnable(int userEnable) {
        this.userEnable = userEnable;
    }

    public long getUsertype_id() {
        return usertype_id;
    }

    public void setUsertype_id(long usertype_id) {
        this.usertype_id = usertype_id;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public void setDeprecated(boolean deprecated) {
        isDeprecated = deprecated;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.userPassword);
        dest.writeString(this.userName);
        dest.writeInt(this.userEnable);
        dest.writeLong(this.usertype_id);
        dest.writeByte(this.isDeprecated ? (byte) 1 : (byte) 0);
        dest.writeString(this.createDate);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.userId = in.readString();
        this.userPassword = in.readString();
        this.userName = in.readString();
        this.userEnable = in.readInt();
        this.usertype_id = in.readLong();
        this.isDeprecated = in.readByte() != 0;
        this.createDate = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
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