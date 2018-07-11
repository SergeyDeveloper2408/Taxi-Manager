package com.sergeydeveloper7.taximanager.view.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 11.01.18.
 */

public class RegisterButtonViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.registerButton) public Button registerButton;

    public RegisterButtonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindViews(Context context, String index){
        if(index.equals(Const.REGISTER_BUTTON))
            registerButton.setText(context.getString(R.string.main_screen_register_enter));
        else
            registerButton.setText(context.getString(R.string.register_screen_button_next_step));
    }
}
