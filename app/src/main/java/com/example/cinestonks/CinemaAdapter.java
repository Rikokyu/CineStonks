package com.example.cinestonks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder> {
    private List<Rap> list;

    public CinemaAdapter(List<Rap> list) {
        this.list = list;
    }

    // Lớp ViewHolder để giữ các View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvPhone;

        public ViewHolder(@NonNull View view) {
            super(view);
            // Đảm bảo các ID này trùng khớp với file cinema.xml của bạn
            tvName = view.findViewById(R.id.tv_ten_rap);
            tvAddress = view.findViewById(R.id.tv_dia_chi);
            tvPhone = view.findViewById(R.id.tv_sdt);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nạp layout cinema.xml cho từng item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cinema, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rap c = list.get(position);

        // Kiểm tra tránh crash nếu dữ liệu từ Firebase bị thiếu hoặc sai cấu trúc
        if (c != null) {
            holder.tvName.setText(c.getTenRap() != null ? c.getTenRap() : "N/A");
            holder.tvAddress.setText(c.getDiaChi() != null ? c.getDiaChi() : "Chưa có địa chỉ");
            holder.tvPhone.setText(c.getSDT() != null ? c.getSDT() : "N/A");
        }
    }

    @Override
    public int getItemCount() {
        // Tránh lỗi nếu list bị null trước khi dữ liệu kịp đổ về
        return (list != null) ? list.size() : 0;
    }
}