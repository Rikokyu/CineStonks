package com.example.cinestonks.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// Xóa hoặc comment dòng này vì nó gây xung đột tên
// import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.models.SuatChieu;

import java.util.List;

public class ScreeningAdapter extends RecyclerView.Adapter<ScreeningAdapter.ViewHolder> {
    private List<SuatChieu> list;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(SuatChieu suatChieu);
    }

    public ScreeningAdapter(List<SuatChieu> list, OnItemClickListener listener) {
        this.list = list;
        this.mListener = listener;
    }

    public ScreeningAdapter(List<SuatChieu> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_screening, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuatChieu suatChieu = list.get(position);

        holder.tvTime.setText(suatChieu.getGioChieu());

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(suatChieu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        ViewHolder(View v) {
            super(v);
            tvTime = v.findViewById(R.id.tvTime);
        }
    }
}