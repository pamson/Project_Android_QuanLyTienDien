package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.project.ultil.Check_Internet_Wifi;
import com.example.project.ultil.Loading;
import com.example.project.ultil.Server;
import com.example.project.ultil.TuongTacServer;

import java.util.ArrayList;

public class DoiMatKhauActivity extends AppCompatActivity {

    private EditText txtTenDangNhapDoiMK, txtMatKhauCu,txtMatKhauMoi;
    private CheckBox ckh_eyeMKCu, ckh_eyeMKMoi;
    private Button btnDoiMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        addControls();
        addEvents();
    }

    private void addEvents() {
        xuLyHienThiMkCu();
        xuLyHienThiMKMoi();
        xuLyDoiMatKhau();
    }

    private void xuLyDoiMatKhau() {
        //Dữ liệu trong key
        final ArrayList<String> key = new ArrayList<String>();
        key.add("userid");
        key.add("mkcu");
        key.add("mkmoi");
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading.loading(DoiMatKhauActivity.this);
                if(Check_Internet_Wifi.haveNetworkConnection(DoiMatKhauActivity.this))
                {
                    String userID = txtTenDangNhapDoiMK.getText().toString().trim();
                    String mkcu = txtMatKhauCu.getText().toString().trim();
                    String mkmoi = txtMatKhauMoi.getText().toString().trim();
                    if(userID.isEmpty() || mkmoi.isEmpty() || mkcu.isEmpty())
                    {
                        Check_Internet_Wifi.showToast_Short(DoiMatKhauActivity.this,"Mời bạn nhập đủ thông tin");
                        Loading.destroyLoading();
                    }
                    else
                    {
                        //Dữ liệu trong value
                        ArrayList<String> value = new ArrayList<String>();
                        value.add(userID);
                        value.add(mkcu);
                        value.add(mkmoi);
                        TuongTacServer.insert_Or_update(DoiMatKhauActivity.this, Server.urlDoiMK,key, value);
                    }
                }
                else
                {
                    Check_Internet_Wifi.showToast_Short(DoiMatKhauActivity.this,"No Wifi or Internet");
                    finish();
                }
            }
        });
    }

    private void xuLyHienThiMKMoi() {
        ckh_eyeMKMoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                    txtMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    private void xuLyHienThiMkCu() {
        ckh_eyeMKCu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtMatKhauCu.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                    txtMatKhauCu.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    private void addControls() {
        txtTenDangNhapDoiMK = (EditText)findViewById(R.id.txtTenDangNhapDoiMK);
        txtMatKhauCu = (EditText)findViewById(R.id.txtMatKhauCu);
        txtMatKhauMoi = (EditText)findViewById(R.id.txtMatKhauMoi);
        ckh_eyeMKCu = (CheckBox)findViewById(R.id.ckh_eyeMKCu);
        ckh_eyeMKMoi = (CheckBox)findViewById(R.id.ckh_eyeMKMoi);
        btnDoiMK = (Button)findViewById(R.id.btnDoiMK);
    }
}