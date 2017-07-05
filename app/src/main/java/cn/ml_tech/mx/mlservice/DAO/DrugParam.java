package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

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
*create at  2017/5/24 13:15
CREATE TABLE [drugparam](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [paramname] text NOT NULL,
    [paramvalue] real NOT NULL,
    [type] integer NOT NULL DEFAULT 0,
    [druginfo_id] integer);


*/

public class DrugParam extends DataSupport implements Parcelable {
    @Override
    public String toString() {
        return "DrugParam{" +
                "id=" + id +
                ", paramname='" + paramname + '\'' +
                ", paramvalue=" + paramvalue +
                ", type=" + type +
                ", druginfo_id=" + druginfo_id +
                '}';
    }

    @Column(unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private String paramname;
    @Column(nullable = false)
    private double paramvalue;
    @Column(nullable = false,defaultValue = "0")
    private int type;
    @Column(nullable = false)
    private long druginfo_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public double getParamvalue() {
        return paramvalue;
    }

    public void setParamvalue(double paramvalue) {
        this.paramvalue = paramvalue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDruginfo_id() {
        return druginfo_id;
    }

    public void setDruginfo_id(long druginfo_id) {
        this.druginfo_id = druginfo_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.paramname);
        dest.writeDouble(this.paramvalue);
        dest.writeInt(this.type);
        dest.writeLong(this.druginfo_id);
    }

    public DrugParam() {
    }

    protected DrugParam(Parcel in) {
        this.id = in.readLong();
        this.paramname = in.readString();
        this.paramvalue = in.readDouble();
        this.type = in.readInt();
        this.druginfo_id = in.readLong();
    }

    public static final Parcelable.Creator<DrugParam> CREATOR = new Parcelable.Creator<DrugParam>() {
        @Override
        public DrugParam createFromParcel(Parcel source) {
            return new DrugParam(source);
        }

        @Override
        public DrugParam[] newArray(int size) {
            return new DrugParam[size];
        }
    };
}