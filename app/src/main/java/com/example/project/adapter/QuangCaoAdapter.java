package com.example.project.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.project.R;
import com.example.project.modle.QuangCao;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Sử dụng viewpager phải kết thừa PagerAdapter
 */
public class QuangCaoAdapter extends PagerAdapter
{
    private Context context;
    private ArrayList<QuangCao> dsQuangCao;

    public QuangCaoAdapter(Context context, ArrayList<QuangCao> dsQuangCao) {
        this.context = context;
        this.dsQuangCao = dsQuangCao;
    }

    /**
     * Mình muốn vẽ bao nhiêu cái pager trong ViewPager thì ở trong mảng có bao nhiêu
     * tấm hình thì vẽ bằng đó pager
     * @return
     */
    @Override
    public int getCount() {
        return dsQuangCao.size();
    }

    /**
     * function này sẽ trả về view theo object đó định hình
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //Tạo một custom view mẫu để các pager còn lại the dùng theo
    //Giống như làm customs adapter
    /**
     * Funtions này là đi định hình và gán dữ liệu cho
     * mỗi object tượng trưng cho mỗi pager
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hienthiquangcao,null);
        QuangCao quangCao = dsQuangCao.get(position);
        ImageView imgBackGroudQuangCao = (ImageView)view.findViewById(R.id.imgBackGroudQuangCao);
        TextView txtNoiDung = (TextView)view.findViewById(R.id.txtNoiDung);
        Button btnTraiNghiem = (Button)view.findViewById(R.id.btnTraiNghiem);

        //Đối với phần hình ảnh ta sẽ dùng thư viện ngoài
        Picasso.with(context).load(quangCao.getHinhAnh()).into(imgBackGroudQuangCao);
        txtNoiDung.setText(quangCao.getNoiDung());
        //add view vào bên trong của ViewPager
        container.addView(view);
        return view;
    }
    /**
     * Pager nó có 1 trường hợp là minh chuyển pager đến pager cuối cùng
     * mà ko cho finsh thì nó sẽ bị lỗi
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //Xóa view đi khi nó thực hiện xong
        container.removeView((View) object);
    }
}
