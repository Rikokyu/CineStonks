package com.example.cinestonks.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cinestonks.R;
import com.example.cinestonks.models.Movie;
import com.example.cinestonks.models.SuatChieu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Map<String, List<SuatChieu>> suatChieuMap;

    public interface OnSuatChieuClickListener {
        void onSuatChieuClick(SuatChieu suatChieu);
    }

    private OnSuatChieuClickListener listener;

    public MovieAdapter(List<Movie> movieList, Map<String, List<SuatChieu>> suatChieuMap, OnSuatChieuClickListener listener) {
        this.movieList = movieList;
        this.suatChieuMap = suatChieuMap;
        this.listener = listener;
    }
    private List<Movie> movieList;
    public class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView tvMovieTitle, tvMovieType, tvMovieAge;
        RecyclerView rvSuat;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvMovieType = itemView.findViewById(R.id.tvMovieType);
            tvMovieAge = itemView.findViewById(R.id.tvMovieAge);
            rvSuat = itemView.findViewById(R.id.rvScreeningHours);
        }
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.tvMovieTitle.setText(movie.getTenPhim());
        holder.tvMovieType.setText(movie.getTheLoai());
        holder.tvMovieAge.setText(String.valueOf(movie.getDoTuoi()));

        // 1. LẤY DỮ LIỆU NGAY TỪ ĐẦU
        List<SuatChieu> list = new ArrayList<>();
        if (suatChieuMap != null && movie.getMaPhim() != null) {
            list = suatChieuMap.get(movie.getMaPhim());
        }

        // Kiểm tra để tránh lỗi NullPointerException nếu phim đó chưa có suất chiếu
        if (list == null) list = new ArrayList<>();

        // Log để debug
        Log.d("DEBUG", "Movie: " + movie.getTenPhim() +
                " | MaPhim: " + movie.getMaPhim() +
                " | So suat: " + list.size());

        // 2. KHỞI TẠO ADAPTER VỚI LISTENER (CHỈ 1 LẦN)
        // Đảm bảo ScreeningAdapter của bạn có Constructor nhận (List, Listener)
        ScreeningAdapter adapter = new ScreeningAdapter(list, new ScreeningAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SuatChieu suatChieu) {
                if (listener != null) {
                    listener.onSuatChieuClick(suatChieu);
                }
            }
        });

        // 3. CÀI ĐẶT RECYCLERVIEW CON (Dùng biến rvSuat đã khai báo trong ViewHolder)
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.itemView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        holder.rvSuat.setLayoutManager(layoutManager);

        // Tránh thêm gạch phân cách nhiều lần khi cuộn
        if (holder.rvSuat.getItemDecorationCount() == 0) {
            holder.rvSuat.addItemDecoration(
                    new DividerItemDecoration(holder.itemView.getContext(), DividerItemDecoration.HORIZONTAL)
            );
        }

        holder.rvSuat.setAdapter(adapter);

        // Tối ưu hóa hiệu năng
        holder.rvSuat.setHasFixedSize(true);
        holder.rvSuat.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return (movieList != null) ? movieList.size():0;
    }
}