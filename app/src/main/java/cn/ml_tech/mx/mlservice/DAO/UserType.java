package cn.ml_tech.mx.mlservice.DAO;
import android.os.Parcel;
import android.os.Parcelable;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class UserType extends DataSupport implements Parcelable {

    @Column(nullable = false, unique = true)
    private long typeId;
    @Column(nullable = false, unique = true)
    private String typeName;

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.typeId);
        dest.writeString(this.typeName);
    }

    public UserType() {
    }

    protected UserType(Parcel in) {
        this.typeId = in.readLong();
        this.typeName = in.readString();
    }

    public static final Parcelable.Creator<UserType> CREATOR = new Parcelable.Creator<UserType>() {
        @Override
        public UserType createFromParcel(Parcel source) {
            return new UserType(source);
        }

        @Override
        public UserType[] newArray(int size) {
            return new UserType[size];
        }
    };
}