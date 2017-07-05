package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * CREATE TABLE "druginfo" (
 * id integer primary key AUTOINCREMENT not null,
 * name text not null ,
 * enName text not null,
 * pinYin text not null,
 * containerId integer not null,
 * factoryId integer not null,
 * foreign key(containerId) REFERENCES drugContainer(id),
 * foreign key (factoryId) REFERENCES factory(id)
 * );

 */
/*
*
*@author wl
*create at  2017/5/24 13:12
CREATE TABLE [druginfo](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [createdate] integer NOT NULL,
    [deprecate] integer NOT NULL,
    [enname] text NOT NULL,
    [name] text NOT NULL,
    [pinyin] text NOT NULL,
    [user_id] integer,
    [drugcontainer_id] integer,
    [factory_id] integer);


*/

public class DrugInfo extends DataSupport implements Parcelable {

    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private Date createdate;
    @Column(nullable = false, defaultValue = "false")
    private boolean deprecate;
    @Column(nullable = false)
    private String enname;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String pinyin;
    @Column(nullable = false)
    private long user_id;
    @Column(nullable = false)
    private long drugcontainer_id;
    @Column(nullable = false)
    private long factory_id;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getDrugcontainer_id() {
        return drugcontainer_id;
    }

    public void setDrugcontainer_id(long drugcontainer_id) {
        this.drugcontainer_id = drugcontainer_id;
    }

    public long getFactory_id() {
        return factory_id;
    }

    public void setFactory_id(long factory_id) {
        this.factory_id = factory_id;
    }

    @Override
    public String toString() {
        return "DrugInfo{" +
                "id=" + id +
                ", createdate=" + createdate +
                ", deprecate=" + deprecate +
                ", enname='" + enname + '\'' +
                ", name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", user_id=" + user_id +
                ", drugcontainer_id=" + drugcontainer_id +
                ", factory_id=" + factory_id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.createdate != null ? this.createdate.getTime() : -1);
        dest.writeByte(this.deprecate ? (byte) 1 : (byte) 0);
        dest.writeString(this.enname);
        dest.writeString(this.name);
        dest.writeString(this.pinyin);
        dest.writeLong(this.user_id);
        dest.writeLong(this.drugcontainer_id);
        dest.writeLong(this.factory_id);
    }

    public DrugInfo() {
    }

    protected DrugInfo(Parcel in) {
        this.id = in.readLong();
        long tmpCreatedate = in.readLong();
        this.createdate = tmpCreatedate == -1 ? null : new Date(tmpCreatedate);
        this.deprecate = in.readByte() != 0;
        this.enname = in.readString();
        this.name = in.readString();
        this.pinyin = in.readString();
        this.user_id = in.readLong();
        this.drugcontainer_id = in.readLong();
        this.factory_id = in.readLong();
    }

    public static final Parcelable.Creator<DrugInfo> CREATOR = new Parcelable.Creator<DrugInfo>() {
        @Override
        public DrugInfo createFromParcel(Parcel source) {
            return new DrugInfo(source);
        }

        @Override
        public DrugInfo[] newArray(int size) {
            return new DrugInfo[size];
        }
    };
}