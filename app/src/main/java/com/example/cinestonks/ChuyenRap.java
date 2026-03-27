package com.example.cinestonks;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChuyenRap extends AppCompatActivity {
    // Không cần tvName, tvAddress ở đây nữa vì nó nằm trong từng Item của Adapter
    private RecyclerView rvRap;
    private CinemaAdapter adapter;
    private List<Rap> mListRap;

    TextView con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // LƯU Ý: Layout này phải chứa RecyclerView (ví dụ: activity_main hoặc activity_chuyen_rap)
        setContentView(R.layout.list_cinema);
        initView();
        loadData();
    }

    private void initView() {
        rvRap = findViewById(R.id.rv_rap); // ID này phải trùng với ID trong XML
        mListRap = new ArrayList<>();
        adapter = new CinemaAdapter(mListRap);

        // Thiết lập RecyclerView
        rvRap.setLayoutManager(new LinearLayoutManager(this));
        rvRap.setAdapter(adapter);
    }

    private void loadData() {
        FirebaseHelper.getRap(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListRap != null) {
                    mListRap.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Rap rap = data.getValue(Rap.class);
                        if (rap != null) {
                            mListRap.add(rap);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChuyenRap.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}