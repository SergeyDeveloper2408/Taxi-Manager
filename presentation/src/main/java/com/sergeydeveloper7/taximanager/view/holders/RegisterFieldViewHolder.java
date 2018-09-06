package com.sergeydeveloper7.taximanager.view.holders;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 10.01.18.
 */

public class RegisterFieldViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.registerFieldLabelTextInputLayout)   public TextInputLayout   registerFieldLabelTextInputLayout;
    @BindView(R.id.registerFieldLabelTextInputEditText) public TextInputEditText registerFieldLabelTextInputEditText;

    public RegisterFieldViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindViews(Context context, String index){
        registerFieldLabelTextInputEditText.setText("");
        switch (index){
            case Const.REGISTER_FIELD_EMAIL:
                registerFieldLabelTextInputLayout.setHint(context.getString(R.string.main_screen_email_address));
                break;
            case Const.REGISTER_FIELD_PASSWORD:
                registerFieldLabelTextInputLayout.setHint(context.getString(R.string.register_screen_password));
                registerFieldLabelTextInputEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case Const.REGISTER_FIELD_USERNAME:
                registerFieldLabelTextInputLayout.setHint(context.getString(R.string.register_screen_username));
                break;
            case Const.REGISTER_FIELD_PHONENUMBER:
                registerFieldLabelTextInputLayout.setHint(context.getString(R.string.register_screen_phonenumber));
                registerFieldLabelTextInputEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case Const.REGISTER_FIELD_CAR_COLOR:
                registerFieldLabelTextInputLayout.setHint(context.getString(R.string.register_screen_car_information_color));
                registerFieldLabelTextInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case Const.REGISTER_FIELD_CAR_MODEL:
                registerFieldLabelTextInputLayout.setHint(context.getString(R.string.register_screen_car_information_model));
                registerFieldLabelTextInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case Const.REGISTER_FIELD_CAR_NUMBER:
                registerFieldLabelTextInputLayout.setHint(context.getString(R.string.register_screen_car_information_number));
                registerFieldLabelTextInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
        }
    }
}
