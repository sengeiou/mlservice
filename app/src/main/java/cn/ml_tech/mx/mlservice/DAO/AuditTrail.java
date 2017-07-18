package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * CREATE TABLE [audittrail](
 * [id] integer PRIMARY KEY AUTOINCREMENT,
 * [event] integer NOT NULL,
 * [info] integer NOT NULL,
 * [mark] text NOT NULL,
 * [time] text NOT NULL,
 * [userautoid] integer,
 * [userlogicid] text ,
 * [audittraileventtype_id] integer,
 * [audittrailinfotype_id] integer);
 */

public class AuditTrail extends DataSupport implements Parcelable {
    @Override
    public String toString() {
        return "AuditTrail{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", info_id=" + info_id +
                ", mark='" + mark + '\'' +
                ", time='" + time + '\'' +
                ", userauto_id=" + userauto_id +
                ", username='" + username + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Column(nullable = false, unique = true)
    private long id;
    @Column(nullable = false)
    private int event_id;
    @Column(nullable = false)
    private int info_id;
    @Column(nullable = false)
    private String mark;
    @Column(nullable = false)
    private String time;
    private int userauto_id;
    private String username;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserauto_id() {
        return userauto_id;
    }

    public void setUserauto_id(int userauto_id) {
        this.userauto_id = userauto_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuditTrail() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.event_id);
        dest.writeInt(this.info_id);
        dest.writeString(this.mark);
        dest.writeString(this.time);
        dest.writeInt(this.userauto_id);
        dest.writeString(this.username);
        dest.writeString(this.value);
    }

    protected AuditTrail(Parcel in) {
        this.id = in.readLong();
        this.event_id = in.readInt();
        this.info_id = in.readInt();
        this.mark = in.readString();
        this.time = in.readString();
        this.userauto_id = in.readInt();
        this.username = in.readString();
        this.value = in.readString();
    }

    public static final Creator<AuditTrail> CREATOR = new Creator<AuditTrail>() {
        @Override
        public AuditTrail createFromParcel(Parcel source) {
            return new AuditTrail(source);
        }

        @Override
        public AuditTrail[] newArray(int size) {
            return new AuditTrail[size];
        }
    };
}
