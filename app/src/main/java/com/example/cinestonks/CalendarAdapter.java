package com.example.cinestonks;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    private List<String> dayNames; // Thứ (T2, T3...)
    private List<String> dates;    // Ngày (16/03, 17/03...)
    private int selectedPosition = 0;
    public CalendarAdapter(List<String> dayNames, List<String> dates) {
        this.dayNames = dayNames;
        this.dates = dates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDayName.setText(dayNames.get(position));
        holder.tvDate.setText(dates.get(position));

        // Logic đổi màu: Ví dụ ô đầu tiên (hôm nay) là màu vàng, còn lại màu tím
        if (selectedPosition == position) {
            holder.layoutItem.setBackgroundResource(R.drawable.bg_selected_date);
            holder.tvDayName.setTextColor(Color.BLACK);
            holder.tvDate.setTextColor(Color.BLACK);
        } else {
            holder.layoutItem.setBackgroundResource(R.drawable.bg_unselected_date);
            holder.tvDayName.setTextColor(Color.WHITE);
            holder.tvDate.setTextColor(Color.WHITE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu lại vị trí cũ để cập nhật (vẽ lại)
                int previousPosition = selectedPosition;

                // Cập nhật vị trí mới được click
                selectedPosition = holder.getAdapterPosition();

                // Chỉ vẽ lại 2 ô: ô vừa click và ô cũ đang vàng
                notifyItemChanged(previousPosition);
                notifyItemChanged(selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayName, tvDate;
        LinearLayout layoutItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.tv_day_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            layoutItem = itemView.findViewById(R.id.layout_item);
        }
    }
}
