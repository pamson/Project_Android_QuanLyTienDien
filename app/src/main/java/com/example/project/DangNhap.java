package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.modle.User;
import com.example.project.ultil.Check_Internet_Wifi;
import com.example.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DangNhap extends AppCompatActivity {

    private TextView txtDangKy;
    private EditText txtTenDangNhap, txtMatKhau;
    private Button btnDangNhap;
    private CheckBox ckh_eye;
    private Intent intent;

    private String saveUser = "luutaikhoan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        addControls();
        addEvents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(saveUser,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName",txtTenDangNhap.getText().toString());
        editor.putString("passWord",txtMatKhau.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(saveUser, MODE_PRIVATE);
        String uName = sharedPreferences.getString("userName","");
        String pWord = sharedPreferences.getString("passWord","");
        txtTenDangNhap.setText(uName);
        txtMatKhau.setText(pWord);
    }

    private void addEvents() {
        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check_Internet_Wifi.showToast_Short(DangNhap.this, "Chào mừng bạn đến với iPson");
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Check_Internet_Wifi.haveNetworkConnection(DangNhap.this))
                {
                    String userName = txtTenDangNhap.getText().toString().trim();
                    String pass = txtMatKhau.getText().toString().trim();
                    if(userName.isEmpty() || pass.isEmpty())
                    {
                        Check_Internet_Wifi.showToast_Short(DangNhap.this,"Mời bạn nhập đủ thông tin");
                    }
                    else
                    {
                        loginUser();
                    }
                }
                else
                {
                    Check_Internet_Wifi.showToast_Short(DangNhap.this,"No Wifi or Internet");
                    finish();
                }
            }
        });

        xulyHienThiMatKhau();
    }

    private void xulyHienThiMatKhau() {
        ckh_eye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtMatKhau.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                    txtMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    private void loginUser() {
        RequestQueue queue = Volley.newRequestQueue(DangNhap.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("[]"))
                        {
                            Check_Internet_Wifi.showToast_Short(DangNhap.this, "Wrong Password or User");
                        }
                        else
                        {
                            try {
                                JSONArray  jsonArray = new JSONArray(response);
                                for(int i =0; i< jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    //Thay đổi thông tin và gán vào cho intent
                                    // nên dùng lại intet(Không nên tạo mới)
                                    intent.putExtra("user", new User(jsonObject.getString("UserID"),
                                            jsonObject.getString("PassWord"),
                                            jsonObject.getString("Name"),
                                            jsonObject.getString("Role")));
                                    //Đánh dấu kết quả trả về
                                    setResult(100, intent);
                                    //Phải đóng màn hình này lại
                                    //Để màn hình MainActivity trở thành Foregroud lifetime
                                    // Vì nó chỉ nhận được kết quả trả về ở trong Foregroud lifetime --> bắt đầu onResume()
                                    finish();
                                }

                            } catch (JSONException e) {
                                Log.e("LoiTKMK", e.toString());
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("lll","loi",error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String ,String>();
                params.put("UserID",txtTenDangNhap.getText().toString());
                params.put("PassWord",txtMatKhau.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void addControls() {
        txtDangKy = (TextView)findViewById(R.id.txtDangKy);

        txtTenDangNhap = (EditText)findViewById(R.id.txtTenDangNhap);
        txtMatKhau = (EditText)findViewById(R.id.txtMatKhau);
        btnDangNhap = (Button)findViewById(R.id.btnDangNhap);

        ckh_eye = (CheckBox)findViewById(R.id.ckh_eye);

        //Lấy và gửi dữ liệu trả về
        intent = getIntent();
    }
}
