package com.example.cinestonks.BLL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.adapters.SeatAdapter;
import com.example.cinestonks.models.Seat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chon_ghe extends AppCompatActivity {
    ImageView imgPoster, iv_back, iv_next;
    TextView txtMovieName, tv_rap, tv_suat, txtCountGhe, tvTotalMoney, tv_TheLoai, tvTicketName, tv_Phong;
    RecyclerView rvSeats;
    SeatAdapter seatAdapter;
    List<Seat> seatList; // Chuyển sang sử dụng model Seat
    String maPhong, maSuat, userId;
    private int maxNormal = 0;
    private int maxDouble = 0;
    private int selectedNormal = 0;
    private int selectedDouble = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_ghe);

        // Nhận dữ liệu từ Intent
        maPhong = getIntent().getStringExtra("MA_PHONG");
        maSuat = getIntent().getStringExtra("MA_SUAT");
        userId = getIntent().getStringExtra("USER_ID");

        initViews();
        getDataFromIntent();
        createSeat();
    }

    private void initViews() {
        imgPoster = findViewById(R.id.imgPoster);
        iv_back = findViewById(R.id.iv_back);
        iv_next = findViewById(R.id.iv_next);
        tv_rap = findViewById(R.id.tv_rap);
        tv_suat = findViewById(R.id.tv_suat);
        txtMovieName = findViewById(R.id.txtMovieName);
        tv_TheLoai = findViewById(R.id.tv_TheLoai);
        tv_Phong = findViewById(R.id.tv_Phong);
        tvTicketName = findViewById(R.id.tvTicketName);
        txtCountGhe = findViewById(R.id.txtCountGhe);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        rvSeats = findViewById(R.id.btn_ghe);

        iv_back.setOnClickListener(v -> finish());

        iv_next.setOnClickListener(v -> {
            if (selectedNormal < maxNormal || selectedDouble < maxDouble) {
                String message = "Vui lòng chọn đủ ";
                if (maxNormal > 0 && selectedNormal < maxNormal) {
                    message += (maxNormal - selectedNormal) + " ghế thường ";
                }
                if (maxDouble > 0 && selectedDouble < maxDouble) {
                    if (maxNormal > 0 && selectedNormal < maxNormal) message += "và ";
                    message += (maxDouble - selectedDouble) + " ghế đôi";
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<String> selectedSeatNames = new ArrayList<>();
            ArrayList<String> selectedSeatIds = new ArrayList<>();
            for (Seat seat : seatList) {
                if (seat.isSelected()) {
                    selectedSeatNames.add(seat.getTenGhe());
                    selectedSeatIds.add(seat.getMaGhe());
                }
            }

            Intent intent = new Intent(chon_ghe.this, thanh_toan.class);
            intent.putExtra("TEN_RAP", tv_rap.getText().toString());
            intent.putExtra("TEN_PHIM", txtMovieName.getText().toString());
            intent.putExtra("THE_LOAI", tv_TheLoai.getText().toString());
            intent.putExtra("TEN_PHONG", tv_Phong.getText().toString());
            intent.putExtra("SUAT_CHIEU", tv_suat.getText().toString());
            intent.putExtra("TONG_TIEN", tvTotalMoney.getText().toString());
            intent.putExtra("SO_LUONG", String.valueOf(selectedSeatNames.size()));
            intent.putStringArrayListExtra("DANH_SACH_GHE", selectedSeatNames);
            intent.putStringArrayListExtra("DANH_SACH_ID_GHE", selectedSeatIds); // Truyền ID ghế sang thanh toán
            intent.putExtra("USER_ID", userId);
            intent.putExtra("MA_SUAT", maSuat);
            startActivity(intent);
        });
    }

    private void getDataFromIntent() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        if (intent != null) {
            tv_rap.setText(intent.getStringExtra("TEN_RAP"));
            txtMovieName.setText(intent.getStringExtra("TEN_PHIM"));
            tv_TheLoai.setText(intent.getStringExtra("THE_LOAI"));
            if (maPhong != null) {
                database.getReference("PhongChieu").child(maPhong).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String phongChieu = snapshot.child("TenPhong").getValue(String.class);
                            if (phongChieu != null) {
                                tv_Phong.setText("Phòng: " + phongChieu);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
            tv_suat.setText(intent.getStringExtra("SUAT_CHIEU"));
            tvTicketName.setText(intent.getStringExtra("TEN_LOAI_VE"));

            String soLuongVe = intent.getStringExtra("SO_LUONG_VE");
            String tongTien = intent.getStringExtra("TONG_TIEN");

            txtCountGhe.setText(soLuongVe + " Ghế:");
            tvTotalMoney.setText(tongTien);

            maxNormal = intent.getIntExtra("MAX_NORMAL", 0);
            maxDouble = intent.getIntExtra("MAX_DOUBLE", 0);
        }
    }

    private void createSeat() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Lọc ghế theo đúng Mã suất chiếu
        database.getReference("Ghe").orderByChild("MaSuatChieu").equalTo(maSuat)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        seatList = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Seat seat = ds.getValue(Seat.class);
                            // KIỂM TRA: Chỉ thêm ghế nếu khớp cả Mã phòng
                            if (seat != null && maPhong != null && maPhong.equals(seat.getMaPhong())) {
                                seatList.add(seat);
                            }
                        }

                        seatAdapter = new SeatAdapter(seatList, position -> {
                            Seat seat = seatList.get(position);
                            // Giả sử trạng thái "1" là ghế đã bán (không cho chọn)
                            if ("1".equals(seat.getTrangThai())) return;

                            if (seat.isSelected()) {
                                seat.setSelected(false);
                                if ("Đôi".equals(seat.getLoaiGhe())) {
                                    selectedDouble--;
                                } else {
                                    selectedNormal--;
                                }
                                seatAdapter.notifyItemChanged(position);
                            } else {
                                // Kiểm tra loại ghế là chuỗi "Đôi" hoặc "Thường"
                                if ("Đôi".equals(seat.getLoaiGhe())) {
                                    if (selectedDouble < maxDouble) {
                                        seat.setSelected(true);
                                        selectedDouble++;
                                        seatAdapter.notifyItemChanged(position);
                                    } else {
                                        Toast.makeText(chon_ghe.this, "Bạn đã chọn đủ số lượng ghế đôi", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    if (selectedNormal < maxNormal) {
                                        seat.setSelected(true);
                                        selectedNormal++;
                                        seatAdapter.notifyItemChanged(position);
                                    } else {
                                        Toast.makeText(chon_ghe.this, "Bạn đã chọn đủ số lượng ghế thường", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

                        rvSeats.setLayoutManager(new GridLayoutManager(chon_ghe.this, 8)); // 8 ghế mỗi hàng
                        rvSeats.setAdapter(seatAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(chon_ghe.this, "Lỗi tải sơ đồ ghế: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
