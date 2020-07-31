package com.example.project.ultil;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.modle.HoaDon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TuongTacServer {
    /**
     * các giá trị trong key , value phải sắp xếp theo thứ tự tương ứng nhau
     * ví dụ giá trị đầu tên trong key tương ứng với giá trị đầu tiên trong value
     * quá trình thực hiện gán các giá trị lần lượt key.get(i) = value.get(i)
     */
    //thêm dữ liệu lên server
    public static void insert_Or_update(final Context context, String url, final ArrayList<String> key, final ArrayList<String > value)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success"))
                            Check_Internet_Wifi.showToast_Short(context,"Success!!!");
                        else
                            Check_Internet_Wifi.showToast_Short(context, "Fail!!!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("loi_insert",error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                for(int i = 0; i < key.size(); i++)
                {
                    params.put(key.get(i),value.get(i));
                }

                return params;
            }
        };
        queue.add(stringRequest);
    }

    /**
     * Xóa dữ liệu trên server
     */
    public static void delete(final Context context, String url, final String key, final String value)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success"))
                            Check_Internet_Wifi.showToast_Short(context,"Success!!!");
                        else
                            Check_Internet_Wifi.showToast_Short(context, "Fail!!!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Loi_delete",error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put(key,value);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    /**
     * Hiên thị hóa đơn trên Server
     * @param context
     * @param url
     * @param ds
     * @param adapter
     */
    public static void hienThiDuLieu_HoaDon_QL(Context context, String url, final ArrayList<HoaDon> ds, final ArrayAdapter<HoaDon> adapter)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i< response.length(); i++)
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String maHD = jsonObject.getString("MaHD");
                                String maKH =jsonObject.getString("MaKH");
                                String ngayPhaiThanhToan = jsonObject.getString("NgayPhaiThanhToan");
                                String ngayThanhToan = jsonObject.getString("NgayThanhToan");
                                String trangThai = jsonObject.getString("TrangThai");
                                int chiSoCu = jsonObject.getInt("ChiSoCu");
                                int chiSoMoi = jsonObject.getInt("ChiSoMoi");
                                int soKwh = jsonObject.getInt("SoKwh");
                                int soTienKwh = jsonObject.getInt("SoTienKwwh");
                                int tongTien = jsonObject.getInt("TongTien");
                                ds.add(new HoaDon(maHD,maKH,ngayPhaiThanhToan,ngayThanhToan,chiSoCu,chiSoMoi,soKwh,soTienKwh,tongTien,trangThai));
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Loi",error.toString());
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }
    /**
     * Hiên thị hóa đơn trên Server
     * @param context
     * @param url
     * @param ds
     * @param adapter
     */
    public static void hienThiDuLieu_HoaDon_KH(Context context, String url, final String value, final ArrayList<HoaDon> ds, final ArrayAdapter<HoaDon> adapter)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String maHD = jsonObject.getString("MaHD");
                                String maKH =jsonObject.getString("MaKH");
                                String ngayPhaiThanhToan = jsonObject.getString("NgayPhaiThanhToan");
                                String ngayThanhToan = jsonObject.getString("NgayThanhToan");
                                String trangThai = jsonObject.getString("TrangThai");
                                int chiSoCu = jsonObject.getInt("ChiSoCu");
                                int chiSoMoi = jsonObject.getInt("ChiSoMoi");
                                int soKwh = jsonObject.getInt("SoKwh");
                                int soTienKwh = jsonObject.getInt("SoTienKwwh");
                                int tongTien = jsonObject.getInt("TongTien");
                                ds.add(new HoaDon(maHD,maKH,ngayPhaiThanhToan,ngayThanhToan,chiSoCu,chiSoMoi,soKwh,soTienKwh,tongTien,trangThai));
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Loi_hoadon",error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("makh",value);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
