package com.example.cinestonks.BLL;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.cinestonks.R;

import java.util.ArrayList;
import java.util.List;

public class ActionBar {
    private final List<ImageView> icons = new ArrayList<>();
    private final Context context;
    private final String userId;
    ImageView ivHome, ivTickets, ivAccount;

    public ActionBar(Context context, View root, String userId) {
        this.context = context;
        this.userId = userId;

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

        if (ivTickets != null) {
            ivTickets.setOnClickListener(v -> {
                selectIcon(ivTickets);
                Intent intent = new Intent(context, ChuyenRap.class);
                intent.putExtra("USER_ID", userId); // Bắt đầu truyền ID từ đây
                context.startActivity(intent);
            });
        }

        if (ivAccount != null) {
            ivAccount.setOnClickListener(v -> {
                selectIcon(ivAccount);
                Intent intent = new Intent(context, Tai_khoan.class);
                intent.putExtra("USER_ID", userId);
                context.startActivity(intent);
            });
        }
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
