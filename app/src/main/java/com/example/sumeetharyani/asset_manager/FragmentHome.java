package com.example.sumeetharyani.asset_manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FragmentHome extends Fragment {

    EditText etAssetRequired, etQuantityRequired;
    Button btnFacultyRequest;
    String assetName;
    int assetQuantity;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth auth;
    private Spinner spinner;
    private String branch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container,false);

        spinner = view.findViewById(R.id.spn);
        auth = FirebaseAuth.getInstance();
        etAssetRequired = view.findViewById(R.id.etAssetRequired);
        etQuantityRequired = view.findViewById(R.id.etQuantityRequired);
        btnFacultyRequest = view.findViewById(R.id.btnFacultyRequest);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        final ArrayList<String> size=new ArrayList<>();
        size.add("Select Branch");
        size.add("CMPN");
        size.add("IT");
        size.add("ETRX");
        size.add("EXTC");
        size.add("INSTRU");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_activated_1,size);
        spinner.setAdapter(adapter);


        btnFacultyRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetName = etAssetRequired.getText().toString();
                assetQuantity = Integer.parseInt(etQuantityRequired.getText().toString());
                int id= spinner.getSelectedItemPosition();
                branch = size.get(id);
                writeAsset(auth.getUid(), assetName, assetQuantity);
                etAssetRequired.setText("");
                etAssetRequired.requestFocus();
                etQuantityRequired.setText("");
                spinner.setSelection(0);
            }
        });

        return view;

    }

    private void writeAsset(String userId, String assetName, int assetQuantity) {
        Asset asset =  new Asset(userId,assetName, assetQuantity,0);

        mDatabaseRef.child("assets").child(branch).child(mDatabaseRef.push().getKey()).setValue(asset);
        mDatabaseRef.child("users").child("requests").child(userId).child(mDatabaseRef.push().getKey()).setValue(asset);
    }

}