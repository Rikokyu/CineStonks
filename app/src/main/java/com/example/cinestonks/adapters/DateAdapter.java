package com.example.cinestonks.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.models.Date;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private List<Date> dateList;
    private int selectedPosition = 0;
    private OnDateClickListener listener;

    public interface OnDateClickListener {
        void onDateClick(Date date);
    }

    public DateAdapter(List<Date> dateList, OnDateClickListener listener) {
        this.dateList = dateList;
        this.listener = listener;
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

            notifyItemChanged(previousSelected);
            notifyItemChanged(selectedPosition);

            if (listener != null) {
                listener.onDateClick(date);
            }
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
            tvDayOfWeek = itemView.findViewById(R.id.tvDayOfWeek);
            tvDateValue = itemView.findViewById(R.id.tvDateValue);
            layoutDate = itemView.findViewById(R.id.layoutDate);
        }
    }
}
