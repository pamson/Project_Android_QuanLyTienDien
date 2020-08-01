package com.example.project.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.modle.UuDai;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * RecyclerView luôn luôn phải đi cùng với ViewHolder
 */
public class UuDaiAdapter extends RecyclerView.Adapter<UuDaiAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<UuDai> dsUuDai;


    public UuDaiAdapter(Context context, ArrayList<UuDai> dsUuDai) {
        this.context = context;
        this.dsUuDai = dsUuDai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.uudai,parent,false);
        return new ViewHolder(itemView);
    }

    /**
     * Gán dữ liệu vào các UI
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UuDai uuDai = dsUuDai.get(position);
        Picasso.with(context).load(uuDai.getHinhAnh()).into(holder.imgUuDai);
        holder.txtNoiDungUuDai.setText(uuDai.getNoiDung());

    }

    @Override
    public int getItemCount() {
        return dsUuDai.size();
    }

    /**
     *Bắt buộc phải tạo lớp ViewHolder để kế thừa từ lớp RecyclerView.ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //Nơi khai báo cái UI ở đây
        private ImageView imgUuDai;
        private TextView txtNoiDungUuDai;
        private ImageButton btnUuDai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addControls(itemView);
        }

        private void addControls(View itemView) {
            imgUuDai =(ImageView)itemView.findViewById(R.id.imgUuDai);
            txtNoiDungUuDai = (TextView)itemView.findViewById(R.id.txtNoiDungUuDai);
            btnUuDai = (ImageButton)itemView.findViewById(R.id.btnUuDai);
        }
    }
}
