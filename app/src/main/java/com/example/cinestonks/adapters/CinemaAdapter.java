package com.example.cinestonks.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.BLL.chon_phim;
import com.example.cinestonks.models.Rap;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder> {
    private List<Rap> list;
    private String userId;

    public CinemaAdapter(List<Rap> list, String userId) {
        this.list = list;
        this.userId = userId;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvPhone;
        public ViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_ten_rap);
            tvAddress = view.findViewById(R.id.tv_dia_chi);
            tvPhone = view.findViewById(R.id.tv_sdt);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cinema, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rap c = list.get(position);

        if (c != null) {
            holder.tvName.setText(c.getTenRap() != null ? c.getTenRap() : "N/A");
            holder.tvAddress.setText(c.getDiaChi() != null ? c.getDiaChi() : "Chưa có địa chỉ");
            holder.tvPhone.setText(c.getSDT() != null ? c.getSDT() : "N/A");
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, chon_phim.class);
            intent.putExtra("SELECTED_NAME", String.valueOf(c.getTenRap()));
            intent.putExtra("MA_RAP", String.valueOf(c.getId()));
            intent.putExtra("USER_ID", userId); // Truyền tiếp userId
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }
}
