package com.yzune.ipcdemo;

import android.os.Parcel;
import android.os.Parcelable;

public class JavaUser implements Parcelable {

    public int userId;
    public String userName;
    public boolean isMale;

    public Book book;

    public JavaUser(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    private JavaUser(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
        book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    public static final Creator<JavaUser> CREATOR = new Creator<JavaUser>() {
        @Override
        public JavaUser createFromParcel(Parcel in) {
            return new JavaUser(in);
        }

        @Override
        public JavaUser[] newArray(int size) {
            return new JavaUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(userName);
        parcel.writeInt(isMale ? 1 : 0);
        parcel.writeParcelable(book, 0);
    }
}
