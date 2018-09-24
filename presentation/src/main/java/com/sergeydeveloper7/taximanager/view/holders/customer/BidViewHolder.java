package com.sergeydeveloper7.taximanager.view.holders.customer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sergeydeveloper7.taximanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 10.01.18.
 */

public class BidViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.bidsId) public TextView registerFieldLabelTextView;
    @BindView(R.id.bidsState) public TextView registerFieldEnterInformationEditText;

    public BidViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindViews(Context context, String index){

    }
}
