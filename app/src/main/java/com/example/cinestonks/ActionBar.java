package com.example.cinestonks;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
public class ActionBar extends AppCompatActivity {
    private final List<ImageView> icons = new ArrayList<>();
    private final Context context;
    ImageView ivHome, ivTickets, ivAccount;

    public ActionBar(Context context, View root) {
        this.context = context;

        ivHome = root.findViewById(R.id.iv_home);
        ivTickets = root.findViewById(R.id.iv_buy_tickets);
        ivAccount = root.findViewById(R.id.iv_account);

        if (ivHome != null) icons.add(ivHome);
        if (ivTickets != null) icons.add(ivTickets);
        if (ivAccount != null) icons.add(ivAccount);

        for (ImageView icon : icons) {
            icon.setOnClickListener(v -> selectIcon((ImageView) v));
        }

        if (ivHome != null) {
            selectIcon(ivHome);
        }

        ivTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChuyenRap.class);
                context.startActivity(intent);
            }
        });

        ivAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, chuyen_account.class);
                context.startActivity(intent);
            }
        });
    }


    private void selectIcon(ImageView selectedIcon) {
        for (ImageView icon : icons) {
            if (icon == selectedIcon) {
                icon.setBackgroundResource(R.drawable.circle_green);
                icon.setColorFilter(ContextCompat.getColor(context, R.color.white));
                icon.setTranslationY(-20f);
            } else {
                icon.setBackground(null);
                icon.setColorFilter(ContextCompat.getColor(context, R.color.gray));
                icon.setTranslationY(0f);
            }
        }
    }
}