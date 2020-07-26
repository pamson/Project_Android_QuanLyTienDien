package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.modle.Card;
import com.example.project.ultil.Check_Internet_Wifi;
import com.example.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangNhapTheActivity extends AppCompatActivity {

    private EditText txtTenDangNhapThe, txtMatKhauThe;
    private Button btnDangNhapThe;
    private CheckBox ckh_eyeThe;
    private String saveCard = "luuCard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap_the);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtTenDangNhapThe = (EditText)findViewById(R.id.txtTenDangNhapThe);
        txtMatKhauThe = (EditText)findViewById(R.id.txtMatKhauThe);
        btnDangNhapThe = (Button)findViewById(R.id.btnDangNhapThe);
        ckh_eyeThe = (CheckBox)findViewById(R.id.ckh_eyeThe);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences(saveCard, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName",txtTenDangNhapThe.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(saveCard,MODE_PRIVATE);
        String user = preferences.getString("userName","");
        String pass = preferences.getString("passWord","");
        txtTenDangNhapThe.setText(user);
    }

    private void addEvents() {
        ckh_eyeThe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtMatKhauThe.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                    txtMatKhauThe.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        btnDangNhapThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Check_Internet_Wifi.haveNetworkConnection(DangNhapTheActivity.this))
                {
                    String userName = txtMatKhauThe.getText().toString().trim();
                    String pass = txtMatKhauThe.getText().toString().trim();
                    if(userName.isEmpty() || pass.isEmpty())
                    {
                        Check_Internet_Wifi.showToast_Short(DangNhapTheActivity.this,"Mời bạn nhập đủ thông tin");
                    }
                    else
                    {
                        xuLyDangNhapThe();
                    }
                }
                else
                {
                    Check_Internet_Wifi.showToast_Short(DangNhapTheActivity.this,"No Wifi or Internet");
                    finish();
                }

            }
        });
    }

    private void xuLyDangNhapThe() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlLoginCard,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("[]"))
                        {
                            Check_Internet_Wifi.showToast_Short(DangNhapTheActivity.this, "Wrong password or user!!!");
                        }
                        else
                        {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i =0; i <jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Intent intent = new Intent(DangNhapTheActivity.this, TheActivity.class);
                                    intent.putExtra("the",new Card(jsonObject.getString("MaThe"),
                                            jsonObject.getString("MatKhau"),
                                            jsonObject.getString("TenChuThe"),
                                            jsonObject.getInt("TongTien")));
                                    startActivity(intent);
                                    finish();
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
                params.put("mathe",txtTenDangNhapThe.getText().toString());
                params.put("matkhau",txtMatKhauThe.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}