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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.adapter.HoaDonAdapter;
import com.example.project.modle.HoaDon;
import com.example.project.modle.User;
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
import java.util.HashMap;
import java.util.Map;

public class HoaDonActivity extends AppCompatActivity {

    private TextView txtTuHoaDonVeTrangChu;
    private ListView lvHoaDonDaThanhToan, lvHoaDonChuaThanhToan;
    private ArrayList<HoaDon> dsHoaDonDaThanhToan, dsHoaDonChuaThanhToan;
    private HoaDonAdapter adapterHoaDonDaThanhToan, adapterHoaDonChuaThanhToan;

    private ImageView imgAddHoaDon, imgSendSMS;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        setUpTapHost();
        addControls();
        addEvents();
    }

    private void setUpTapHost() {
        //Bên trong tabhost sử dùng frameLayout tại 1 thời điểm chỉ hiểu thị 1 layout duy nhất
        //Tabhost là nơi đựng các selector
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        //Bắt buộc phải gọi lệnh này, để cấu hình để tạo ra cái tabhost đó
        tabHost.setup();
        TabHost.TabSpec  tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        //setIndicator chỉ hiển thị hình hoặc chuỗi
        //Nếu như muốn có cả hình cả chuỗi thì nên tạo hình có chứ chuỗi
        tab1.setIndicator("",getResources().getDrawable(R.drawable.dathanhtoan));
        //phải thêm tab vào tabhost
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("",getResources().getDrawable(R.drawable.chuathanhtoan));
        tabHost.addTab(tab2);
    }

    private void addEvents() {
        txtTuHoaDonVeTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        kiemTraRole(user);
        imgAddHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThemHoaDon();
            }
        });
        imgSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSMS = new Intent(HoaDonActivity.this, GuiTinNhanActivity.class);
                startActivity(intentSMS);
            }
        });
    }

    private void xuLyThemHoaDon() {
        final SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date t = calendar.getTime();
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.themhoadon);
        final EditText txtNhapMaHD = (EditText)dialog.findViewById(R.id.txtNhapMaHD);
        final EditText txtNhapmakh = (EditText)dialog.findViewById(R.id.txtNhapmakh);
        final EditText txtNhapNgayPhaiThanhToan = (EditText)dialog.findViewById(R.id.txtNhapNgayPhaiThanhToan);
        final EditText txtNhapChiSoMoi = (EditText)dialog.findViewById(R.id.txtNhapChiSoMoi);
        Button btnThemHD = (Button)dialog.findViewById(R.id.btnThemHD);

        txtNhapNgayPhaiThanhToan.setText(date.format(t));
        //Dữ liệu key
        final ArrayList<String> key = new ArrayList<String>();
        key.add("mahd");
        key.add("makh");
        key.add("ngayphaithanhtoan");
        key.add("chisomoi");
        btnThemHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahd = txtNhapMaHD.getText().toString();
                String makh = txtNhapmakh.getText().toString();
                String ngayphaithanhtoan = txtNhapNgayPhaiThanhToan.getText().toString();
                String chisomoi = txtNhapChiSoMoi.getText().toString();
                if(mahd.isEmpty() || makh.isEmpty() || ngayphaithanhtoan.isEmpty() || chisomoi.isEmpty())
                {
                    Check_Internet_Wifi.showToast_Short(HoaDonActivity.this, "Mời bạn nhập đủ thông tin");
                }
                else
                {
                    //Dữ liệu value
                    ArrayList<String> value = new ArrayList<String>();
                    value.add(mahd);
                    value.add(makh);
                    value.add(ngayphaithanhtoan);
                    value.add(chisomoi);

                    TuongTacServer.insert_Or_update(HoaDonActivity.this,Server.urlThemHoaDon,key, value);
                    finish();
                    dialog.cancel();
                }

            }
        });
        dialog.show();
    }

    private void kiemTraRole(User user) {
        if(user.getRole().equals("QL"))
        {
            imgAddHoaDon.setVisibility(View.VISIBLE);
            imgSendSMS.setVisibility(View.VISIBLE);
            //Hiển thị hóa đơn đã thanh toán
            TuongTacServer.hienThiDuLieu_HoaDon_QL(HoaDonActivity.this,Server.urlhoadondathanhtoan_quanly,dsHoaDonDaThanhToan,adapterHoaDonDaThanhToan);

            //Hiển thị hóa đơn chưa thanh toán
            TuongTacServer.hienThiDuLieu_HoaDon_QL(HoaDonActivity.this,Server.urlhoadonchuathanhtoan_quanly,dsHoaDonChuaThanhToan,adapterHoaDonChuaThanhToan);
        }
        else
        {
            imgAddHoaDon.setVisibility(View.GONE);
            imgSendSMS.setVisibility(View.GONE);
            //Hiển thị hóa đơn đã thanh toán
            TuongTacServer.hienThiDuLieu_HoaDon_KH(HoaDonActivity.this,Server.urlhoadondathanhtoan_khachhang,user.getUserID(),dsHoaDonDaThanhToan,adapterHoaDonDaThanhToan);
            //Hiển thị hóa đơn chưa thanh toán
            TuongTacServer.hienThiDuLieu_HoaDon_KH(HoaDonActivity.this,Server.urlhoadonchuathanhtoan_khachhang,user.getUserID(),dsHoaDonChuaThanhToan,adapterHoaDonChuaThanhToan);
        }
    }
    private void addControls() {
        Loading.loading(HoaDonActivity.this);
        txtTuHoaDonVeTrangChu = (TextView)findViewById(R.id.txtTuHoaDonVeTrangChu);
        imgAddHoaDon = (ImageView)findViewById(R.id.imgAddHoaDon);
        imgSendSMS = (ImageView) findViewById(R.id.imgSendSMS);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Bundle");
        user = (User) bundle.getSerializable("user");


        lvHoaDonDaThanhToan = (ListView)findViewById(R.id.lvHoaDonDaThanhToan);
        lvHoaDonChuaThanhToan = (ListView)findViewById(R.id.lvHoaDonChuaThanhToan);
        dsHoaDonDaThanhToan = new ArrayList<HoaDon>();
        dsHoaDonChuaThanhToan = new ArrayList<HoaDon>();
        adapterHoaDonDaThanhToan = new HoaDonAdapter(HoaDonActivity.this, R.layout.hddathanhtoan, dsHoaDonDaThanhToan,user.getRole());
        adapterHoaDonChuaThanhToan = new HoaDonAdapter(HoaDonActivity.this, R.layout.hddathanhtoan, dsHoaDonChuaThanhToan,user.getRole());
        lvHoaDonDaThanhToan.setAdapter(adapterHoaDonDaThanhToan);
        lvHoaDonChuaThanhToan.setAdapter(adapterHoaDonChuaThanhToan);

    }
}