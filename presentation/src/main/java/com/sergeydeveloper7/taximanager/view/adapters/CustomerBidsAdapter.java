package com.sergeydeveloper7.taximanager.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.holders.RegisterButtonViewHolder;
import com.sergeydeveloper7.taximanager.view.holders.RegisterFieldViewHolder;
import com.sergeydeveloper7.taximanager.view.holders.RegisterLabelViewHolder;

import java.util.ArrayList;

/**
 * Created by serg on 09.01.18.
 */

public class CustomerBidsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int         VIEW_TYPE_FIELD = 0;
    private final int         VIEW_TYPE_BUTTON = 1;
    private final int         VIEW_TYPE_LABEL = 2;
    private Context           context;
    private ArrayList<String> index;

    public CustomerBidsAdapter(Context context, ArrayList<String> index) {
        this.context = context;
        this.index = index;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FIELD) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_field_edit_text, parent, false);
            return new RegisterFieldViewHolder(layoutView);
        } else if (viewType == VIEW_TYPE_BUTTON) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_field_button, parent, false);
            return new RegisterButtonViewHolder(layoutView);
        } else if (viewType == VIEW_TYPE_LABEL) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_field_divider, parent, false);
            return new RegisterLabelViewHolder(layoutView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return this.index == null ? 0 : this.index.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch(index.get(position)){
////            case Const.REGISTER_BUTTON_CUSTOMER:
////            case Const.REGISTER_BUTTON_DRIVER:
//                return VIEW_TYPE_BUTTON;
            case Const.REGISTER_LABEL_PERSONAL:
            case Const.REGISTER_LABEL_CAR:
                return VIEW_TYPE_LABEL;
            default:
                return VIEW_TYPE_FIELD;
        }
    }
}
