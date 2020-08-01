package com.example.project.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.DangNhapTheActivity;
import com.example.project.R;
import com.example.project.TheActivity;
import com.example.project.modle.Card;
import com.example.project.modle.HoaDon;
import com.example.project.modle.User;
import com.example.project.ultil.Check_Internet_Wifi;
import com.example.project.ultil.Server;
import com.example.project.ultil.TuongTacServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        addEvents(hoaDon);
        return row;
    }

    private void addEvents(final HoaDon hoaDon) {
        imgShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThanhToan(hoaDon);
            }
        });
    }

    private void xuLyThanhToan(HoaDon hoaDon) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dangnhapthedethanhtoan);
        final EditText txtTenDNThe = (EditText)dialog.findViewById(R.id.txtTenDNThe);
        final EditText txtMKThe = (EditText)dialog.findViewById(R.id.txtMKThe);
        Button btnDNThe = (Button)dialog.findViewById(R.id.btnDNThe);
        //Dữ liệu trong key
        final ArrayList<String> key = new ArrayList<String>();
        key.add("mahd");
        key.add("mathe");
        //Dữ liệu trong value
        final ArrayList<String > value = new ArrayList<String>();
        value.add(hoaDon.getMaHD());

        btnDNThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtTenDNThe.getText().toString().trim();
                String pw = txtMKThe.getText().toString().trim();
                if(user.isEmpty() || pw.isEmpty())
                {
                    Check_Internet_Wifi.showToast_Short(context,"Mời bạn nhập đủ thông tin");
                }
                else
                {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlLoginCard,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("[]"))
                                    {
                                        Check_Internet_Wifi.showToast_Short(context, "Wrong password or user!!!");
                                    }
                                    else
                                    {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            for(int i =0; i <jsonArray.length(); i++)
                                            {
                                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                String mathe = jsonObject.getString("MaThe");
                                                value.add(mathe);
                                                TuongTacServer.insert_Or_update(context,Server.urlThanhToan,key,value);
                                                context.finish();
                                                dialog.cancel();
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            Log.e("loiJson", e.toString());
                                        }
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("ttt","Loi",error);
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("mathe",txtTenDNThe.getText().toString());
                            params.put("matkhau",txtMKThe.getText().toString());
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });

        dialog.show();
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
