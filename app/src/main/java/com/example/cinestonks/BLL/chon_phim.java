package com.example.cinestonks.BLL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinestonks.R;
import com.example.cinestonks.adapters.DateAdapter;
import com.example.cinestonks.adapters.MovieAdapter;
import com.example.cinestonks.models.Date;
import com.example.cinestonks.models.Movie;
import com.example.cinestonks.models.SuatChieu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class chon_phim extends AppCompatActivity {

    private TextView tvDisplayId;
    private RecyclerView rvDates, rvMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private Map<String, List<SuatChieu>> suatChieuMap = new HashMap<>();
    private String receivedId;
    private String receivedName;
    private String selectedDate;
    private String userId; // Thêm biến lưu userId
    private Set<String> currentMaPhongSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_phim);

        receivedName = getIntent().getStringExtra("SELECTED_NAME");
        receivedId = getIntent().getStringExtra("MA_RAP");
        userId = getIntent().getStringExtra("USER_ID"); // Nhận userId

        // Mặc định lấy ngày hôm nay
        selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());

        initViews();

        tvDisplayId.setText(receivedName != null ? receivedName : "Không nhận được tên rạp!");

        displayDateList();
        
        // Bắt đầu luồng xử lý: Rạp -> Phòng -> Suất chiếu -> Phim
        loadDataFlow();
        
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvDisplayId = findViewById(R.id.tv_display_id);
        rvDates = findViewById(R.id.rvDates);
        rvMovies = findViewById(R.id.rvMovies);

        movieList = new ArrayList<>();

        movieAdapter = new MovieAdapter(movieList, suatChieuMap, new MovieAdapter.OnSuatChieuClickListener() {
            @Override
            public void onSuatChieuClick(SuatChieu suatChieu) {
                Intent intent = new Intent(chon_phim.this, chon_ve.class);
                intent.putExtra("MA_RAP", receivedId);
                intent.putExtra("MA_PHIM", suatChieu.getMaPhim());
                intent.putExtra("MA_SUAT", suatChieu.getMaSuat());
                intent.putExtra("MA_PHONG", suatChieu.getMaPhong());
                intent.putExtra("USER_ID", userId); // Truyền tiếp userId
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvMovies.addItemDecoration(decoration);

        rvMovies.setAdapter(movieAdapter);
    }

    private void loadDataFlow() {
        if (receivedId == null) {
            Toast.makeText(this, "Mã rạp không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("PhongChieu").orderByChild("MaRap").equalTo(receivedId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            clearData();
                            Toast.makeText(chon_phim.this, "Rạp này chưa có phòng chiếu!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        currentMaPhongSet.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            currentMaPhongSet.add(ds.getKey());
                        }
                        loadSuatChieuByPhongs();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private void loadSuatChieuByPhongs() {
        if (currentMaPhongSet.isEmpty()) return;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SuatChieu");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                suatChieuMap.clear();
                Set<String> maPhimSet = new HashSet<>();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    SuatChieu sc = ds.getValue(SuatChieu.class);

                    // Lọc theo Rạp (thông qua mã phòng) và Ngày đã chọn
                    if (sc != null && currentMaPhongSet.contains(sc.getMaPhong())
                            && selectedDate.equals(sc.getNgayChieu())) {

                        sc.setMaSuat(ds.getKey());
                        String maPhim = sc.getMaPhim();

                        if (maPhim != null) {
                            if (!suatChieuMap.containsKey(maPhim)) {
                                suatChieuMap.put(maPhim, new ArrayList<>());
                            }
                            suatChieuMap.get(maPhim).add(sc);
                            maPhimSet.add(maPhim);
                        }
                    }
                }

                if (!maPhimSet.isEmpty()) {
                    loadMoviesByIds(maPhimSet);
                } else {
                    clearData();
                    Toast.makeText(chon_phim.this, "Không có suất chiếu trong ngày " + selectedDate, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadMoviesByIds(Set<String> maPhimSet) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Phim");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movieList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (maPhimSet.contains(ds.getKey())) {
                        Movie movie = ds.getValue(Movie.class);
                        if (movie != null) {
                            movie.setMaPhim(ds.getKey());
                            movieList.add(movie);
                        }
                    }
                }
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void clearData() {
        movieList.clear();
        suatChieuMap.clear();
        movieAdapter.notifyDataSetChanged();
    }

    private void displayDateList() {
        List<Date> autoDateList = generateAutoDates();
        rvDates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        
        DateAdapter adapter = new DateAdapter(autoDateList, date -> {
            selectedDate = date.getFullDate();
            loadSuatChieuByPhongs();
        });
        
        rvDates.setAdapter(adapter);
    }

    private List<Date> generateAutoDates() {
        List<Date> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("vi", "VN"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM", Locale.getDefault());
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        for (int i = 0; i < 10; i++) {
            String dayOfWeek = (i == 0) ? "Hôm nay" : dayFormat.format(calendar.getTime());
            if (i != 0) {
                dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
            }
            String dateValue = dateFormat.format(calendar.getTime());
            String fullDate = fullDateFormat.format(calendar.getTime());
            
            list.add(new Date(dayOfWeek, dateValue, fullDate, i == 0));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return list;
    }
}
