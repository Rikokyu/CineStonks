package com.example.cinestonks.BLL;

import android.content.Intent;import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinestonks.R;
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
    String gioiTinh = "Nam"; // Mặc định

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        initControl();
        backToPrevious();
    }

    private void initControl() {
        etHoTen = findViewById(R.id.etFullName);
        etSDT = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etNgaySinh = findViewById(R.id.etNgaySinh);
        etMatKhau = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        btnRegister = findViewById(R.id.btnRegister);

        // Gán sự kiện click duy nhất để thực hiện kiểm tra và đăng ký
        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fullName = etHoTen.getText().toString().trim();
        String phone = etSDT.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String ngaySinh = etNgaySinh.getText().toString().trim();
        String password = etMatKhau.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        int selectedId = rgGioiTinh.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton rbSelected = findViewById(selectedId);
            gioiTinh = rbSelected.getText().toString();
        }

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(ngaySinh) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu nhập lại không khớp");
            return;
        }

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        // KIỂM TRA EMAIL ĐÃ TỒN TẠI HAY CHƯA
        usersRef.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // THÔNG BÁO NẾU EMAIL ĐÃ TỒN TẠI
                    etEmail.setError("Email này đã được đăng ký");
                    Toast.makeText(DangKy.this, "Email này đã tồn tại trên hệ thống!", Toast.LENGTH_LONG).show();
                } else {
                    // TIẾN HÀNH ĐĂNG KÝ NẾU CHƯA CÓ EMAIL
                    String userId = usersRef.push().getKey();

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
                Toast.makeText(DangKy.this, "Lỗi kết nối: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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