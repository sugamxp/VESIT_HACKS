package com.example.sumeetharyani.asset_manager.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sumeetharyani.asset_manager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPrice extends AppCompatActivity {
    EditText editText;
    Button btn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);
//        editText = findViewById(R.id.etPrice);
//        btn = findViewById(R.id.btnadd);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String x = editText.getText().toString();
//                auth = FirebaseAuth.getInstance();
//                mDatabaseRef = FirebaseDatabase.getInstance().getReference();
//                mDatabaseRef.child("assets").child("CMPN").child("LN83toal9Fv7hU9sf6n").child("price").setValue(x);
//
//            }
//        });




    }
}
