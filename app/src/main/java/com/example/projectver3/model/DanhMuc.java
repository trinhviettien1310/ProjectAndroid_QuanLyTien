package com.example.projectver3.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DanhMuc implements Parcelable, Serializable {
    String tenDanhMuc, mau, hinh, ghiChu, mail;
    int maDanhMuc;

    boolean loai;




    @Override
    public String toString() {
        return "DanhMuc{" +
                "maDanhMuc='" + maDanhMuc + '\'' +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                ", mau='" + mau + '\'' +
                ", hinh='" + hinh + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", loai=" + loai +
                '}';
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public boolean isLoai() {
        return loai;
    }

    public void setLoai(boolean loai) {
        this.loai = loai;
    }

    public DanhMuc(String mail,int maDanhMuc, String tenDanhMuc, String mau, String hinh, String ghiChu, boolean loai) {
        this.tenDanhMuc = tenDanhMuc;
        this.mau = mau;
        this.hinh = hinh;
        this.ghiChu = ghiChu;
        this.mail = mail;
        this.maDanhMuc = maDanhMuc;
        this.loai = loai;
    }

    public DanhMuc() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    protected DanhMuc(Parcel in) {
        // Đọc dữ liệu từ Parcel và khởi tạo đối tượng DanhMuc
        maDanhMuc = in.readInt();
        tenDanhMuc = in.readString();
        mau = in.readString();
        hinh = in.readString();
        ghiChu = in.readString();
        loai = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Viết dữ liệu của đối tượng DanhMuc vào Parcel
        dest.writeInt(maDanhMuc);
        dest.writeString(tenDanhMuc);
        dest.writeString(mau);
        dest.writeString(hinh);
        dest.writeString(ghiChu);
        dest.writeByte((byte) (loai ? 1 : 0));
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DanhMuc> CREATOR = new Creator<DanhMuc>() {
        @Override
        public DanhMuc createFromParcel(Parcel in) {
            return new DanhMuc(in);
        }

        @Override
        public DanhMuc[] newArray(int size) {
            return new DanhMuc[size];
        }
    };
}
