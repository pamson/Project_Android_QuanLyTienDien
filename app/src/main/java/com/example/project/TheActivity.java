package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.project.modle.Card;
import com.example.project.modle.User;
import com.example.project.ultil.Check_Internet_Wifi;

import java.text.DecimalFormat;

public class TheActivity extends AppCompatActivity {

    private TextView txtTuTheVeTrangChu, txtTienKhaDungTren, txtTenChuThe,txtMaThe,txtTienKhaDungDuoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtTuTheVeTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        txtTuTheVeTrangChu = (TextView)findViewById(R.id.txtTuTheVeTrangChu);
        txtTienKhaDungTren = (TextView)findViewById(R.id.txtTienKhaDungTren);
        txtTenChuThe = (TextView)findViewById(R.id.txtTenChuThe);
        txtMaThe = (TextView)findViewById(R.id.txtMaThe);
        txtTienKhaDungDuoi = (TextView)findViewById(R.id.txtTienKhaDungDuoi);

        Intent intent = getIntent();
        Card card = (Card) intent.getSerializableExtra("the");

        DecimalFormat dcf = new DecimalFormat("###,###");
        String tien = dcf.format(card.getTongTien()) + " VND";
        txtTienKhaDungTren.setText(tien);
        txtTienKhaDungDuoi.setText(tien);
        txtTenChuThe.setText(card.getTenChuThe());
        txtMaThe.setText(card.getMaThe());


    }
}