package com.example.cinestonks;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
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

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();

            // 1. Dùng context lấy từ view, và đích đến là class chon_phim
            Intent intent = new Intent(context, chon_phim.class);

            // 2. Truyền ID (Đảm bảo c.getId() trả về kiểu dữ liệu bạn muốn, ví dụ String hoặc int)
            intent.putExtra("SELECTED_NAME", String.valueOf(c.getTenRap()));
            intent.putExtra("MA_RAP", String.valueOf(c.getId()));

            // 3. Gọi startActivity từ chính biến context đã lấy ở trên
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Tránh lỗi nếu list bị null trước khi dữ liệu kịp đổ về
        return (list != null) ? list.size() : 0;
    }
}