package com.example.sumeetharyani.asset_manager.Fragment;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.sumeetharyani.asset_manager.GS.Asset;
import com.example.sumeetharyani.asset_manager.R;
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


        final ArrayList<String> size=new ArrayList<>();
        size.add("Select Branch");
        size.add("CMPN");
        size.add("IT");
        size.add("ETRX");
        size.add("EXTC");
        size.add("INSTRU");

        if(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("branch", "defaultStringIfNothingFound").equals("defaultStringIfNothingFound")) {

            new MaterialDialog.Builder(getActivity())
                    .title("Select your Department")
                    .items(R.array.items)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("branch", text.toString()).apply();
                            branch = text.toString();
                            return true;
                        }
                    }).positiveText("Okay")
                    .show();
        }else{
            branch = (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("branch", "defaultStringIfNothingFound"));
        }
        auth = FirebaseAuth.getInstance();
        etAssetRequired = view.findViewById(R.id.etAssetRequired);
        etQuantityRequired = view.findViewById(R.id.etQuantityRequired);
        btnFacultyRequest = view.findViewById(R.id.btnFacultyRequest);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        btnFacultyRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetName = etAssetRequired.getText().toString();
                assetQuantity = Integer.parseInt(etQuantityRequired.getText().toString());
                writeAsset(auth.getUid(), assetName, assetQuantity);
                etAssetRequired.setText("");
                etAssetRequired.requestFocus();
                etQuantityRequired.setText("");
            }
        });
//        FirebaseVisionBarcodeDetectorOptions options =
//                new FirebaseVisionBarcodeDetectorOptions.Builder()
//                        .setBarcodeFormats(
//                                FirebaseVisionBarcode.FORMAT_QR_CODE,
//                                FirebaseVisionBarcode.FORMAT_AZTEC)
//                        .build();
//
//
//        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
//                .getVisionBarcodeDetector(options);


        return view;



    }

    private void writeAsset(String userId, String assetName, int assetQuantity) {
        Asset asset =  new Asset(userId,assetName, assetQuantity,0);

        mDatabaseRef.child("assets").child(branch).child(mDatabaseRef.push().getKey()).setValue(asset);
        mDatabaseRef.child("users").child("requests").child(userId).child(mDatabaseRef.push().getKey()).setValue(asset);
    }

}