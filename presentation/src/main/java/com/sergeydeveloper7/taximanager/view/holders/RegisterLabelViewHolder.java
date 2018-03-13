package com.sergeydeveloper7.taximanager.view.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 10.01.18.
 */

public class RegisterLabelViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.registerFieldLabel) TextView registerFieldLabel;

    public RegisterLabelViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindViews(Context context, String index){
        switch (index){
            case Const.REGISTER_LABEL_PERSONAL:
                registerFieldLabel.setText(context.getString(R.string.register_screen_personal_information));
                break;
            case Const.REGISTER_LABEL_CAR:
                registerFieldLabel.setText(context.getString(R.string.register_screen_car_information));
                break;
        }
    }
}
