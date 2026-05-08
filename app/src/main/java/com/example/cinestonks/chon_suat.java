package com.example.cinestonks;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chon_suat extends AppCompatActivity {
    private RecyclerView rvScreeningHours;
    private ScreeningAdapter screeningAdapter;
    private List<SuatChieu> suatChieuList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_film);

    }

    public void init(){
        rvScreeningHours = findViewById(R.id.rvScreeningHours);
        suatChieuList = new ArrayList<>();
        screeningAdapter = new ScreeningAdapter(suatChieuList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvScreeningHours.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        rvScreeningHours.addItemDecoration(decoration);

        rvScreeningHours.setAdapter(screeningAdapter);
    }

    public void getSuat(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SuatChieu");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SuatChieu suatChieu = dataSnapshot.getValue(SuatChieu.class);
                    if (suatChieu != null)
                    {
                        suatChieuList.add(suatChieu);
                    }
                    screeningAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(chon_suat.this, "Lỗi" + error.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }
}
