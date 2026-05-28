package com.example.cinestonks.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.models.Ve;

import java.util.List;

public class VeAdapter extends RecyclerView.Adapter<VeAdapter.ViewHolder> {
    private List<Ve> list;
    private OnQuantityChangeListener listener;
    private int maxTickets = 999; // Mặc định không giới hạn

    public interface OnQuantityChangeListener {
        void onQuantityChanged(); 
    }

    public VeAdapter(List<Ve> list, OnQuantityChangeListener listener) {
        this.list = list;
        this.listener = listener;
    }

    // Phương thức để thiết lập số lượng vé tối đa có thể chọn
    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
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
        holder.tvQuantity.setText(String.valueOf(ve.getQuantity()));

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQty = ve.getQuantity();
                if (currentQty > 0) {
                    ve.setQuantity(currentQty - 1);
                    holder.tvQuantity.setText(String.valueOf(ve.getQuantity()));
                    if (listener != null) listener.onQuantityChanged();
                }
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra tổng số vé đã chọn so với số ghế còn trống
                if (getTotalTickets() >= maxTickets) {
                    Toast.makeText(v.getContext(), "Không thể chọn thêm. Đã đạt giới hạn ghế trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int currentQty = ve.getQuantity();
                ve.setQuantity(currentQty + 1);
                holder.tvQuantity.setText(String.valueOf(ve.getQuantity()));
                if (listener != null) listener.onQuantityChanged();
            }
        });
    }

    public long getTotalPrice() {
        long total = 0;
        for (Ve v : list) total += (long) v.getGiaTien() * v.getQuantity();
        return total;
    }

    public int getTotalTickets() {
        int count = 0;
        for (Ve v : list) count += v.getQuantity();
        return count;
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTicketName, tvTicketPrice, tvQuantity;
        Button btnMinus, btnPlus;
        ViewHolder(View v) {
            super(v);
            tvTicketName = v.findViewById(R.id.tvTicketName);
            tvTicketPrice = v.findViewById(R.id.tvTicketPrice);
            tvQuantity = v.findViewById(R.id.tvQuantity);
            btnMinus = v.findViewById(R.id.btnMinus);
            btnPlus = v.findViewById(R.id.btnPlus);
        }
    }
}
