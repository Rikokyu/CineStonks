package com.example.cinestonks.BLL;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.adapters.HistoryAdapter;
import com.example.cinestonks.models.HoaDon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LichSuMuaVeActivity extends AppCompatActivity {
    private RecyclerView rvHistory;
    private HistoryAdapter adapter;
    private List<HoaDon> historyList;
    private String userId;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_mua_ve);

        ivBack = findViewById(R.id.ivBack);
        rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        historyList = new ArrayList<>();
        adapter = new HistoryAdapter(historyList);
        rvHistory.setAdapter(adapter);

        userId = getIntent().getStringExtra("USER_ID");

        if (userId != null) {
            loadHistory();
        }

        ivBack.setOnClickListener(v -> finish());
    }

    private void loadHistory() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("HoaDon");
        // Lọc hóa đơn theo MaND
        ref.orderByChild("MaND").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    HoaDon hoaDon = data.getValue(HoaDon.class);
                    if (hoaDon != null) {
                        historyList.add(hoaDon);
                    }
                }
                adapter.notifyDataSetChanged();
                if (historyList.isEmpty()) {
                    Toast.makeText(LichSuMuaVeActivity.this, "Bạn chưa mua vé nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LichSuMuaVeActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
