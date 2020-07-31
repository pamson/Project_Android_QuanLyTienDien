package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.ultil.Check_Internet_Wifi;
import com.example.project.ultil.Server;
import com.example.project.ultil.TuongTacServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;

public class GiaDienActivity extends AppCompatActivity {

    private TextView txtTuGiaDienVeTrangChu,txtSoTien;
    private ImageView imgSuaSoTien;
    private int[] tienDien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gia_dien);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtTuGiaDienVeTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        HienThiGiaDien();
        imgSuaSoTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySuaGiaDien();
            }
        });
    }

    private void xuLySuaGiaDien() {
        final Dialog dialog = new Dialog(GiaDienActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.suagiadien);
        final EditText giaDien = (EditText)dialog.findViewById(R.id.txtNhapSotien);
        Button btnSuaGiaDien = (Button)dialog.findViewById(R.id.btnSuaGiaDien);
        //Dữ liệu key
        final ArrayList<String> key = new ArrayList<String>();
        key.add("oldSoTien");
        key.add("soTien");
        giaDien.setText(tienDien[0]+"");
        btnSuaGiaDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String > value = new ArrayList<String >();
                value.add(tienDien[0]+"");
                value.add(giaDien.getText().toString());
                TuongTacServer.insert_Or_update(GiaDienActivity.this, Server.urlSuaTrangThaiSoDien, key, value);
                HienThiGiaDien();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void HienThiGiaDien() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Server.urlTrangThaiSoDien, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject  jsonObject = response.getJSONObject(i);
                                int soTien = jsonObject.getInt("SoKwh");
                                txtSoTien.setText(soTien + " VND");
                                tienDien[0] = soTien;


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Loi_TTSD",error.toString());
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    private void addControls() {
        txtTuGiaDienVeTrangChu = (TextView)findViewById(R.id.txtTuGiaDienVeTrangChu);
        txtSoTien = (TextView)findViewById(R.id.txtSoTien);
        imgSuaSoTien = (ImageView)findViewById(R.id.imgSuaSoTien);

        tienDien = new int[1];

        Intent intent = getIntent();
        String role = intent.getStringExtra("role");
        if(role.equals("KH"))
            imgSuaSoTien.setVisibility(View.GONE);
        else
            imgSuaSoTien.setVisibility(View.VISIBLE);
    }
}