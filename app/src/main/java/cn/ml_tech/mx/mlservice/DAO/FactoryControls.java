package cn.ml_tech.mx.mlservice.DAO;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * CREATE TABLE factory
 * (
 * id integer primary key AUTOINCREMENT not null,
 * name text not null unique,
 * address text not null,
 * phone text,
 * fax text,
 * mail text
 * contactName text
 * contactPhone text,
 * webSite text
 * , province_code TEXT default NULL, city_code TEXT default NULL, area_code TEXT default NULL, contactName text, contactPhone text);
 */
/*
*
*@author wl
*create at  2017/5/24 13:21
CREATE TABLE [factory](
    [id] integer PRIMARY KEY AUTOINCREMENT,
    [address] text NOT NULL,
    [area_code] text DEFAULT 'null',
    [city_code] text DEFAULT 'null',
    [contactname] text,
    [contactphone] text,
    [deprecate] integer NOT NULL DEFAULT false,
    [fax] text,
    [mail] text,
    [name] text NOT NULL UNIQUE,
    [phone] text,
    [province_code] text DEFAULT 'null',
    [website] text,
    [user_id] integer);
*/
public class FactoryControls implements Parcelable {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String fax;
    private String mail;
    private String contactName;
    private String contactPhone;
    private String webSite;
    private String province_code;
    private String city_code;
    private String area_code;
    private boolean deprecate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.fax);
        dest.writeString(this.mail);
        dest.writeString(this.contactName);
        dest.writeString(this.contactPhone);
        dest.writeString(this.webSite);
        dest.writeString(this.province_code);
        dest.writeString(this.city_code);
        dest.writeString(this.area_code);
        dest.writeByte(this.deprecate ? (byte) 1 : (byte) 0);
    }

    public FactoryControls() {
    }

    protected FactoryControls(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.fax = in.readString();
        this.mail = in.readString();
        this.contactName = in.readString();
        this.contactPhone = in.readString();
        this.webSite = in.readString();
        this.province_code = in.readString();
        this.city_code = in.readString();
        this.area_code = in.readString();
        this.deprecate = in.readByte() != 0;
    }

    public static final Parcelable.Creator<FactoryControls> CREATOR = new Parcelable.Creator<FactoryControls>() {
        @Override
        public FactoryControls createFromParcel(Parcel source) {
            return new FactoryControls(source);
        }

        @Override
        public FactoryControls[] newArray(int size) {
            return new FactoryControls[size];
        }
    };
}
