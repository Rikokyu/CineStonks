package com.example.cinestonks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class chon_phim extends AppCompatActivity {

    private TextView tvDisplayId;
    private RecyclerView rvDates, rvMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private Map<String, List<SuatChieu>> suatChieuMap = new HashMap<>();
    private String receivedId;
    private String receivedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_phim);

        receivedName = getIntent().getStringExtra("SELECTED_NAME");
        receivedId = getIntent().getStringExtra("MA_RAP");

        initViews();

        tvDisplayId.setText(receivedName != null ? receivedName : "Không nhận được tên rạp!");

        displayDateList();
        getListFilm();
        getListSuatChieu();
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

                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvMovies.addItemDecoration(decoration);

        rvMovies.setAdapter(movieAdapter);
    }
    private void getListSuatChieu(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SuatChieu");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                suatChieuMap.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SuatChieu sc = dataSnapshot.getValue(SuatChieu.class);
                    if (sc == null) continue;

                    sc.setMaSuat(dataSnapshot.getKey());

                    String maPhim = sc.getMaPhim();

                    if (maPhim == null) continue;

                    if (!suatChieuMap.containsKey(maPhim)) {
                        suatChieuMap.put(maPhim, new ArrayList<>());
                    }
                    suatChieuMap.get(maPhim).add(sc);
                }
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(chon_phim.this, "Loi SuatChieu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListFilm(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Phim");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movieList.clear(); // 🔥 tránh trùng

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Movie movie = dataSnapshot.getValue(Movie.class);

                    if(movie != null) {
                        movie.setMaPhim(dataSnapshot.getKey()); // 🔥 QUAN TRỌNG
                        movieList.add(movie);
                    }
                }

                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(chon_phim.this, "Loi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void displayDateList() {
        List<Date> autoDateList = generateAutoDates();
        rvDates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Truyền 'this' (Context) vào DateAdapter để nó có thể gọi loadMoviesByDate
        DateAdapter adapter = new DateAdapter(autoDateList);
        rvDates.setAdapter(adapter);
    }

    private List<Date> generateAutoDates() {
        List<Date> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("vi", "VN"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());

        for (int i = 0; i < 10; i++) {
            String dayOfWeek = (i == 0) ? "Hôm nay" : dayFormat.format(calendar.getTime());
            if (i != 0) {
                dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
            }
            String dateValue = dateFormat.format(calendar.getTime());
            list.add(new Date(dayOfWeek, dateValue, i == 0));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return list;
    }
}