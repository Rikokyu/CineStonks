package com.example.cinestonks.BLL;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.supports.FirebaseHelper;
import com.example.cinestonks.R;
import com.example.cinestonks.adapters.CinemaAdapter;
import com.example.cinestonks.models.Rap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChuyenRap extends AppCompatActivity {
    private RecyclerView rvRap;
    private CinemaAdapter adapter;
    private List<Rap> mListRap;
    private ImageView iv_back;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cinema);
        
        userId = getIntent().getStringExtra("USER_ID"); // Nhận userId từ ActionBar
        
        initView();
        loadData();
        BackToPrevious();
    }

    public void BackToPrevious() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> finish());
    }

    private void initView() {
        rvRap = findViewById(R.id.rv_rap);
        mListRap = new ArrayList<>();
        adapter = new CinemaAdapter(mListRap, userId); // Truyền userId vào Adapter

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
                            rap.setId(data.getKey());
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
