package com.example.sumeetharyani.asset_manager.Activity;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.sumeetharyani.asset_manager.GS.Asset;
import com.example.sumeetharyani.asset_manager.Adapters.PurchaseAdapter;
import com.example.sumeetharyani.asset_manager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity {
    private static final String TAG = "ProductActivity";
    private RecyclerView recyclerView;
    private PurchaseAdapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ArrayList<Asset> list;
    private String branch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        list = new ArrayList<Asset>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.purchase_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        if(PreferenceManager.getDefaultSharedPreferences(this).getString("purchasebranch", "defaultStringIfNothingFound").equals("defaultStringIfNothingFound")) {
            Log.d(TAG, "onCreate: HERE!!!");
            new MaterialDialog.Builder(this)
                    .title("Select your Department")
                    .items(R.array.items)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            PreferenceManager.getDefaultSharedPreferences(PurchaseActivity.this).edit().putString("purchasebranch", text.toString()).apply();
                            branch = text.toString();
                            makeRecyclerView();
                            return true;
                        }
                    }).positiveText("Okay")
                    .show();

        }else{
            branch = (PreferenceManager.getDefaultSharedPreferences(this).getString("purchasebranch", "defaultStringIfNothingFound"));
            makeRecyclerView();
        }
    }

    private void makeRecyclerView(){
        databaseReference = firebaseDatabase.getReference("assets").child(branch);
        if (databaseReference != null) {
            Log.d(TAG, "onCreateView: not null");
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Asset asset = d.getValue(Asset.class);
                    list.add(asset);
                    Log.d(TAG, "onDataChange: " + String.valueOf(list.size()));
                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d(TAG, "onCreateView: "+ String.valueOf(list.size()));

        adapter = new PurchaseAdapter(this, list);
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "onCreateView: called");
    }
}