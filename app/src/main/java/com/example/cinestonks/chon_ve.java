package com.example.cinestonks;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

public class chon_ve extends AppCompatActivity {
    private RecyclerView rvTicket;
    private ImageView iv_back;
    private List<Ve> veList;
    private VeAdapter veAdapter;

    // Đổi tên biến để tránh nhầm lẫn với TextView
    private String maRap, maPhim, maSuat;
    private TextView tv_rap, tv_phim, tv_theloai, tv_suat, tvCountTicket, tvTotalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_ve);

        // Lấy mã ID từ Intent
        maRap = getIntent().getStringExtra("MA_RAP");
        maPhim = getIntent().getStringExtra("MA_PHIM");
        maSuat = getIntent().getStringExtra("MA_SUAT");
        initViews();
        getTicket();
        getTen();
        BackToPrevious();
    }

    public void BackToPrevious() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> finish());
    }

    private void getTen() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // 1. Lấy tên Rạp (Trỏ trực tiếp vào maRap)
        if (maRap != null) {
            database.getReference("Rap").child(maRap).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Lưu ý: Kiểm tra chính xác tên field trên Firebase (TenRap hay tenRap)
                        String tenRap = snapshot.child("TenRap").getValue(String.class);
                        tv_rap.setText(tenRap);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

        // 2. Lấy tên Phim và Thể loại (Trỏ trực tiếp vào maPhim)
        if (maPhim != null) {
            database.getReference("Phim").child(maPhim).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String tenPhim = snapshot.child("TenPhim").getValue(String.class);
                        String theLoai = snapshot.child("TheLoai").getValue(String.class);
                        tv_phim.setText(tenPhim);
                        tv_theloai.setText(theLoai);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

        // 3. Lấy giờ chiếu (Trỏ trực tiếp vào maSuat)
        if (maSuat != null) {
            database.getReference("SuatChieu").child(maSuat).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String gioChieu = snapshot.child("GioChieu").getValue(String.class);

                        String ngayChieu = snapshot.child("NgayChieu").getValue(String.class);

                        if (gioChieu != null) {
                            tv_suat.setText(gioChieu + " - " + ngayChieu);
                        } else {
                            tv_suat.setText("Không lấy được giờ");
                        }
                    } else {
                        tv_suat.setText("Mã suất " + maSuat + " không tồn tại");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    private void initViews() {
        tv_rap = findViewById(R.id.tv_ten_rap);
        tv_phim = findViewById(R.id.tv_ten_phim);
        tv_theloai = findViewById(R.id.tv_TheLoai);
        tv_suat = findViewById(R.id.tv_suat);
        rvTicket = findViewById(R.id.rvTicketTypes);
        tvCountTicket = findViewById(R.id.tvTotalCount);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);

        veList = new ArrayList<>();
        veAdapter = new VeAdapter(veList, new VeAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged() {
                updateTotal();
            }
        });

        rvTicket.setLayoutManager(new LinearLayoutManager(this));
        rvTicket.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvTicket.setAdapter(veAdapter);
    }
    private void updateTotal() {
        long totalMoney = veAdapter.getTotalPrice();
        int totalCount = veAdapter.getTotalTickets();

        tvTotalMoney.setText(String.format("%,d VNĐ", totalMoney));
        tvCountTicket.setText(String.valueOf(totalCount));
    }

    private void getTicket() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ve");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                veList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Ve ve = dataSnapshot.getValue(Ve.class);
                    if (ve != null) {
                        veList.add(ve);
                    }
                }
                veAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(chon_ve.this, "Lỗi tải loại vé: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}