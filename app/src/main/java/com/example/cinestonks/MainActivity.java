package com.example.cinestonks;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Xử lý Insets (Padding cho hệ thống)
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
                return insets;
            });
        }

        // 2. Khởi tạo ActionBar (Menu dưới)
        View actionBarLayout = findViewById(R.id.layout_actionbar);
        if (actionBarLayout != null) {
            new ActionBar(this, actionBarLayout);
        }

        // 3. Khởi tạo ViewPager2 cho Poster
        setupViewPager();
    }

    private void setupViewPager() {
        ViewPager2 viewPager = findViewById(R.id.viewPagerPosters);

        if (viewPager != null) {
            // Thêm các ảnh từ drawable vào danh sách
            List<Integer> posterList = new ArrayList<>();
            posterList.add(R.drawable.denamhon);
            posterList.add(R.drawable.latmat7);
            posterList.add(R.drawable.nuhon);
            posterList.add(R.drawable.phimmai);
            posterList.add(R.drawable.wolfman);

            // Thiết lập Adapter
            posterAdapter adapter = new posterAdapter(posterList);
            viewPager.setAdapter(adapter);

            // Cấu hình hiệu ứng hiển thị
            CompositePageTransformer transformer = new CompositePageTransformer();
            transformer.addTransformer(new MarginPageTransformer(40));
            transformer.addTransformer((page, position) -> {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.75f + r * 0.15f);
                page.setScaleX(0.75f + r * 0.15f);
            });

            viewPager.setPageTransformer(transformer);
            viewPager.setOffscreenPageLimit(3);
            viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    private void testData() {
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        DatabaseReference myRef = data.getReference("message");
        myRef.setValue("Hello, World!");
    }
}
