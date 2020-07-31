package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.modle.User;
import com.example.project.ultil.Check_Internet_Wifi;

public class MainActivity extends AppCompatActivity {
    private TextView txtXinMoiDangNhap;
    private ImageButton btnKhachHang, btnGiaDien, btnCard, btnHienThiTaiKhoan, btnHoaDon;
    private Intent intent;
    private User user;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtXinMoiDangNhap = (TextView)findViewById(R.id.txtXinMoiDangNhap);
        btnKhachHang = (ImageButton) findViewById(R.id.btnKhachHang);
        btnGiaDien = (ImageButton) findViewById(R.id.btnGiaDien);
        btnCard = (ImageButton) findViewById(R.id.btnCard);
        btnHienThiTaiKhoan = (ImageButton) findViewById(R.id.btnHienThiTaiKhoan);
        btnHoaDon = (ImageButton) findViewById(R.id.btnHoaDon);


        intent = new Intent(MainActivity.this, DangNhap.class);
    }

    //Là hàm tự động lấy được kết quả trả về từ màn hình khác
    // Với điều kiện nó phải ở trong foreground lifetime
    // Tức là nó phải nhìn thấy được và tương tác được với màn hình này
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == 100)
        {
            user = (User) data.getSerializableExtra("user");
            txtXinMoiDangNhap.setText(user.getName());
            role = user.getRole();
        }
    }

    private void addEvents() {
        btnKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyButtonKhachHang();
            }
        });
        btnHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyButtonHoaDon();
            }
        });
        btnHienThiTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyButtonHienThiTaiKhoan();
            }
        });
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyButtonCard();
            }
        });
        btnGiaDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyButtonGiaDien();
            }
        });
    }


    private void xuLyButtonGiaDien() {
        Intent intentGiaDien = new Intent(MainActivity.this, GiaDienActivity.class);
        intentGiaDien.putExtra("role",role);
        if(txtXinMoiDangNhap.getText().equals("ĐĂNG NHẬP"))
        {
            startActivityForResult(intent,99);
        }
        else if(role.equals("QL"))
        {
            startActivity(intentGiaDien);
        }
        else
        {
            startActivity(intentGiaDien);
        }
    }

    private void xuLyButtonCard() {
        if(txtXinMoiDangNhap.getText().equals("ĐĂNG NHẬP"))
        {
            startActivityForResult(intent,99);
        }
        else if(role.equals("KH"))
        {
            Intent intentCard = new Intent(MainActivity.this, DangNhapTheActivity.class);
            startActivity(intentCard);
        }
    }

    private void xuLyButtonHienThiTaiKhoan() {
        if(txtXinMoiDangNhap.getText().equals("ĐĂNG NHẬP"))
        {
            startActivityForResult(intent,99);
        }
        else if(role.equals("QL"))
        {
            Intent intentTaiKhoanKH = new Intent(MainActivity.this, UserKHActivity.class);
            startActivity(intentTaiKhoanKH);
        }
    }
    private void xuLyButtonHoaDon() {
        Intent intentHoaDon = new Intent(MainActivity.this, HoaDonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        intentHoaDon.putExtra("Bundle",bundle);
        if(txtXinMoiDangNhap.getText().equals("ĐĂNG NHẬP"))
        {
            startActivityForResult(intent,99);
        }
        else if(role.equals("QL"))
        {
            startActivity(intentHoaDon);
        }
        else
        {
            startActivity(intentHoaDon);
        }
    }

    private void xuLyButtonKhachHang() {
        if(txtXinMoiDangNhap.getText().equals("ĐĂNG NHẬP"))
        {
            startActivityForResult(intent,99);
        }
        else if(role.equals("QL"))
        {
            Intent intentKhachHang = new Intent(MainActivity.this, KhachHangActivity.class);
            startActivity(intentKhachHang);
        }
    }
}
