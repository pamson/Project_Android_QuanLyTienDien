package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.adapter.KhachHangAdapter;
import com.example.project.modle.KhachHang;
import com.example.project.ultil.Check_Internet_Wifi;
import com.example.project.ultil.Loading;
import com.example.project.ultil.Server;
import com.example.project.ultil.TuongTacServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KhachHangActivity extends AppCompatActivity {

    private TextView txtTuKhachHangVeTrangChu;
    private ImageView imgAddKhachHang;

    private ArrayList<KhachHang> dsKhachHang;
    private ListView lvKhachHang;
    private KhachHangAdapter khachHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtTuKhachHangVeTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        HienThiDanhSachKhachHang();
        imgAddKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThemKhachHang();
            }
        });
    }


    private void xuLyThemKhachHang() {
        final SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date t = calendar.getTime();
        final Dialog dialog = new Dialog(KhachHangActivity.this);
        //không cho tắt hộp thoại đi khi click ra ngoài hộp thoại
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.themkhachhang);
        final EditText txtNhapMaKH = (EditText)dialog.findViewById(R.id.txtNhapMaKH);
        final EditText txtNhapTenKH = (EditText)dialog.findViewById(R.id.txtNhapTenKH);
        final EditText txtNhapNgaySinh = (EditText)dialog.findViewById(R.id.txtNhapNgaySinh);
        final EditText txtNhapEmail = (EditText)dialog.findViewById(R.id.txtNhapEmail);
        final EditText txtNhapDiaChi = (EditText)dialog.findViewById(R.id.txtNhapDiaChi);
        final EditText txtNhapSDT = (EditText)dialog.findViewById(R.id.txtNhapSDT);
        final EditText txtNhapMaThe = (EditText)dialog.findViewById(R.id.txtNhapMaThe);
        Button btnThemKH = (Button)dialog.findViewById(R.id.btnThemKH);

        txtNhapNgaySinh.setText(date.format(t));

        //Dữ liệu trong key
        final ArrayList<String> key = new ArrayList<String>();
        key.add("maKH");
        key.add("tenKH");
        key.add("ngaySinh");
        key.add("email");
        key.add("diaChi");
        key.add("sdt");
        key.add("maThe");

        btnThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading.loading(KhachHangActivity.this);
                String makh = txtNhapMaKH.getText().toString();
                String tenkh = txtNhapTenKH.getText().toString();
                String ngaysinh = txtNhapNgaySinh.getText().toString();
                String email = txtNhapEmail.getText().toString();
                String diachi =txtNhapDiaChi.getText().toString();
                String sdt =txtNhapSDT.getText().toString();
                String mathe =txtNhapMaThe.getText().toString();
                if(makh.isEmpty() || tenkh.isEmpty() || ngaysinh.isEmpty() || email.isEmpty() || diachi.isEmpty()||sdt.isEmpty()|mathe.isEmpty() )
                {
                    Check_Internet_Wifi.showToast_Short(KhachHangActivity.this, "Mời bạn nhập đủ thông tin");
                    Loading.destroyLoading();
                }
                else
                {
                    //Dữ liệu trong value
                    final ArrayList<String> value = new ArrayList<String>();
                    value.add(makh);
                    value.add(tenkh);
                    value.add(ngaysinh);
                    value.add(email);
                    value.add(diachi);
                    value.add(sdt);
                    value.add(mathe);
                    dsKhachHang.add(new KhachHang(makh,tenkh,ngaysinh,email,diachi,sdt,mathe));
                    khachHangAdapter.notifyDataSetChanged();
                    TuongTacServer.insert_Or_update(KhachHangActivity.this,Server.urlThemKhachHang,key, value);
                    dialog.cancel();
                }
            }
        });
        dialog.show();
    }


    private void HienThiDanhSachKhachHang() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Server.urlKhachHang, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i  =0; i < response.length(); i++)
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String maKH = jsonObject.getString("MaKH");
                                String tenKH = jsonObject.getString("TenKH");
                                String ngaySinh = jsonObject.getString("NgaySinh");
                                String email = jsonObject.getString("Email");
                                String diaChi = jsonObject.getString("DiaChi");
                                String sdt = jsonObject.getString("SDT");
                                String maThe = jsonObject.getString("MaThe");
                                dsKhachHang.add(new KhachHang(maKH,tenKH,ngaySinh,email,diaChi,sdt,maThe));
                                khachHangAdapter.notifyDataSetChanged();
                                Loading.destroyLoading();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LoiKH",error.toString());
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    private void addControls() {
        Loading.loading(KhachHangActivity.this);
        txtTuKhachHangVeTrangChu =(TextView)findViewById(R.id.txtTuKhachHangVeTrangChu);
        imgAddKhachHang = (ImageView)findViewById(R.id.imgAddKhachHang);

        lvKhachHang = (ListView)findViewById(R.id.lvKhachHang);
        dsKhachHang = new ArrayList<KhachHang>();
        khachHangAdapter = new KhachHangAdapter(KhachHangActivity.this, R.layout.khachhang, dsKhachHang);
        lvKhachHang.setAdapter(khachHangAdapter);

    }


}