package com.example.project.ultil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class Check_Internet_Wifi
{
    //Để kiểm tra kết nối bạn cần phải cấp quền cho nó
    // <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    //Kiểm tra kết nối
    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        //getSystemService là lấy dich vụ từ màn hình nào đó phải truyền màn hình muốn lấy SERVICE này
        //ConnectivityManager là một lớp quản lý các kết nối
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //trả về dịch vụ internet của thiể bị
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    //Thông báo
    public static void showToast_Short(Context context, String thongBao)
    {
        Toast.makeText(context,thongBao,Toast.LENGTH_LONG).show();
    }
}
