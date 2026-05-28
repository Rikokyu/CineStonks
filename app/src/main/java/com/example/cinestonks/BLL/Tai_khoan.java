package com.example.cinestonks.BLL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinestonks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tai_khoan extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvHoTen, tvEmail, tvSDT, tvNgaySinh, tvGioiTinh;
    private Button btnLogout, btnViewHistory;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tai_khoan);

        initViews();
        userId = getIntent().getStringExtra("USER_ID");

        if (userId != null) {
            loadUserProfile(userId);
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }

        ivBack.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(Tai_khoan.this, chuyen_account.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnViewHistory.setOnClickListener(v -> {
            if (userId != null) {
                Intent intent = new Intent(Tai_khoan.this, LichSuMuaVeActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Lỗi: Không có mã người dùng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        tvHoTen = findViewById(R.id.tvHoTen);
        tvEmail = findViewById(R.id.tvEmail);
        tvSDT = findViewById(R.id.tvSDT);
        tvNgaySinh = findViewById(R.id.tvNgaySinh);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        btnLogout = findViewById(R.id.btnLogout);
        btnViewHistory = findViewById(R.id.btnViewHistory);
    }

    private void loadUserProfile(String uid) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("TaiKhoan").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String hoTen = snapshot.child("HoTen").getValue(String.class);
                    String email = snapshot.child("Email").getValue(String.class);
                    String sdt = snapshot.child("SDT").getValue(String.class);
                    String ngaySinh = snapshot.child("NgaySinh").getValue(String.class);
                    String gioiTinh = snapshot.child("GioiTinh").getValue(String.class);

                    tvHoTen.setText("Họ và tên: " + (hoTen != null ? hoTen : "N/A"));
                    tvEmail.setText("Email: " + (email != null ? email : "N/A"));
                    tvSDT.setText("Số ĐT: " + (sdt != null ? sdt : "N/A"));
                    tvNgaySinh.setText("Ngày sinh: " + (ngaySinh != null ? ngaySinh : "N/A"));
                    tvGioiTinh.setText("Giới tính: " + (gioiTinh != null ? gioiTinh : "N/A"));
                } else {
                    Toast.makeText(Tai_khoan.this, "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Tai_khoan.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
