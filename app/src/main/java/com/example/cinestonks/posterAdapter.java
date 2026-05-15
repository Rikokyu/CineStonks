package com.example.cinestonks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class posterAdapter extends RecyclerView.Adapter<posterAdapter.PosterViewHolder> {
    private List<Integer> posterList; // Danh sách các R.drawable.poster_path

    public posterAdapter(List<Integer> posterList) {
        this.posterList = posterList;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        holder.imageView.setImageResource(posterList.get(position));
    }

    @Override
    public int getItemCount() {
        return posterList.size();
    }

    static class PosterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgPoster);
        }
    }
}
