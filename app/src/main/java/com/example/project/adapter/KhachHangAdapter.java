package com.example.project.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.KhachHangActivity;
import com.example.project.R;
import com.example.project.modle.KhachHang;
import com.example.project.ultil.Check_Internet_Wifi;
import com.example.project.ultil.Loading;
import com.example.project.ultil.Server;
import com.example.project.ultil.TuongTacServer;

import java.util.ArrayList;
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
        addEvents(khachHang);
        return row;
    }

    private void addEvents(final KhachHang khachHang) {
        imgDeleteKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading.loading(context);
                String keyMaKH = "maKH";
                String valueMaKH = khachHang.getMaKH();
                TuongTacServer.delete(context, Server.urlXoaKhachHang,keyMaKH,valueMaKH);
                objects.remove(khachHang);
                notifyDataSetChanged();
            }
        });
        imgUpdateKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySuaKH(khachHang);
            }
        });
    }

    private void xuLySuaKH(final KhachHang khachHang) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.suakhachhang);
        final EditText txtNhapMaKH = (EditText)dialog.findViewById(R.id.txtNhapMaKH);
        final EditText txtNhapTenKH = (EditText)dialog.findViewById(R.id.txtNhapTenKH);
        final EditText txtNhapNgaySinh = (EditText)dialog.findViewById(R.id.txtNhapNgaySinh);
        final EditText txtNhapEmail = (EditText)dialog.findViewById(R.id.txtNhapEmail);
        final EditText txtNhapDiaChi = (EditText)dialog.findViewById(R.id.txtNhapDiaChi);
        final EditText txtNhapSDT = (EditText)dialog.findViewById(R.id.txtNhapSDT);
        final EditText txtNhapMaThe = (EditText)dialog.findViewById(R.id.txtNhapMaThe);
        Button btnSuaKH = (Button)dialog.findViewById(R.id.btnSuaKH);
        //Dữ liệu trong key
        final ArrayList<String> key = new ArrayList<String>();
        key.add("oldMaKH");
        key.add("maKH");
        key.add("tenKH");
        key.add("ngaySinh");
        key.add("email");
        key.add("diaChi");
        key.add("sdt");
        key.add("maThe");

        //Gán dữ liệu lên edittext
        txtNhapMaKH.setText(khachHang.getMaKH());
        txtNhapTenKH.setText(khachHang.getTenKH());
        txtNhapNgaySinh.setText(khachHang.getNgaySinh());
        txtNhapEmail.setText(khachHang.getEmail());
        txtNhapDiaChi.setText(khachHang.getDiaChi());
        txtNhapSDT.setText(khachHang.getSdt());
        txtNhapMaThe.setText(khachHang.getMaThe());

        //
        btnSuaKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading.loading(context);
                //Dữ liệu trong value
                final ArrayList<String> value = new ArrayList<String>();
                value.add(khachHang.getMaKH());
                value.add(txtNhapMaKH.getText().toString());
                value.add(txtNhapTenKH.getText().toString());
                value.add(txtNhapNgaySinh.getText().toString());
                value.add(txtNhapEmail.getText().toString());
                value.add(txtNhapDiaChi.getText().toString());
                value.add(txtNhapSDT.getText().toString());
                value.add(txtNhapMaThe.getText().toString());
                //Load lại dữ liệu
                khachHang.setMaKH(txtNhapMaKH.getText().toString());
                khachHang.setTenKH(txtNhapTenKH.getText().toString());
                khachHang.setNgaySinh(txtNhapNgaySinh.getText().toString());
                khachHang.setEmail(txtNhapEmail.getText().toString());
                khachHang.setDiaChi(txtNhapDiaChi.getText().toString());
                khachHang.setSdt(txtNhapSDT.getText().toString());
                khachHang.setMaThe(txtNhapMaThe.getText().toString());
                //
                TuongTacServer.insert_Or_update(context,Server.urlSuaKhachHang,key,value);
                notifyDataSetChanged();
                dialog.cancel();
            }
        });
        dialog.show();
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
