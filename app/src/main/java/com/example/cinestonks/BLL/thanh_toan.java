package com.example.cinestonks.BLL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinestonks.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class thanh_toan extends AppCompatActivity {
    private ImageView iv_back;
    private TextView tv_ten_rap, tv_ten_phim, tv_TheLoai, tv_phong, tv_suat;
    private TextView tv_danh_sach_ghe, tv_so_luong, tv_tong_tien, tv_con_lai;
    private Button btnPayment;
    private ArrayList<String> selectedSeats;
    private ArrayList<String> selectedSeatIds; // Thêm list ID ghế
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanh_toan);

        initViews();
        getDataFromIntent();
    }

    private void initViews() {
        iv_back = findViewById(R.id.iv_back);
        tv_ten_rap = findViewById(R.id.tv_ten_rap);
        tv_ten_phim = findViewById(R.id.tv_ten_phim);
        tv_TheLoai = findViewById(R.id.tv_TheLoai);
        tv_phong = findViewById(R.id.tv_phong);
        tv_suat = findViewById(R.id.tv_suat);
        
        tv_danh_sach_ghe = findViewById(R.id.tv_danh_sach_ghe);
        tv_so_luong = findViewById(R.id.tv_so_luong);
        tv_tong_tien = findViewById(R.id.tv_tong_tien);
        tv_con_lai = findViewById(R.id.tv_con_lai);
        
        btnPayment = findViewById(R.id.btnPayment);

        iv_back.setOnClickListener(v -> finish());

        btnPayment.setOnClickListener(v -> saveInvoiceToFirebase());
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra("USER_ID"); 
            tv_ten_rap.setText(intent.getStringExtra("TEN_RAP"));
            tv_ten_phim.setText(intent.getStringExtra("TEN_PHIM"));
            tv_TheLoai.setText(intent.getStringExtra("THE_LOAI"));
            tv_phong.setText(intent.getStringExtra("TEN_PHONG"));
            tv_suat.setText(intent.getStringExtra("SUAT_CHIEU"));
            
            String soLuong = intent.getStringExtra("SO_LUONG");
            String tongTien = intent.getStringExtra("TONG_TIEN");
            
            if (soLuong != null) tv_so_luong.setText(soLuong + " ghế");
            if (tongTien != null) {
                tv_tong_tien.setText(tongTien);
                tv_con_lai.setText(tongTien);
            }

            selectedSeats = intent.getStringArrayListExtra("DANH_SACH_GHE");
            selectedSeatIds = intent.getStringArrayListExtra("DANH_SACH_ID_GHE"); // Nhận danh sách ID ghế

            if (selectedSeats != null && !selectedSeats.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < selectedSeats.size(); i++) {
                    sb.append(selectedSeats.get(i));
                    if (i < selectedSeats.size() - 1) sb.append(", ");
                }
                tv_danh_sach_ghe.setText(sb.toString());
            }
        }
    }

    private void saveInvoiceToFirebase() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        String invoiceId = dbRef.child("HoaDon").push().getKey();

        String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

        Map<String, Object> invoice = new HashMap<>();
        invoice.put("MaHoaDon", invoiceId);
        invoice.put("MaND", userId);
        invoice.put("TenRap", tv_ten_rap.getText().toString());
        invoice.put("TenPhim", tv_ten_phim.getText().toString());
        invoice.put("TheLoai", tv_TheLoai.getText().toString());
        invoice.put("PhongChieu", tv_phong.getText().toString());
        invoice.put("SuatChieu", tv_suat.getText().toString());
        invoice.put("DanhSachGhe", selectedSeats);
        invoice.put("TongTien", tv_tong_tien.getText().toString());
        invoice.put("NgayThanhToan", currentTime);
        invoice.put("TrangThai", "Đã thanh toán");

        if (invoiceId != null) {
            dbRef.child("HoaDon").child(invoiceId).setValue(invoice).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Cập nhật trạng thái ghế sau khi lưu hóa đơn thành công
                    updateSeatsStatus();
                } else {
                    Toast.makeText(this, "Lỗi khi lưu hóa đơn: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateSeatsStatus() {
        if (selectedSeatIds == null || selectedSeatIds.isEmpty()) {
            finishPayment();
            return;
        }

        DatabaseReference gheRef = FirebaseDatabase.getInstance().getReference("Ghe");
        int totalSeats = selectedSeatIds.size();
        final int[] updatedCount = {0};

        for (String seatId : selectedSeatIds) {
            gheRef.child(seatId).child("TrangThai").setValue("1").addOnCompleteListener(task -> {
                updatedCount[0]++;
                if (updatedCount[0] == totalSeats) {
                    // Khi tất cả ghế đã được cập nhật
                    Toast.makeText(this, "Thanh toán và cập nhật ghế thành công!", Toast.LENGTH_SHORT).show();
                    finishPayment();
                }
            });
        }
    }

    private void finishPayment() {
        Intent intent = new Intent(thanh_toan.this, MainActivity.class);
        intent.putExtra("USER_ID", userId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
