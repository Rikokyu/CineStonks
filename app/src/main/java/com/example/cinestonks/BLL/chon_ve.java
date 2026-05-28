package com.example.cinestonks.BLL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.adapters.VeAdapter;
import com.example.cinestonks.models.Ve;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chon_ve extends AppCompatActivity {
    private RecyclerView rvTicket;
    private ImageView iv_back, iv_next;
    private List<Ve> veList;
    private VeAdapter veAdapter;

    private String maRap, maPhim, maSuat, maPhong, userId;
    private TextView tv_rap, tv_phim, tv_theloai, tv_suat, tvCountTicket, tvTotalMoney, tv_Phong, tv_AvailableSeats;
    private int availableSeatsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_ve);

        maRap = getIntent().getStringExtra("MA_RAP");
        maPhim = getIntent().getStringExtra("MA_PHIM");
        maSuat = getIntent().getStringExtra("MA_SUAT");
        maPhong = getIntent().getStringExtra("MA_PHONG");
        userId = getIntent().getStringExtra("USER_ID");

        initViews();
        getTicket();
        getTen();
        calculateAvailableSeats();
        BackToPrevious();
        setupEvents();
    }

    private void calculateAvailableSeats() {
        if (maSuat == null || maPhong == null) return;

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Ghe");

        // Lọc ghế theo mã suất chiếu
        db.orderByChild("MaSuatChieu").equalTo(maSuat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int countAvailable = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String trangThai = ds.child("TrangThai").getValue(String.class);
                    String seatMaPhong = ds.child("MaPhong").getValue(String.class);

                    // Kiểm tra TrangThai == "0" và đúng Mã phòng
                    if ("0".equals(trangThai) && maPhong.equals(seatMaPhong)) {
                        countAvailable++;
                    }
                }

                availableSeatsCount = countAvailable;
                tv_AvailableSeats.setText("Số ghế còn trống: " + availableSeatsCount);

                // Truyền giá trị này vào Adapter làm maxTickets
                if (veAdapter != null) {
                    veAdapter.setMaxTickets(availableSeatsCount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(chon_ve.this, "Lỗi kết nối dữ liệu ghế", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupEvents() {
        iv_next.setOnClickListener(v -> {
            int totalTickets = veAdapter.getTotalTickets();
            if (totalTickets > 0) {
                Intent intent = new Intent(chon_ve.this, chon_ghe.class);

                int countNormal = 0;
                int countDouble = 0;
                StringBuilder ticketDetail = new StringBuilder();

                for (Ve ve : veList) {
                    if (ve.getQuantity() > 0) {
                        if (ticketDetail.length() > 0) ticketDetail.append(", ");
                        ticketDetail.append(ve.getQuantity()).append(" ").append(ve.getTenVe());

                        String tenVe = ve.getTenVe().toLowerCase();
                        if (tenVe.contains("đôi") || tenVe.contains("couple")) {
                            countDouble += ve.getQuantity();
                        } else {
                            countNormal += ve.getQuantity();
                        }
                    }
                }

                intent.putExtra("TEN_RAP", tv_rap.getText().toString());
                intent.putExtra("TEN_PHIM", tv_phim.getText().toString());
                intent.putExtra("THE_LOAI", tv_theloai.getText().toString());
                intent.putExtra("SUAT_CHIEU", tv_suat.getText().toString());
                intent.putExtra("SO_LUONG_VE", String.valueOf(totalTickets));
                intent.putExtra("TONG_TIEN", tvTotalMoney.getText().toString());
                intent.putExtra("TEN_LOAI_VE", ticketDetail.toString());
                intent.putExtra("MA_PHONG", maPhong);
                intent.putExtra("MA_SUAT", maSuat);
                intent.putExtra("USER_ID", userId);

                intent.putExtra("MAX_NORMAL", countNormal);
                intent.putExtra("MAX_DOUBLE", countDouble);

                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng chọn ít nhất một vé", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void BackToPrevious() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> finish());
    }

    private void getTen() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        if (maRap != null) {
            database.getReference("Rap").child(maRap).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String tenRap = snapshot.child("TenRap").getValue(String.class);
                        tv_rap.setText(tenRap);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

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
        if (maPhong != null) {
            database.getReference("PhongChieu").child(maPhong).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String phongChieu = snapshot.child("TenPhong").getValue(String.class);
                        if (phongChieu != null) {
                            tv_Phong.setText("Phòng: " + phongChieu);
                        } else {
                            tv_Phong.setText("Không lấy được phòng");
                        }
                    } else {
                        tv_Phong.setText("Mã phòng " + maPhong + " không tồn tại");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    private void initViews() {
        iv_next = findViewById(R.id.iv_next);
        tv_rap = findViewById(R.id.tv_ten_rap);
        tv_phim = findViewById(R.id.tv_ten_phim);
        tv_theloai = findViewById(R.id.tv_TheLoai);
        tv_suat = findViewById(R.id.tv_suat);
        rvTicket = findViewById(R.id.rvTicketTypes);
        tvCountTicket = findViewById(R.id.tvTotalCount);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        tv_Phong = findViewById(R.id.tv_Phong);
        tv_AvailableSeats = findViewById(R.id.tv_AvailableSeats);

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
