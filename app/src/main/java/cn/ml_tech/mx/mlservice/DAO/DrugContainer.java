package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE drugContainer
 * (
 * id integer primary key AUTOINCREMENT   not null,
 * name text not null unique,
 * type  INTEGER not null,
 * specification INTEGER not null,
 * diameter REAL not null,
 * height REAL,
 * trayID INTEGER not null,
 * srcTime REAL not null,
 * stpTime REAL not null,
 * channelValue1 REAL not null,
 * channelValue2 REAL not null,
 * channelValue3 REAL not null,
 * channelValue4 REAL not null,
 * shadeParam REAL not null,
 * rotateSpeed INTEGER NOT NULL DEFAULT 4500,
 * sendParam REAL not null,
 * foreign key (specification) REFERENCES specificationType(id),
 * foreign key (trayID) REFERENCES tray(id)
 * );
 */
/*
*
*@author wl
*create at  2017/5/24 13:07
CREATE TABLE [drugcontainer](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [channelvalue1] real NOT NULL,
    [channelvalue2] real NOT NULL,
    [channelvalue3] real NOT NULL,
    [channelvalue4] real NOT NULL,
    [diameter] real NOT NULL,
    [height] real NOT NULL,
    [name] text NOT NULL UNIQUE,
    [rotatespeed] integer NOT NULL DEFAULT 4500,
    [sendparam] real NOT NULL,
    [shadeparam] real NOT NULL,
    [srctime] real NOT NULL,
    [stptime] real NOT NULL,
    [containertype_id] integer,
    [specificationtype_id] integer,
    [tray_id] integer);


*/
public class DrugContainer extends DataSupport implements Parcelable {

    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private double channelvalue1;//通道阈值
    @Column(nullable = false)
    private double channelvalue2;
    @Column(nullable = false)
    private double channelvalue3;
    @Column(nullable = false)
    private double channelvalue4;
    @Column(nullable = false)
    private double diameter;//直径
    private double height;//高
    @Column(nullable = false)
    private String name;//名字
    @Column(nullable = false)
    private int rotatespeed;//旋转速度
    @Column(nullable = false)
    private double sendparam;
    @Column(nullable = false)
    private double shadeparam;//遮光参数默认
    @Column(nullable = false)
    private double srctime;//最大旋瓶时间
    @Column(nullable = false)
    private double stptime;//最大停瓶时间
    @Column(nullable = false)
    private long containertype_id;//类型
    @Column(nullable = false)
    private long specificationtype_id;//规格id
    @Column(nullable = false)
    private long tray_id;//托盘id
    @Column(nullable = false)
    private double delaytime;//规格id

    public double getDelaytime() {
        return delaytime;
    }

    public void setDelaytime(double delaytime) {
        this.delaytime = delaytime;
    }

    public double getImagetime() {
        return imagetime;
    }

    public void setImagetime(double imagetime) {
        this.imagetime = imagetime;
    }

    @Column(nullable = false)
    private double imagetime;//托盘id

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getChannelvalue1() {
        return channelvalue1;
    }

    public void setChannelvalue1(double channelvalue1) {
        this.channelvalue1 = channelvalue1;
    }

    public double getChannelvalue2() {
        return channelvalue2;
    }

    public void setChannelvalue2(double channelvalue2) {
        this.channelvalue2 = channelvalue2;
    }

    public double getChannelvalue3() {
        return channelvalue3;
    }

    public void setChannelvalue3(double channelvalue3) {
        this.channelvalue3 = channelvalue3;
    }

    public double getChannelvalue4() {
        return channelvalue4;
    }

    public void setChannelvalue4(double channelvalue4) {
        this.channelvalue4 = channelvalue4;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRotatespeed() {
        return rotatespeed;
    }

    public void setRotatespeed(int rotatespeed) {
        this.rotatespeed = rotatespeed;
    }

    public double getSendparam() {
        return sendparam;
    }

    public void setSendparam(double sendparam) {
        this.sendparam = sendparam;
    }

    public double getShadeparam() {
        return shadeparam;
    }

    public void setShadeparam(double shadeparam) {
        this.shadeparam = shadeparam;
    }

    public double getSrctime() {
        return srctime;
    }

    public void setSrctime(double srctime) {
        this.srctime = srctime;
    }

    public double getStptime() {
        return stptime;
    }

    public void setStptime(double stptime) {
        this.stptime = stptime;
    }

    public long getContainertype_id() {
        return containertype_id;
    }

    public void setContainertype_id(long containertype_id) {
        this.containertype_id = containertype_id;
    }

    public long getSpecificationtype_id() {
        return specificationtype_id;
    }

    public void setSpecificationtype_id(long specificationtype_id) {
        this.specificationtype_id = specificationtype_id;
    }

    public long getTray_id() {
        return tray_id;
    }

    public void setTray_id(long tray_id) {
        this.tray_id = tray_id;
    }

    public DrugContainer() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeDouble(this.channelvalue1);
        dest.writeDouble(this.channelvalue2);
        dest.writeDouble(this.channelvalue3);
        dest.writeDouble(this.channelvalue4);
        dest.writeDouble(this.diameter);
        dest.writeDouble(this.height);
        dest.writeString(this.name);
        dest.writeInt(this.rotatespeed);
        dest.writeDouble(this.sendparam);
        dest.writeDouble(this.shadeparam);
        dest.writeDouble(this.srctime);
        dest.writeDouble(this.stptime);
        dest.writeLong(this.containertype_id);
        dest.writeLong(this.specificationtype_id);
        dest.writeLong(this.tray_id);
        dest.writeDouble(this.delaytime);
        dest.writeDouble(this.imagetime);
    }

    protected DrugContainer(Parcel in) {
        this.id = in.readLong();
        this.channelvalue1 = in.readDouble();
        this.channelvalue2 = in.readDouble();
        this.channelvalue3 = in.readDouble();
        this.channelvalue4 = in.readDouble();
        this.diameter = in.readDouble();
        this.height = in.readDouble();
        this.name = in.readString();
        this.rotatespeed = in.readInt();
        this.sendparam = in.readDouble();
        this.shadeparam = in.readDouble();
        this.srctime = in.readDouble();
        this.stptime = in.readDouble();
        this.containertype_id = in.readLong();
        this.specificationtype_id = in.readLong();
        this.tray_id = in.readLong();
        this.delaytime = in.readDouble();
        this.imagetime = in.readDouble();
    }

    public static final Creator<DrugContainer> CREATOR = new Creator<DrugContainer>() {
        @Override
        public DrugContainer createFromParcel(Parcel source) {
            return new DrugContainer(source);
        }

        @Override
        public DrugContainer[] newArray(int size) {
            return new DrugContainer[size];
        }
    };
}