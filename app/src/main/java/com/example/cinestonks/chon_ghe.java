package com.example.cinestonks;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class chon_ghe extends AppCompatActivity {
    ImageView imgPoster, iv_back, iv_next;
    TextView txtMovieName, txtCinema, txtFullShowtime, txtCountGhe, txtTotalAmount;
    RecyclerView rvSeats;
    SeatAdapter seatAdapter;
    List<Seat> seatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_ghe);
        initViews();
        createSeat();
    }

    private void initViews() {
        imgPoster = findViewById(R.id.imgPoster);
        iv_back = findViewById(R.id.iv_back);
        iv_next = findViewById(R.id.iv_next);
        txtCinema = findViewById(R.id.txtCinema);
        txtFullShowtime = findViewById(R.id.txtFullShowtime);
        txtMovieName = findViewById(R.id.txtMovieName);
        txtCountGhe = findViewById(R.id.txtCountGhe);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        rvSeats = findViewById(R.id.btn_ghe);
        
        iv_back.setOnClickListener(v -> finish());
    }

    private void createSeat() {
        seatList = new ArrayList<>();
        int totalSeats = 100;
        int seatsPerRow = 8;

        for (int i = 0; i < totalSeats; i++) {
            int rowIndex = i / seatsPerRow;
            int colIndex = i % seatsPerRow;
            
            char rowChar = (char) ('A' + rowIndex);
            String seatName = rowChar + String.valueOf(colIndex + 1);

            int type = Seat.TYPE_NORMAL;
            
            // Hàng dưới cùng là ghế đôi
            int totalRows = (int) Math.ceil((double) totalSeats / seatsPerRow);
            if (rowIndex == totalRows - 1) {
                type = Seat.TYPE_DOUBLE;
            }

            // Giả lập một số ghế đã bán (màu xám) để test điều kiện không có text
            if (i == 10 || i == 11 || i == 22 || i == 23) {
                type = Seat.TYPE_SOLD;
            }

            seatList.add(new Seat(seatName, type));
        }

        seatAdapter = new SeatAdapter(seatList, position -> {
            Seat seat = seatList.get(position);
            if (seat.getType() != Seat.TYPE_SOLD) {
                seat.setSelected(!seat.isSelected());
                seatAdapter.notifyItemChanged(position);
                updateSelectedInfo();
            }
        });

        rvSeats.setLayoutManager(new GridLayoutManager(this, seatsPerRow));
        rvSeats.setAdapter(seatAdapter);
    }

    private void updateSelectedInfo() {
        int count = 0;
        int total = 0;
        for (Seat seat : seatList) {
            if (seat.isSelected()) {
                count++;
                // Giả định giá: Ghế thường 60k, Ghế đôi 120k
                total += (seat.getType() == Seat.TYPE_DOUBLE) ? 120000 : 60000;
            }
        }
        txtCountGhe.setText(count + " Ghế:");
        txtTotalAmount.setText("Tổng cộng: " + String.format("%,d", total) + "đ");
    }
}
