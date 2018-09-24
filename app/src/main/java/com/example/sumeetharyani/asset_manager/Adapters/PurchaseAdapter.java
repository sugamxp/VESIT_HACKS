package com.example.sumeetharyani.asset_manager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sumeetharyani.asset_manager.Activity.AddPrice;
import com.example.sumeetharyani.asset_manager.GS.Asset;
import com.example.sumeetharyani.asset_manager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseHolder> {
    private static final String TAG = "PurchaseAdapter";
    private Context mContext;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ArrayList<Asset> mlist;

    public PurchaseAdapter(Context context, ArrayList<Asset> list) {
        mContext = context;
        auth = FirebaseAuth.getInstance();
        mlist = list;
        Log.d(TAG, "Purchase: " + String.valueOf(mlist.size()));

    }

    @Override
    public PurchaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.purchase_cardview, parent, false);
        return new PurchaseHolder(view);
    }


    @Override
    public void onBindViewHolder(final PurchaseHolder holder, final int position) {
        if (mlist.size() != 0) {
            Log.d(TAG, "onBindViewHolder: called" + String.valueOf(position));
            String name = mlist.get(holder.getAdapterPosition()).getAssetName();
            int quant = mlist.get(holder.getAdapterPosition()).getAssetQuantity();
            holder.assetName.setText(name);
            holder.assetQuantity.setText(String.valueOf(quant));

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, AddPrice.class);
//                    intent.putExtra("title", title);
//                    intent.putExtra("content", content);
//                    intent.putExtra("flag", 1);
//                    intent.putExtra("id", id);
//
//                    c.startActivity(intent);
//                    CustomIntent.customType(c,"fadein-to-fadeout");
//                }
//            });

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, AddPrice.class);
                    mContext.startActivity(i);
                }
            });
            Log.d(TAG, "onDataChange: " + name);
            Log.d(TAG, "onDataChange: " + String.valueOf(mlist.size()));
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class PurchaseHolder extends RecyclerView.ViewHolder {

        TextView assetName;
        TextView assetQuantity;
        Button btn;

        public PurchaseHolder(View itemView) {
            super(itemView);
            assetName = itemView.findViewById(R.id.tv_name);
            assetQuantity = itemView.findViewById(R.id.tv_quant);
            btn = itemView.findViewById(R.id.btnaddPrice);

        }

    }
}
