package com.example.cinestonks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VeAdapter extends RecyclerView.Adapter<VeAdapter.ViewHolder> {
    private List<Ve> list;
    public VeAdapter(List<Ve> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new VeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ve ve = list.get(position);
        holder.tvTicketName.setText(ve.getTenVe());
        holder.tvTicketPrice.setText(String.valueOf(ve.getGiaTien()));
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTicketName, tvTicketPrice;
        ViewHolder(View v) {
            super(v);
            tvTicketName = v.findViewById(R.id.tvTicketName);
            tvTicketPrice = v.findViewById(R.id.tvTicketPrice);
        }
    }
}
