package com.example.sumeetharyani.asset_manager.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumeetharyani.asset_manager.GS.Asset;
import com.example.sumeetharyani.asset_manager.R;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.AssetHolder> {
    private static final String TAG = "AssetAdapter";
    private Context mContext;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ArrayList<Asset> mlist;

    public AssetAdapter(Context context, ArrayList<Asset> list) {
        mContext = context;
        auth = FirebaseAuth.getInstance();
        mlist = list;
        Log.d(TAG, "AssetAdapter: " + String.valueOf(mlist.size()));


    }

    @Override
    public AssetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.cardview, parent, false);
        return new AssetHolder(view);
    }

    @Override
    public void onBindViewHolder(AssetHolder holder, final int position) {
        if (mlist.size()!=0) {
            Log.d(TAG, "onBindViewHolder: called" + String.valueOf(position));
            String name = mlist.get(holder.getAdapterPosition()).getAssetName();
            int quant = mlist.get(holder.getAdapterPosition()).getAssetQuantity();
            holder.assetName.setText(name);
            holder.assetQuantity.setText(String.valueOf(quant));
            holder.status.setBackgroundColor(Color.RED);
            Log.d(TAG, "onDataChange: " + name);
            Log.d(TAG, "onDataChange: " + String.valueOf(mlist.size()));
        }
        else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class AssetHolder extends RecyclerView.ViewHolder {

        TextView assetName;
        TextView assetQuantity;
        TextView status;

        public AssetHolder(View itemView) {
            super(itemView);
            assetName = itemView.findViewById(R.id.tv_title);
            assetQuantity = itemView.findViewById(R.id.tv_content);
            status = itemView.findViewById(R.id.status);
        }

    }
}



















