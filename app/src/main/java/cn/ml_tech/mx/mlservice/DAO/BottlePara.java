package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间: 2017/6/22
 * 创建人: zhongwang
 * 功能描述: 检测对象相关参数实体类
 */

public class BottlePara implements Parcelable {
    double MaxStatTime;
    double MaxStopTime;
    double StopDelayTime;
    double ImageDelayTime;
    double threshold40;
    double threshold50;
    double threshold60;
    double threshold70;

    public double getMaxStatTime() {
        return MaxStatTime;
    }

    public void setMaxStatTime(double maxStatTime) {
        MaxStatTime = maxStatTime;
    }

    public double getMaxStopTime() {
        return MaxStopTime;
    }

    public void setMaxStopTime(double maxStopTime) {
        MaxStopTime = maxStopTime;
    }

    public double getStopDelayTime() {
        return StopDelayTime;
    }

    public void setStopDelayTime(double stopDelayTime) {
        StopDelayTime = stopDelayTime;
    }

    public double getImageDelayTime() {
        return ImageDelayTime;
    }

    public void setImageDelayTime(double imageDelayTime) {
        ImageDelayTime = imageDelayTime;
    }

    public double getThreshold40() {
        return threshold40;
    }

    public void setThreshold40(double threshold40) {
        this.threshold40 = threshold40;
    }

    public double getThreshold50() {
        return threshold50;
    }

    public void setThreshold50(double threshold50) {
        this.threshold50 = threshold50;
    }

    public double getThreshold60() {
        return threshold60;
    }

    public void setThreshold60(double threshold60) {
        this.threshold60 = threshold60;
    }

    public double getThreshold70() {
        return threshold70;
    }

    public void setThreshold70(double threshold70) {
        this.threshold70 = threshold70;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.MaxStatTime);
        dest.writeDouble(this.MaxStopTime);
        dest.writeDouble(this.StopDelayTime);
        dest.writeDouble(this.ImageDelayTime);
        dest.writeDouble(this.threshold40);
        dest.writeDouble(this.threshold50);
        dest.writeDouble(this.threshold60);
        dest.writeDouble(this.threshold70);
    }

    public BottlePara() {
    }

    protected BottlePara(Parcel in) {
        this.MaxStatTime = in.readDouble();
        this.MaxStopTime = in.readDouble();
        this.StopDelayTime = in.readDouble();
        this.ImageDelayTime = in.readDouble();
        this.threshold40 = in.readDouble();
        this.threshold50 = in.readDouble();
        this.threshold60 = in.readDouble();
        this.threshold70 = in.readDouble();
    }

    public static final Creator<BottlePara> CREATOR = new Creator<BottlePara>() {
        @Override
        public BottlePara createFromParcel(Parcel source) {
            return new BottlePara(source);
        }

        @Override
        public BottlePara[] newArray(int size) {
            return new BottlePara[size];
        }
    };
}
