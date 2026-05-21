package com.example.cinestonks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class chon_ghe extends AppCompatActivity {
    ImageView imgPoster, iv_back, iv_next;
    TextView txtMovieName, tv_rap, tv_suat, txtCountGhe, tvTotalMoney, tv_TheLoai, tvTicketName;
    RecyclerView rvSeats;
    SeatAdapter seatAdapter;
    List<Phong.Ghe> seatList;

    private int maxNormal = 0;
    private int maxDouble = 0;
    private int selectedNormal = 0;
    private int selectedDouble = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_ghe);
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
        tvTicketName = findViewById(R.id.tvTicketName);
        txtCountGhe = findViewById(R.id.txtCountGhe);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        rvSeats = findViewById(R.id.btn_ghe);
        
        iv_back.setOnClickListener(v -> finish());
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            tv_rap.setText(intent.getStringExtra("TEN_RAP"));
            txtMovieName.setText(intent.getStringExtra("TEN_PHIM"));
            tv_TheLoai.setText(intent.getStringExtra("THE_LOAI"));
            tv_suat.setText(intent.getStringExtra("SUAT_CHIEU"));
            tvTicketName.setText(intent.getStringExtra("TEN_LOAI_VE"));
            
            String soLuongVe = intent.getStringExtra("SO_LUONG_VE");
            String tongTien = intent.getStringExtra("TONG_TIEN");
            
            txtCountGhe.setText(soLuongVe + " Ghế:");
            tvTotalMoney.setText(tongTien);

            // Lấy giới hạn số lượng từng loại
            maxNormal = intent.getIntExtra("MAX_NORMAL", 0);
            maxDouble = intent.getIntExtra("MAX_DOUBLE", 0);
        }
    }

    private void createSeat() {
        seatList = new ArrayList<>();
        int totalSeats = 80;
        int seatsPerRow = 8;

        for (int i = 0; i < totalSeats; i++) {
            int rowIndex = i / seatsPerRow;
            int colIndex = i % seatsPerRow;
            char rowChar = (char) ('A' + rowIndex);
            String seatName = rowChar + String.valueOf(colIndex + 1);

            int type = Phong.Ghe.LOAI_THUONG;
            if (rowIndex >= 7) type = Phong.Ghe.LOAI_DOI;
            if (i == 10 || i == 15) type = Phong.Ghe.LOAI_DA_BAN;

            seatList.add(new Phong.Ghe(seatName, type));
        }

        seatAdapter = new SeatAdapter(seatList, position -> {
            Phong.Ghe seat = seatList.get(position);
            
            if (seat.getLoaiGhe() == Phong.Ghe.LOAI_DA_BAN) return;

            if (seat.isDangChon()) {
                // Hủy chọn
                seat.setDangChon(false);
                if (seat.getLoaiGhe() == Phong.Ghe.LOAI_DOI) selectedDouble--;
                else selectedNormal--;
                seatAdapter.notifyItemChanged(position);
            } else {
                // Chọn mới - Kiểm tra giới hạn
                if (seat.getLoaiGhe() == Phong.Ghe.LOAI_DOI) {
                    if (selectedDouble < maxDouble) {
                        seat.setDangChon(true);
                        selectedDouble++;
                        seatAdapter.notifyItemChanged(position);
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn " + maxDouble + " ghế đôi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (selectedNormal < maxNormal) {
                        seat.setDangChon(true);
                        selectedNormal++;
                        seatAdapter.notifyItemChanged(position);
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn " + maxNormal + " ghế thường", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        rvSeats.setLayoutManager(new GridLayoutManager(this, seatsPerRow));
        rvSeats.setAdapter(seatAdapter);
    }
}
