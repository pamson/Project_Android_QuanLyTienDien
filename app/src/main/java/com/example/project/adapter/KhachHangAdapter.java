package com.example.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.modle.KhachHang;

import java.util.List;

public class KhachHangAdapter extends ArrayAdapter<KhachHang>
{

    private Activity context;
    private int resource;
    private List<KhachHang> objects;

    TextView txtMaKH,txtTenKH,txtNgaySinh,txtEmail,txtDiaChi,txtSDT,txtMT;
    private ImageView imgUpdateKhachHang,imgDeleteKhachHang;
    public KhachHangAdapter(@NonNull Activity context, int resource, @NonNull List<KhachHang> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        KhachHang khachHang = this.objects.get(position);
        addControls(row);
        setUp(khachHang);
        return row;
    }

    private void setUp(KhachHang khachHang) {
        txtMaKH.setText(khachHang.getMaKH());
        txtTenKH.setText(khachHang.getTenKH());
        txtNgaySinh.setText(khachHang.getNgaySinh());
        txtEmail.setText(khachHang.getEmail());
        txtDiaChi.setText(khachHang.getDiaChi());
        txtSDT.setText(khachHang.getSdt());
        txtMT.setText(khachHang.getMaThe());

    }

    private void addControls(View row) {
        txtMaKH = (TextView)row.findViewById(R.id.txtMaKH);
        txtTenKH = (TextView)row.findViewById(R.id.txtTenKH);
        txtNgaySinh = (TextView)row.findViewById(R.id.txtNgaySinh);
        txtEmail = (TextView)row.findViewById(R.id.txtEmail);
        txtDiaChi = (TextView)row.findViewById(R.id.txtDiaChi);
        txtSDT = (TextView)row.findViewById(R.id.txtSDT);
        txtMT = (TextView)row.findViewById(R.id.txtMT);
        imgUpdateKhachHang = (ImageView) row.findViewById(R.id.imgUpdateKhachHang);
        imgDeleteKhachHang = (ImageView) row.findViewById(R.id.imgDeleteKhachHang);

    }
}
