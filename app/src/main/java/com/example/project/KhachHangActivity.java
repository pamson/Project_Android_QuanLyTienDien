package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        txtTuKhachHangVeTrangChu =(TextView)findViewById(R.id.txtTuKhachHangVeTrangChu);
        imgAddKhachHang = (ImageView)findViewById(R.id.imgAddKhachHang);

        lvKhachHang = (ListView)findViewById(R.id.lvKhachHang);
        dsKhachHang = new ArrayList<KhachHang>();
        khachHangAdapter = new KhachHangAdapter(KhachHangActivity.this, R.layout.khachhang, dsKhachHang);
        lvKhachHang.setAdapter(khachHangAdapter);

    }


}