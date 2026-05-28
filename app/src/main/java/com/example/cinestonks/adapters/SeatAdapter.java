package com.example.cinestonks.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.models.Seat;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private List<Seat> seatList;
    private OnSeatClickListener listener;

    public interface OnSeatClickListener {
        void onSeatClick(int position);
    }

    public SeatAdapter(List<Seat> seatList, OnSeatClickListener listener) {
        this.seatList = seatList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);
        holder.tvSeatName.setText(seat.getTenGhe());

        int bgColor;
        int textColor;

        // LUỒNG HIỂN THỊ THEO YÊU CẦU:
        if ("1".equals(seat.getTrangThai())) {
            // Nếu TrangThai == 1: Hiển thị ghế đã bán (màu xám, không cho nhấn)
            bgColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.gray);
            holder.tvSeatName.setText("X"); // Hoặc để trống
            textColor = Color.WHITE;
            holder.itemView.setEnabled(false); // Vô hiệu hóa click
        } else {
            // Nếu TrangThai == 0 (hoặc khác 1): Hiển thị bình thường
            holder.itemView.setEnabled(true);

            if (seat.isSelected()) {
                // Đang được người dùng chọn
                bgColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.green);
                textColor = Color.WHITE;
            } else if ("Đôi".equals(seat.getLoaiGhe())) {
                // Ghế đôi chưa chọn
                bgColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.yellow);
                textColor = Color.BLACK;
            } else {
                // Ghế thường chưa chọn
                bgColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.purple);
                textColor = Color.WHITE;
            }
        }

        holder.cardSeat.setCardBackgroundColor(bgColor);
        holder.tvSeatName.setTextColor(textColor);

        holder.itemView.setOnClickListener(v -> {
            // Chỉ xử lý click nếu ghế chưa bán (TrangThai == 0)
            if (!"1".equals(seat.getTrangThai())) {
                listener.onSeatClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seatList != null ? seatList.size() : 0;
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        CardView cardSeat;
        TextView tvSeatName;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            cardSeat = itemView.findViewById(R.id.card_seat);
            tvSeatName = itemView.findViewById(R.id.tv_seat_name);
        }
    }
}
