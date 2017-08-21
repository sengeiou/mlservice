package cn.ml_tech.mx.mlservice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间: 2017/6/27
 * 创建人: zhongwang
 * 功能描述:
 */

public class SpecificationType implements Parcelable {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    public SpecificationType() {
    }

    protected SpecificationType(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<SpecificationType> CREATOR = new Parcelable.Creator<SpecificationType>() {
        @Override
        public SpecificationType createFromParcel(Parcel source) {
            return new SpecificationType(source);
        }

        @Override
        public SpecificationType[] newArray(int size) {
            return new SpecificationType[size];
        }
    };
}
