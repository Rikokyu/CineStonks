package com.example.cinestonks.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.models.HoaDon;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final List<HoaDon> historyList;

    public HistoryAdapter(List<HoaDon> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HoaDon hoaDon = historyList.get(position);
        holder.tvMovieName.setText(hoaDon.getTenPhim());
        holder.tvDateTime.setText(hoaDon.getNgayThanhToan() + " - " + hoaDon.getSuatChieu());
        holder.tvCinema.setText(hoaDon.getTenRap() + " - " + hoaDon.getPhongChieu());
        
        List<String> seats = hoaDon.getDanhSachGhe();
        if (seats != null) {
            holder.tvSeats.setText("Ghế: " + String.join(", ", seats));
        }
        
        holder.tvPrice.setText("Tổng tiền: " + hoaDon.getTongTien());
        holder.tvStatus.setText(hoaDon.getTrangThai());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieName, tvDateTime, tvCinema, tvSeats, tvPrice, tvStatus;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvCinema = itemView.findViewById(R.id.tvCinema);
            tvSeats = itemView.findViewById(R.id.tvSeats);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
