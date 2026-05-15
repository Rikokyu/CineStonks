package com.example.cinestonks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HoaDon extends AppCompatActivity {
    TextView quantity, sum_tickets, sum_snacks, all_sum, discount, change;
    CheckBox bidv, vcb, momo;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoa_don);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hoa_don), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        initView();
        calculateTotal();
        // Initialize ActionBar logic
        View actionBarLayout = findViewById(R.id.layout_actionbar);
        if (actionBarLayout != null) {
            new ActionBar(this, actionBarLayout);
        }
//        View itemSuatChieuLayout = findViewById(R.id.item_suat_chieu);
//        if (itemSuatChieuLayout != null) {
//            new ItemSuatChieu(this, );
//        }
    }

    private void initView(){
        quantity = findViewById(R.id.txt_quantity);
        sum_tickets = findViewById(R.id.txt_sum_tickets);
        sum_snacks = findViewById(R.id.txt_sum_snacks);
        all_sum = findViewById(R.id.txt_all_sum);
        discount = findViewById(R.id.txt_discount);
        change = findViewById(R.id.txt_change);

        bidv = findViewById(R.id.chb_bidv);
        vcb = findViewById(R.id.chb_vcb);
        momo = findViewById(R.id.chb_momo);

        pay = findViewById(R.id.btn_pay);

    }
    private void calculateTotal(){
        try {
            int ticketVal = Integer.parseInt(sum_tickets.getText().toString());
            int snackVal = Integer.parseInt(sum_snacks.getText().toString());
            int discountVal = Integer.parseInt(discount.getText().toString());

            int totalSum = ticketVal + snackVal;
            int finalSum = totalSum - discountVal;

            all_sum.setText(String.format("%,d đ", totalSum));
            change.setText(String.format("%,d đ", finalSum));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int parseTextToInt(TextView tv) {
    String changeText = tv.getText().toString().replaceAll("[^\\d]", "");
    if(changeText.isEmpty()) return 0;
    return Integer.parseInt(changeText);
    }
    private void payAll(){
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bidv.isChecked() || vcb.isChecked() || momo.isChecked()) {
                    pay();
                    Intent myIntent = new Intent(HoaDon.this, QRPay.class);
                    myIntent.putExtra("cost", change.getText().toString());
                    startActivity(myIntent);
                } else {
                    Toast.makeText(HoaDon.this, "Vui lòng chọn ngân hàng thanh toán", Toast.LENGTH_SHORT).show();
                }
            }});}

    private void pay(){
        if(bidv.isChecked()){
        
        }
    }
}
