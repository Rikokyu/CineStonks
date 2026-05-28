package com.example.cinestonks.supports;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {
    // Trong file FirebaseHelper.java
    public static void getRap(ValueEventListener listener) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Rap");
        ref.addValueEventListener(listener);
    }
}
