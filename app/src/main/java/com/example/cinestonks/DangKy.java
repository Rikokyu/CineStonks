package com.example.cinestonks;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private TextInputEditText etFullName, etPhone, etEmail, etUsername, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initControl();
        BackToPrevious();
    }

    private void initControl() {
        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
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
        String fullName = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Kiểm tra dữ liệu trống
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu khớp
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu nhập lại không khớp");
            return;
        }

        // 1. Đổi node gốc thành "TaiKhoan" theo hình image_931c18.png
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        // 2. Kiểm tra xem Email đã tồn tại chưa (Vì CSDL của bạn dùng Email)
        usersRef.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    etEmail.setError("Email này đã được đăng ký");
                } else {
                    // Tạo ID mới (Ví dụ: ND001, ND002... hoặc dùng push key)
                    // Ở đây dùng push().getKey() để đảm bảo không trùng lặp
                    String userId = usersRef.push().getKey();

                    // 3. Sửa lại Map để khớp chính xác với các trường trong hình
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("HoTen", fullName);     // Thay cho fullName
                    userMap.put("SDT", phone);         // Thay cho phone
                    userMap.put("Email", email);       // Giữ nguyên hoặc viết hoa chữ E
                    userMap.put("MatKhau", password);   // Thay cho password

                    // Các trường bổ sung có trong hình image_931c18.png
                    userMap.put("GioiTinh", "Chưa xác định");
                    userMap.put("NgaySinh", "1990-01-01");
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

    public void BackToPrevious() {
        iv_back = findViewById(R.id.iv_back);
        if (iv_back != null) {
            iv_back.setOnClickListener(v -> finish());
        }
    }
}
