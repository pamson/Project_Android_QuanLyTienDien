package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.adapter.UserAdapter;
import com.example.project.modle.User;
import com.example.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserKHActivity extends AppCompatActivity {

    private TextView txtTuTaiKhoanKhachHangVeTrangChu;
    private ListView lvTaiKhoanKhachHang;
    private ArrayList<User> dsUser;
    private UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_k_h);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtTuTaiKhoanKhachHangVeTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        HienThiUser();
    }

    private void HienThiUser() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Server.urlUser, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0; i < response.length(); i++)
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String userID = jsonObject.getString("UserID");
                                String passWord = jsonObject.getString("PassWord");
                                String Name = jsonObject.getString("Name");
                                String Role = jsonObject.getString("Role");
                                dsUser.add(new User(userID,passWord,Name, Role));
                                userAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Loi_hienthiuser",error.toString());
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    private void addControls() {
        txtTuTaiKhoanKhachHangVeTrangChu = (TextView) findViewById(R.id.txtTuTaiKhoanKhachHangVeTrangChu);
        lvTaiKhoanKhachHang = (ListView)findViewById(R.id.lvTaiKhoanKhachHang);
        dsUser = new ArrayList<User>();
        userAdapter = new UserAdapter(UserKHActivity.this,R.layout.userkhachhang,dsUser);
        lvTaiKhoanKhachHang.setAdapter(userAdapter);
    }
}