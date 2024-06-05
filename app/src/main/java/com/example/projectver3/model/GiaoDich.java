package com.example.projectver3.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GiaoDich implements Parcelable, Serializable{
    String maGiaoDich;
    String ngayGiaoDich;
    String soTien;

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    String taiKhoan;
    DanhMuc danhMuc;

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public GiaoDich() {
    }

    @Override
    public String toString() {
        return "GiaoDich{" +
                "maGiaoDich='" + maGiaoDich + '\'' +
                ", ngayGiaoDich='" + ngayGiaoDich + '\'' +
                ", soTien='" + soTien + '\'' +
                ", taiKhoan='" + taiKhoan + '\'' +
                ", danhMuc=" + danhMuc +
                '}';
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(String ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public GiaoDich(String maGiaoDich, String ngayGiaoDich, String soTien, DanhMuc danhMuc, String taiKhoan) {
        this.maGiaoDich = maGiaoDich;
        this.ngayGiaoDich = ngayGiaoDich;
        this.soTien = soTien;
        this.danhMuc = danhMuc;
        this.taiKhoan = taiKhoan;
    }

    protected GiaoDich(Parcel in) {
        // Đọc dữ liệu từ Parcel và khởi tạo đối tượng DanhMuc
        maGiaoDich = in.readString();
        ngayGiaoDich = in.readString();
        soTien = in.readString();
        danhMuc = DanhMuc.CREATOR.createFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Viết dữ liệu của đối tượng DanhMuc vào Parcel
        dest.writeString(maGiaoDich);
        dest.writeString(ngayGiaoDich);
        dest.writeString(soTien);
        danhMuc.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<GiaoDich> CREATOR = new Parcelable.Creator<GiaoDich>() {
        @Override
        public GiaoDich createFromParcel(Parcel in) {
            return new GiaoDich(in);
        }

        @Override
        public GiaoDich[] newArray(int size) {
            return new GiaoDich[size];
        }
    };

//    public LocalDate getFormattedDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        LocalDate date = LocalDate.parse(ngayGiaoDich, formatter);
//        return date; // Trả về ngày không định dạng nếu có lỗi
//    }
}
