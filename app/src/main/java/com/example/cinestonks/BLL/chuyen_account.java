package com.example.cinestonks.BLL;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class chuyen_account extends AppCompatActivity {
    private ImageView iv_back;
    private TextView tvForget, tvRegis;
    private TextInputEditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        initControl();
        BackToPrevious();
    }

    private void initControl() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForget = findViewById(R.id.tvQuenMK);
        tvRegis = findViewById(R.id.tvDangKy);

        // Chuyển sang màn hình Đăng ký
        tvRegis.setOnClickListener(v -> {
            Intent intent = new Intent(chuyen_account.this, DangKy.class);
            startActivity(intent);
        });

        // Xử lý sự kiện nhấn nút Đăng nhập
        btnLogin.setOnClickListener(v -> loginUser());

        // Xử lý quên mật khẩu
        tvForget.setOnClickListener(v -> {
            Toast.makeText(chuyen_account.this, "Chức năng đang được cập nhật", Toast.LENGTH_SHORT).show();
        });
    }

    private void loginUser() {
        String email = etUsername.getText().toString().trim(); // Bây giờ ta dùng email để đăng nhập
        String password = etPassword.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (TextUtils.isEmpty(email)) {
            etUsername.setError("Vui lòng nhập Email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Vui lòng nhập mật khẩu");
            return;
        }

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        usersRef.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    boolean isLoginSuccess = false;
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String dbPassword = userSnapshot.child("MatKhau").getValue(String.class);

                        if (dbPassword != null && dbPassword.equals(password)) {
                            isLoginSuccess = true;

                            String userId = userSnapshot.getKey(); // Lấy ID người dùng

                            Toast.makeText(chuyen_account.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(chuyen_account.this, MainActivity.class);
                            intent.putExtra("USER_ID", userId); // Truyền ID sang MainActivity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }
                    if (!isLoginSuccess) {
                        Toast.makeText(chuyen_account.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(chuyen_account.this, "Tài khoản Email không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(chuyen_account.this, "Lỗi kết nối: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
