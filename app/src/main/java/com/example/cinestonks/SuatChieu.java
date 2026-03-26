package com.example.cinestonks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SuatChieu extends AppCompatActivity {
    // 1. Khởi tạo Database với URL từ hình ảnh của bạn
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://cinestonks-16fc4-default-rdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("Phim");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suat_chieu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.suat_chieu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Initialize ActionBar logic
        View actionBarLayout = findViewById(R.id.action_bar_up);
        if (actionBarLayout != null) {
            new ActionBar(this, actionBarLayout);
        }
//        View itemSuatChieuLayout = findViewById(R.id.item_suat_chieu);
//        if (itemSuatChieuLayout != null) {
//            new ItemSuatChieu(this, );
//        }
    }

    private void testData() {
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        DatabaseReference myRef = data.getReference("message");
        myRef.setValue("Hello, World!");
    }
// Read from the database
//    myRef.addValueEventListener(object: ValueEventListener {
//
//            override fun onDataChange(snapshot:DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = snapshot.getValue<String>()
//                Log.d(TAG, "Value is: " + value)
//            }
//
//            override fun onCancelled(error:DatabaseError) {
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//
//        })
    private void initView()
    {
//        LinearLayout lnMain = findViewById(R.id.suat_chieu);
//        lnMain.removeAllViews();
//        for(int i=0; i<ID_DRAWABLES.length; i++){
//            View v = LayoutInflater.from(this).inflate(R.layout.item_suat_chieu, null);
//            ImageView ivTopic = v.findViewById(R.id.iv_topic);
//            TextView tvTopic = v.findViewById(R.id.tv_topic);
//            ivTopic.setImageResource(ID_DRAWABLES[i]);
//            tvTopic.setText(ID_TEXTS[i]);
//
//            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    1.0f
//            );
//            v.setLayoutParams(param);
//            lnMain.addView(v);
        }

}