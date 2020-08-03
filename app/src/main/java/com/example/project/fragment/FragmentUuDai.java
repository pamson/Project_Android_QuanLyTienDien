package com.example.project.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.R;
import com.example.project.adapter.UuDaiAdapter;
import com.example.project.modle.UuDai;
import com.example.project.ultil.Loading;
import com.example.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentUuDai extends Fragment
{
    private View view;
    private RecyclerView rcvUuDai;
    private ArrayList<UuDai> dsUuDai;
    private UuDaiAdapter uuDaiAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_uudai,container,false);
        addControls();
        layDuLieuTuServerVe();
        return view;
    }

    private void layDuLieuTuServerVe() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Server.urlUuDai, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0; i< response.length(); i++)
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("ID");
                                String imgUuDai = jsonObject.getString("HinhAnh");
                                String noiDung = jsonObject.getString("NoiDung");
                                dsUuDai.add(new UuDai(id,imgUuDai,noiDung));
                                uuDaiAdapter.notifyDataSetChanged();
                                Loading.destroyLoading();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Loi_UuDai",error.toString());
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    private void addControls() {
        Loading.loading(getActivity());
        rcvUuDai = (RecyclerView)view.findViewById(R.id.rcvUuDai);
        //Giúp tối ưu hóa dữ liệu để không ảnh hưởng bởi nội dung trong adapter
        rcvUuDai.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);


        // tạo ra khoảng các ngăn giữa các item
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL);
        Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.custom_diveder);
        //do setDrawable truyền vào 1 drawable nên phải tại Drawable vì
        // vì R.drawable.custom_diveder  trả về kiểu int
        dividerItemDecoration.setDrawable(drawable);
        rcvUuDai.addItemDecoration(dividerItemDecoration);


        rcvUuDai.setLayoutManager(linearLayoutManager);
        dsUuDai = new ArrayList<UuDai>();

        uuDaiAdapter = new UuDaiAdapter(getActivity(),dsUuDai);
        rcvUuDai.setAdapter(uuDaiAdapter);


    }
}
