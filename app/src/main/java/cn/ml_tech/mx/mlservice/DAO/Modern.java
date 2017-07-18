package cn.ml_tech.mx.mlservice.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建时间: 2017/7/17
 * 创建人: zhongwang
 * 功能描述:传递Map集合
 */

public class Modern implements Parcelable {

    Map<Integer, List<String>> map;

    public Map<Integer, List<String>> getMap() {
        return map;
    }

    public void setMap(Map<Integer, List<String>> map) {
        this.map = map;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.map.size());
        for (Map.Entry<Integer, List<String>> entry : this.map.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeStringList(entry.getValue());
        }
    }

    public Modern() {
    }

    protected Modern(Parcel in) {
        int mapSize = in.readInt();
        this.map = new HashMap<Integer, List<String>>(mapSize);
        for (int i = 0; i < mapSize; i++) {
            Integer key = (Integer) in.readValue(Integer.class.getClassLoader());
            List<String> value = in.createStringArrayList();
            this.map.put(key, value);
        }
    }

    public static final Parcelable.Creator<Modern> CREATOR = new Parcelable.Creator<Modern>() {
        @Override
        public Modern createFromParcel(Parcel source) {
            return new Modern(source);
        }

        @Override
        public Modern[] newArray(int size) {
            return new Modern[size];
        }
    };
}
