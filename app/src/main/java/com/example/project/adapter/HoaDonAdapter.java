package com.example.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.modle.HoaDon;

import java.util.List;

public class HoaDonAdapter extends ArrayAdapter<HoaDon>
{

    private Activity context;
    private int resource;
    private List<HoaDon> objects;
    private String role;

    private TextView txtMaHD, txtKH, txtNgayPhaiThanhToan, txtNgayThanhToan,txtChiSoCu,txtChiSoMoi, txtSoKwh,txtSoTienKwh,txtTongTien,txtTrangThai;
    private ImageView imgShopping;
    public HoaDonAdapter(@NonNull Activity context, int resource, @NonNull List<HoaDon> objects, String role) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.role = role;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);
        HoaDon hoaDon = this.objects.get(position);
        addControls(row);
        kiemTraTrangThai(hoaDon);
        setUp(hoaDon);
        return row;
    }

    private void setUp(HoaDon hoaDon) {
        txtMaHD.setText(hoaDon.getMaHD());
        txtKH.setText(hoaDon.getMaKH());
        txtNgayPhaiThanhToan.setText(hoaDon.getNgayPhaiThanhToan());
        txtNgayThanhToan.setText(hoaDon.getNgayThanhToan());
        txtChiSoCu.setText(hoaDon.getChiSoCu()+"");
        txtChiSoMoi.setText(hoaDon.getChiSoMoi()+"");
        txtSoKwh.setText(hoaDon.getSoKwh()+"");
        txtSoTienKwh.setText(hoaDon.getSoTienKwh()+"");
        txtTongTien.setText(hoaDon.getTongTien()+"");
        txtTrangThai.setText(hoaDon.getTrangThai());
    }

    private void kiemTraTrangThai(HoaDon hoaDon) {
        if(hoaDon.getTrangThai().equals("Đã đóng") || role.equals("QL"))
            imgShopping.setVisibility(View.GONE);
        else
            imgShopping.setVisibility(View.VISIBLE);
    }

    private void addControls(View row) {
        txtMaHD = ( TextView)row.findViewById(R.id.txtMaHD);
        txtKH = ( TextView)row.findViewById(R.id.txtKH);
        txtNgayPhaiThanhToan = ( TextView)row.findViewById(R.id.txtNgayPhaiThanhToan);
        txtNgayThanhToan = ( TextView)row.findViewById(R.id.txtNgayThanhToan);
        txtChiSoCu = ( TextView)row.findViewById(R.id.txtChiSoCu);
        txtChiSoMoi = ( TextView)row.findViewById(R.id.txtChiSoMoi);
        txtSoKwh = ( TextView)row.findViewById(R.id.txtSoKwh);
        txtSoTienKwh = ( TextView)row.findViewById(R.id.txtSoTienKwh);
        txtTongTien = ( TextView)row.findViewById(R.id.txtTongTien);
        txtTrangThai = ( TextView)row.findViewById(R.id.txtTrangThai);
        imgShopping = (ImageView)row.findViewById(R.id.imgShopping);
    }
}
