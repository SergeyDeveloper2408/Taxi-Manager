package com.sergeydeveloper7.taximanager.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sergeydeveloper7.domain.Util;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.fragments.RegisterFragment;
import com.sergeydeveloper7.taximanager.view.holders.RegisterButtonViewHolder;
import com.sergeydeveloper7.taximanager.view.holders.RegisterFieldViewHolder;
import com.sergeydeveloper7.taximanager.view.holders.RegisterLabelViewHolder;

import java.util.ArrayList;

/**
 * Created by serg on 09.01.18.
 */

public class RegisterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int         VIEW_TYPE_FIELD = 0;
    private final int         VIEW_TYPE_BUTTON = 1;
    private final int         VIEW_TYPE_LABEL = 2;
    private Context           context;
    private ArrayList<String> index;
    private RegisterFragment  fragment;

    public RegisterAdapter(Context context, ArrayList<String> index, RegisterFragment fragment) {
        this.context = context;
        this.index = index;
        this.fragment = fragment;
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
        if(holder instanceof RegisterFieldViewHolder){
            RegisterFieldViewHolder viewHolder = (RegisterFieldViewHolder) holder;
            viewHolder.bindViews(context, index.get(viewHolder.getAdapterPosition()));
            viewHolder.registerFieldEnterInformationEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    switch (index.get(viewHolder.getAdapterPosition())){
                        case Const.REGISTER_FIELD_EMAIL:
                            fragment.getUserModel().setEmail(s.toString());
                            break;
                        case Const.REGISTER_FIELD_PASSWORD:
                            if(!s.toString().isEmpty()){
                                fragment.getUserModel().setPass(String.valueOf(Util.encrypt(s.toString())));
                            }
                            break;
                        case Const.REGISTER_FIELD_USERNAME:
                            if(fragment.getUserModel().getRole().equals(context.getString(R.string.register_screen_role_customer))){
                                fragment.getUserModel().setUserName(s.toString());
                                fragment.getCustomerModel().setUserName(s.toString());
                            } else {
                                fragment.getUserModel().setUserName(s.toString());
                                fragment.getDriverModel().setUserName(s.toString());
                            }
                            break;
                        case Const.REGISTER_FIELD_PHONENUMBER:
                                fragment.getUserModel().setPhoneNumber(s.toString());
                            break;
                        case Const.REGISTER_FIELD_CAR_COLOR:
                            fragment.getCarModel().setColor(s.toString());
                            break;
                        case Const.REGISTER_FIELD_CAR_MODEL:
                            fragment.getCarModel().setModel(s.toString());
                            break;
                        case Const.REGISTER_FIELD_CAR_NUMBER:
                            fragment.getCarModel().setNumber(s.toString());
                            break;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if(holder instanceof RegisterButtonViewHolder){
            RegisterButtonViewHolder viewHolder = (RegisterButtonViewHolder) holder;
            viewHolder.registerButton.setOnClickListener((View v) -> {

                if(fragment.getUserModel().getEmail().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.register_screen_email_hint), Toast.LENGTH_SHORT).show();
                } else if(fragment.getUserModel().getPass().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.register_screen_password_hint), Toast.LENGTH_SHORT).show();
                } else if(fragment.getUserModel().getUserName().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.register_screen_username_hint), Toast.LENGTH_SHORT).show();
                } else if(fragment.getUserModel().getPhoneNumber().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.register_screen_phone_hint), Toast.LENGTH_SHORT).show();
                } else if(fragment.getUserModel().getRole().equals(context.getString(R.string.register_screen_role_driver))) {
                    if(fragment.getCarModel().getColor().isEmpty()){
                        Toast.makeText(context, context.getString(R.string.register_screen_car_color_hint), Toast.LENGTH_SHORT).show();
                    } else if(fragment.getCarModel().getModel().isEmpty()){
                        Toast.makeText(context, context.getString(R.string.register_screen_car_model_hint), Toast.LENGTH_SHORT).show();
                    } else if(fragment.getCarModel().getNumber().isEmpty()){
                        Toast.makeText(context, context.getString(R.string.register_screen_car_number_hint), Toast.LENGTH_SHORT).show();
                    } else {
                        fragment.registerDriver();
                    }
                } else {
                    fragment.registerCustomer();
                }
            });
        } else if(holder instanceof RegisterLabelViewHolder){
            RegisterLabelViewHolder viewHolder = (RegisterLabelViewHolder) holder;
            viewHolder.bindViews(context, index.get(viewHolder.getAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        return this.index == null ? 0 : this.index.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch(index.get(position)){
            case Const.REGISTER_BUTTON_CUSTOMER:
            case Const.REGISTER_BUTTON_DRIVER:
                return VIEW_TYPE_BUTTON;
            case Const.REGISTER_LABEL_PERSONAL:
            case Const.REGISTER_LABEL_CAR:
                return VIEW_TYPE_LABEL;
            default:
                return VIEW_TYPE_FIELD;
        }
    }
}
