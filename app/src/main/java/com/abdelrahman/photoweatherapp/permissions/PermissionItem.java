package com.abdelrahman.photoweatherapp.permissions;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 08-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.permissions
 */
public class PermissionItem implements Parcelable {
    public static final Parcelable.Creator<PermissionItem> CREATOR = new Parcelable.Creator<PermissionItem>() {
        @Override
        public PermissionItem createFromParcel(Parcel in) {
            return new PermissionItem(in);
        }

        @Override
        public PermissionItem[] newArray(int size) {
            return new PermissionItem[size];
        }
    };
    private final String name;
    @StringRes
    private final int rationaleTitleRes;
    @StringRes
    private final int rationaleRes;

    public PermissionItem(String name, int rationaleTitleRes, int rationaleRes) {
        this.name = name;
        this.rationaleTitleRes = rationaleTitleRes;
        this.rationaleRes = rationaleRes;
    }

    protected PermissionItem(Parcel in) {
        name = in.readString();
        rationaleTitleRes = in.readInt();
        rationaleRes = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(rationaleTitleRes);
        dest.writeInt(rationaleRes);
    }

    public String getName() {
        return name;
    }

    public int getRationaleTitleRes() {
        return rationaleTitleRes;
    }

    public int getRationaleRes() {
        return rationaleRes;
    }
}
