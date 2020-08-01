package com.example.project.modle;

public class QuangCao
{
    private String id, hinhAnh, noiDung;

    public QuangCao() {
    }

    public QuangCao(String id, String hinhAnh, String noiDung) {
        this.id = id;
        this.hinhAnh = hinhAnh;
        this.noiDung = noiDung;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
