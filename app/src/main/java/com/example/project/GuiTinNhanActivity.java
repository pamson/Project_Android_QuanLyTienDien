package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Magnifier;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.ultil.Check_Internet_Wifi;
import com.example.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GuiTinNhanActivity extends AppCompatActivity {

    private TextView txtTuSendSMSVeHoaDon;
    private EditText txtNoiDungTinNhan;
    private Button btnGui;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_tin_nhan);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtTuSendSMSVeHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laySdtKhachHangTuServerVe_GuiTinNhan();
            }
        });
    }

    private void laySdtKhachHangTuServerVe_GuiTinNhan() {
        RequestQueue queue = Volley.newRequestQueue(GuiTinNhanActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Server.urlKhachHang, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0; i< response.length(); i++)
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String sdt = jsonObject.getString("SDT");
                                xuLyGuiSMS(sdt);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Loi_KH",error.toString());
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }


    private void xuLyGuiSMS(String sdt) {
        //Lấy mặc định SmsManager
        final SmsManager sms = SmsManager.getDefault();
        //Gửi lệnh cho hệ thống yêu cầu muốn nhắn tin
        //Mặc dù cấp quyền gửi tin nhắn nhưng vì bảo mật người dùng phải cho phép mới có quyền sử dụng tính năng này
        Intent mgsSent = new Intent("ACTION_MSG_SENT");
        //Khai báo pendinding intent để kiểm tra kết quả
        // PendingIntent là 1 Intent nhưng là intent delay
        // Nó cứ chờ đến khi nào thực hiện xong nó mới dc kích hoạt
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,mgsSent,0);

        // Cấu hình để tự động lắng nghe kết quả
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg = "Gửi thành công";
                if(result != Activity.RESULT_OK)
                {
                    msg = "Gửi thất bại";
                }
                Check_Internet_Wifi.showToast_Short(GuiTinNhanActivity.this,msg);
            }
        },new IntentFilter("ACTION_MSG_SENT"));


        // Gọi hàm gửi tin nhắn đi
        sms.sendTextMessage(sdt,null,txtNoiDungTinNhan.getText().toString(), pendingIntent,null);
    }

    private void addControls() {
        ActivityCompat.requestPermissions(GuiTinNhanActivity.this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        txtTuSendSMSVeHoaDon = (TextView)findViewById(R.id.txtTuSendSMSVeHoaDon);
        txtNoiDungTinNhan = (EditText)findViewById(R.id.txtNoiDungTinNhan);
        btnGui = (Button)findViewById(R.id.btnGui);
    }
}