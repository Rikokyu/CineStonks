package com.example.cinestonks;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {
    // Trong file FirebaseHelper.java
    public static void getRap(ValueEventListener listener) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Rap");
        // Lấy tất cả rạp, không dùng child(id) hay limitToFirst(1)
        ref.addValueEventListener(listener);
    }
}
