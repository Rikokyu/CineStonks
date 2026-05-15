package com.example.cinestonks; // Đảm bảo đúng package của bạn

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DangKy extends AppCompatActivity {
    private ImageView iv_back;
    private TextInputEditText etHoTen, etSDT, etEmail, etNgaySinh, etMatKhau, etConfirmPassword;
    private RadioGroup rgGioiTinh;
    private Button btnRegister;
    private TextView tvLogin;
    String gioiTinh = "Nam"; // Mặc định
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register); // Đảm bảo file XML này đã có RadioGroup và ID đúng

        initControl();
        backToPrevious();
    }

    private void initControl() {
        // Ánh xạ View đúng theo cấu trúc CSDL TaiKhoan
        etHoTen = findViewById(R.id.etFullName); // ID trong XML có thể giữ nguyên nhưng biến nên đổi cho dễ nhớ
        etSDT = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etNgaySinh = findViewById(R.id.etNgaySinh); // Bạn nên đổi ID XML này thành etNgaySinh cho đúng nghĩa
        etMatKhau = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rgGioiTinh = findViewById(R.id.rgGioiTinh); // Cần thêm vào XML

        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> registerUser());

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(DangKy.this, chuyen_account.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String fullName = etHoTen.getText().toString().trim();
        String phone = etSDT.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String ngaySinh = etNgaySinh.getText().toString().trim();
        String password = etMatKhau.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Lấy giới tính từ RadioGroup
        int selectedId = rgGioiTinh.getCheckedRadioButtonId();

        if (selectedId != -1) {
            RadioButton rbSelected = findViewById(selectedId);
            gioiTinh = rbSelected.getText().toString();
        }

        // Kiểm tra dữ liệu trống
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(ngaySinh) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu khớp
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu nhập lại không khớp");
            return;
        }

        // Kết nối tới node "TaiKhoan"
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        // Kiểm tra Email trùng lặp
        usersRef.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    etEmail.setError("Email này đã được đăng ký");
                } else {
                    // Tạo ID tự động (ND001, ...) hoặc dùng push().getKey()
                    String userId = usersRef.push().getKey();

                    // Map dữ liệu khớp 100% với hình image_931c18.png
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("HoTen", fullName);
                    userMap.put("SDT", phone);
                    userMap.put("Email", email);
                    userMap.put("MatKhau", password);
                    userMap.put("GioiTinh", gioiTinh);
                    userMap.put("NgaySinh", ngaySinh);
                    userMap.put("TrangThai", "Active");

                    if (userId != null) {
                        usersRef.child(userId).setValue(userMap).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(DangKy.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DangKy.this, chuyen_account.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(DangKy.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DangKy.this, "Lỗi hệ thống: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backToPrevious() {
        iv_back = findViewById(R.id.iv_back);
        if (iv_back != null) {
            iv_back.setOnClickListener(v -> finish());
        }
    }
}