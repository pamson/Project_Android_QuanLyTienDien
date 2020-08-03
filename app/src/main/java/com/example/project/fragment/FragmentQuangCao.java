package com.example.project.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.R;
import com.example.project.adapter.QuangCaoAdapter;
import com.example.project.modle.QuangCao;
import com.example.project.ultil.Loading;
import com.example.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class FragmentQuangCao extends Fragment
{
    /**
     *  Fragment biểu diễn một hành vi hay một phần giao diện người dùng trong một Activity
     *  Bạn có thể kết hợp nhiều phân đoạn trong một hoạt động duy nhất để xây dựng một UI nhiều bảng và
     *  sử dụng lại phân đoạn trong nhiều hoạt động.
     *  Bạn có thể coi phân đoạn như là một phần mô-đun của một hoạt động,
     *  có vòng đời của chính nó, nhận các sự kiện đầu vào của chính nó,
     *  và bạn có thể thêm hoặc gỡ bỏ trong khi hoạt động đang chạy (kiểu như một "hoạt động con"
     *  mà bạn có thể sử dụng lại trong các hoạt động khác nhau).
     */

    private View view;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private ArrayList<QuangCao> dsQuangCao;
    private QuangCaoAdapter quangCaoAdapter;

    //để các pager tự động chạy cần khai báo và sử dung
    private Handler handler;
    private Runnable runnable;
    private int currentItem;
    /**
     * Gọi function này để có thể gắn layout vào
     * 1 Fragment thường là 1 phần của activity user interface và sẽ "đóng góp " layout của nó tới activity
     * Để set 1 layout cho fragment , bạn phải implement onCreateVIew() ,
     * hàm này được gọi bởi Android system và khi đó layout bắt đầu được vẽ
     * .Hàm này trả về 1 View (chính là root view của layout fragment )
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /**
         * Tham số 1: Resoure ID của layout mà bạn muốn inflate
         *  Tham số 2: ViewGroup(lớp cha của layout được inflate ) .
         *  Truyền vào container rất quan trọng để hệ thống có thể apply parameter của layout đến root view .
         *  Tham số 3:Một biến boolean chỉ ra rằng layout có dc gắn vào Viewgroup trong khi đang inflate hay không
         *  (Trong trường hợp này set false vì hệ thống đã insert layout vào container rồi ,
         *  còn set true thì nó sẽ "miễn cưỡng " tạo 1 viewgroup trong layout cuối )
         *  Tiếp đến chúng ta phải add fragment vào activity.
         */
        view = inflater.inflate(R.layout.fragment_quangcao,container,false);
        addControls();
        addEvents();
        layDuLieuTuServerVeApp();
        xuLyHienThiDulieu();
        return view;
    }

    private void addEvents() {

    }

    private void addControls() {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.indicatorDefaut);

        dsQuangCao = new ArrayList<QuangCao>();

    }

    private void layDuLieuTuServerVeApp() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Server.urlQuangCao, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0; i < response.length(); i++)
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("ID");
                                String hinhAnh = jsonObject.getString("HinhAnh");
                                String noiDung = jsonObject.getString("NoiDung");
                                dsQuangCao.add(new QuangCao(id,hinhAnh,noiDung));
                                quangCaoAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Loi_QC",error.toString());
                    }
                }
        );
        queue.add(jsonArrayRequest);

    }
    public void xuLyHienThiDulieu()
    {
        quangCaoAdapter = new QuangCaoAdapter(getActivity(),dsQuangCao);
        viewPager.setAdapter(quangCaoAdapter);
        //xét có bao pager thì hiểu thị bằng đấy indicator
        circleIndicator.setViewPager(viewPager);


        //handler giống như quản lý còn runable là các sự việc được gọi
        handler = new Handler();
        //hàm Runabale này sẽ thực hiện lắng nghe khi handler gọi
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = viewPager.getCurrentItem();
                currentItem++;
                if(currentItem >= viewPager.getAdapter().getCount())
                {
                    currentItem = 0;
                }
                viewPager.setCurrentItem(currentItem,true);
                handler.postDelayed(runnable,4000);
            }
        };
        handler.postDelayed(runnable,4000);
    }
}
