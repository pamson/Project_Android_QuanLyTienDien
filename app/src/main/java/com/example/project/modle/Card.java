package com.example.project.modle;

import java.io.Serializable;

public class Card implements Serializable
{
    private String MaThe, MatKhau, TenChuThe;
    private int TongTien;

    public Card() {
    }

    public Card(String maThe, String matKhau, String tenChuThe, int tongTien) {
        MaThe = maThe;
        MatKhau = matKhau;
        TenChuThe = tenChuThe;
        TongTien = tongTien;
    }

    public String getMaThe() {
        return MaThe;
    }

    public void setMaThe(String maThe) {
        MaThe = maThe;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getTenChuThe() {
        return TenChuThe;
    }

    public void setTenChuThe(String tenChuThe) {
        TenChuThe = tenChuThe;
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }
}
