package com.example.project.modle;

import java.io.Serializable;

public class UuDai implements Serializable
{
    private int id;
    private String hinhAnh, noiDung;

    public UuDai() {
    }

    public UuDai(int id, String hinhAnh, String noiDung) {
        this.id = id;
        this.hinhAnh = hinhAnh;
        this.noiDung = noiDung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
