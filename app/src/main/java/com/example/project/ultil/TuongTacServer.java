package com.example.project.ultil;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    public static void insert(final Context context, String url, final ArrayList<String> key, final ArrayList<String > value)
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
}
