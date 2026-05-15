package com.example.cinestonks;

import android.os.Bundle;
import android.text.style.BulletSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QRPay extends AppCompatActivity {
    TextView all_costs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_for_pay);
    }
    public void initView(){
        all_costs = findViewById(R.id.tv_all_costs);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            all_costs.setText(extras.getString("cost"));
        }
    }


}
