package com.example.cinestonks;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private List<Phong.Ghe> seatList;
    private OnSeatClickListener listener;

    public interface OnSeatClickListener {
        void onSeatClick(int position);
    }

    public SeatAdapter(List<Phong.Ghe> seatList, OnSeatClickListener listener) {
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
        Phong.Ghe seat = seatList.get(position);
        holder.tvSeatName.setText(seat.getTenGhe());

        int bgColor;
        int textColor;

        if (seat.getLoaiGhe() == Phong.Ghe.LOAI_DA_BAN) {
            bgColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.gray);
            holder.tvSeatName.setText(""); // No text for sold seats
            textColor = Color.TRANSPARENT;
        } else if (seat.isDangChon()) {
            bgColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.green);
            textColor = Color.WHITE;
        } else if (seat.getLoaiGhe() == Phong.Ghe.LOAI_DOI) {
            bgColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.yellow);
            textColor = Color.BLACK;
        } else {
            bgColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.purple);
            textColor = Color.WHITE;
        }

        holder.cardSeat.setCardBackgroundColor(bgColor);
        holder.tvSeatName.setTextColor(textColor);

        holder.itemView.setOnClickListener(v -> {
            if (seat.getLoaiGhe() != Phong.Ghe.LOAI_DA_BAN) {
                listener.onSeatClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seatList.size();
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
