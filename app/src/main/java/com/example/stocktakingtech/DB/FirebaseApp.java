package com.example.stocktakingtech.DB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseApp extends android.app.Application{

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
