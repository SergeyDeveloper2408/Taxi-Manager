package com.sergeydeveloper7.taximanager.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.data.models.general.BidModel;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.view.holders.customer.BidViewHolder;

import java.util.ArrayList;

/**
 * Created by serg on 09.01.18.
 */

public class CustomerBidsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context             context;
    private ArrayList<BidModel> items;

    public CustomerBidsAdapter(Context context, ArrayList<BidModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid, parent, false);
        return new BidViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return this.items == null ? 0 : this.items.size();
    }

}
