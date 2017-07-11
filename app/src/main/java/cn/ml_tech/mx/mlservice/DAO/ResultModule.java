package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 创建时间: 2017/7/10
 * 创建人: zhongwang
 * 功能描述:
 */

public class ResultModule implements Parcelable, Serializable {
    double data;
    String result;
    public ResultModule(double data, String result) {
        this.data = data;
        this.result = result;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultModule{" +
                "data=" + data +
                ", result='" + result + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.data);
        dest.writeString(this.result);
    }

    protected ResultModule(Parcel in) {
        this.data = in.readDouble();
        this.result = in.readString();
    }

    public static final Parcelable.Creator<ResultModule> CREATOR = new Parcelable.Creator<ResultModule>() {
        @Override
        public ResultModule createFromParcel(Parcel source) {
            return new ResultModule(source);
        }

        @Override
        public ResultModule[] newArray(int size) {
            return new ResultModule[size];
        }
    };
}
