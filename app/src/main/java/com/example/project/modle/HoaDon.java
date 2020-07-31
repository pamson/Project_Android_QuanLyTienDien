package com.example.project.modle;

import java.io.Serializable;

public class HoaDon implements Serializable
{
    private String maHD, maKH, ngayPhaiThanhToan, ngayThanhToan,trangThai;
    private int chiSoCu, chiSoMoi, soKwh, soTienKwh,tongTien;

    public HoaDon() {
    }

    public HoaDon(String maHD, String maKH, String ngayPhaiThanhToan, String ngayThanhToan, int chiSoCu, int chiSoMoi, int soKwh, int soTienKwh, int tongTien, String trangThai) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.ngayPhaiThanhToan = ngayPhaiThanhToan;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThai = trangThai;
        this.chiSoCu = chiSoCu;
        this.chiSoMoi = chiSoMoi;
        this.soKwh = soKwh;
        this.soTienKwh = soTienKwh;
        this.tongTien = tongTien;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getNgayPhaiThanhToan() {
        return ngayPhaiThanhToan;
    }

    public void setNgayPhaiThanhToan(String ngayPhaiThanhToan) {
        this.ngayPhaiThanhToan = ngayPhaiThanhToan;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getChiSoCu() {
        return chiSoCu;
    }

    public void setChiSoCu(int chiSoCu) {
        this.chiSoCu = chiSoCu;
    }

    public int getChiSoMoi() {
        return chiSoMoi;
    }

    public void setChiSoMoi(int chiSoMoi) {
        this.chiSoMoi = chiSoMoi;
    }

    public int getSoKwh() {
        return soKwh;
    }

    public void setSoKwh(int soKwh) {
        this.soKwh = soKwh;
    }

    public int getSoTienKwh() {
        return soTienKwh;
    }

    public void setSoTienKwh(int soTienKwh) {
        this.soTienKwh = soTienKwh;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
