package com.example.cinestonks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    // Đổi từ Date thành DateModel để tránh trùng thư viện hệ thống
    private List<Date> dateList;
    private int selectedPosition = 0;

    public DateAdapter(List<Date> dateList) {
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Date date = dateList.get(position);
        holder.tvDayOfWeek.setText(date.getDayOfWeek());
        holder.tvDateValue.setText(date.getDate());

        // Xử lý màu sắc dựa trên vị trí được chọn
        if (selectedPosition == position) {
            holder.layoutDate.setBackgroundResource(R.drawable.bg_date_selected);
            holder.tvDayOfWeek.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black));
            holder.tvDateValue.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black));
        } else {
            holder.layoutDate.setBackgroundResource(R.drawable.bg_date_unselected);
            holder.tvDayOfWeek.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white));
            holder.tvDateValue.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white));
        }

        holder.itemView.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            // Chỉ cập nhật lại 2 item thay đổi để tối ưu hiệu năng
            notifyItemChanged(previousSelected);
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return dateList != null ? dateList.size() : 0;
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayOfWeek, tvDateValue;
        LinearLayout layoutDate;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            // Đảm bảo các ID này trùng khớp với ID trong file item_day.xml
            tvDayOfWeek = itemView.findViewById(R.id.tvDayOfWeek);
            tvDateValue = itemView.findViewById(R.id.tvDateValue);
            layoutDate = itemView.findViewById(R.id.layoutDate);
        }
    }
}