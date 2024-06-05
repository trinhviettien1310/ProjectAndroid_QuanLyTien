package com.example.projectver3.model;

import java.io.Serializable;

public class ThongKe implements Serializable {
    String tenGD, hinh, mau;
    Float tongTien;

    public String getTenGD() {
        return tenGD;
    }

    @Override
    public String toString() {
        return "ThongKe{" +
                "tenGD='" + tenGD + '\'' +
                ", hinh='" + hinh + '\'' +
                ", mau='" + mau + '\'' +
                ", tongTien=" + tongTien +
                '}';
    }

    public void setTenGD(String tenGD) {
        this.tenGD = tenGD;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public Float getTongTien() {
        return tongTien;
    }

    public void setTongTien(Float tongTien) {
        this.tongTien = tongTien;
    }

    public ThongKe(String tenGD, String hinh, String mau, Float tongTien) {
        this.tenGD = tenGD;
        this.hinh = hinh;
        this.mau = mau;
        this.tongTien = tongTien;
    }

    public ThongKe() {
    }
}
