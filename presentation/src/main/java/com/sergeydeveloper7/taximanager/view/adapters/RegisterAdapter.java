package com.sergeydeveloper7.taximanager.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.data.enums.ValidationError;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.interfaces.ShowRegistrationError;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.fragments.main.RegisterFragment;
import com.sergeydeveloper7.taximanager.view.holders.register.RegisterButtonViewHolder;
import com.sergeydeveloper7.taximanager.view.holders.register.RegisterFieldViewHolder;
import com.sergeydeveloper7.taximanager.view.holders.register.RegisterLabelViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by serg on 09.01.18.
 */

public class RegisterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int                          VIEW_TYPE_FIELD = 0;
    private final int                          VIEW_TYPE_BUTTON = 1;
    private final int                          VIEW_TYPE_LABEL = 2;
    private Context                            context;
    private ArrayList<String>                  index;
    private RegisterFragment                   fragment;
    private Map<String, ShowRegistrationError> errorMap = new HashMap<>();

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
            errorMap.put(index.get(viewHolder.getAdapterPosition()), (ValidationError validationError)
                    -> showError(viewHolder, validationError));
            viewHolder.registerFieldLabelTextInputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    viewHolder.registerFieldLabelTextInputLayout.setErrorEnabled(false);
                    viewHolder.registerFieldLabelTextInputLayout.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    switch (index.get(viewHolder.getAdapterPosition())){
                        case Const.REGISTER_FIELD_EMAIL:
                            fragment.getUserModel().setEmail(s.toString());
                            break;
                        case Const.REGISTER_FIELD_PASSWORD:
                            if(!s.toString().isEmpty()){
                                fragment.getUserModel().setPass(s.toString());
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
            viewHolder.bindViews(context, index.get(viewHolder.getAdapterPosition()));
            viewHolder.registerButton.setOnClickListener((View v) -> {
                if(index.get(viewHolder.getAdapterPosition()).equals(Const.REGISTER_BUTTON_NEXT_STEP)){
                    if(checkPersonalFields()){
                        fragment.showCarFillingInformation();
                    }
                } else {
                    if(fragment.getUserModel().getRole().equals(context.getString(R.string.register_screen_role_driver))) {
                        if(checkCarFields()){
                            fragment.validateUser();
                        }
                    } else {
                        if(checkPersonalFields())
                            fragment.validateUser();
                    }
                }
            });
        } else if(holder instanceof RegisterLabelViewHolder){
            RegisterLabelViewHolder viewHolder = (RegisterLabelViewHolder) holder;
            viewHolder.bindViews(context, index.get(viewHolder.getAdapterPosition()));
        }
    }

    private boolean checkPersonalFields(){
        boolean isValidFields = false;
        if(fragment.getUserModel().getEmail().isEmpty()){
            getErrorMap().get(Const.REGISTER_FIELD_EMAIL).showError(ValidationError.EMAIL_EMPTY);
        } else if(!fragment.getUserModel().getEmail().contains("@")){
            getErrorMap().get(Const.REGISTER_FIELD_EMAIL).showError(ValidationError.EMAIL_INCORRECT);
        } else if(fragment.getUserModel().getPass().isEmpty()){
            getErrorMap().get(Const.REGISTER_FIELD_PASSWORD).showError(ValidationError.PASSWORD_EMPTY);
        } else if(fragment.getUserModel().getPass().length()<6){
            getErrorMap().get(Const.REGISTER_FIELD_PASSWORD).showError(ValidationError.PASSWORD_SHORT);
        } else if(fragment.getUserModel().getUserName().isEmpty()){
            getErrorMap().get(Const.REGISTER_FIELD_USERNAME).showError(ValidationError.USERNAME_EMPTY);
        } else if(fragment.getUserModel().getUserName().length()<6){
            getErrorMap().get(Const.REGISTER_FIELD_USERNAME).showError(ValidationError.USERNAME_SHORT);
        } else if(fragment.getUserModel().getPhoneNumber().isEmpty()){
            getErrorMap().get(Const.REGISTER_FIELD_PHONENUMBER).showError(ValidationError.PHONENUMBER_EMPTY);
        } else if(fragment.getUserModel().getPhoneNumber().length()<6){
            getErrorMap().get(Const.REGISTER_FIELD_PHONENUMBER).showError(ValidationError.PHONENUMBER_SHORT);
        } else isValidFields = true;
        return isValidFields;
    }

    private boolean checkCarFields(){
        boolean isValidFields = false;
        if(fragment.getCarModel().getColor().isEmpty()){
            getErrorMap().get(Const.REGISTER_FIELD_EMAIL).showError(ValidationError.CAR_COLOR_EMPTY);
        } else if(fragment.getCarModel().getModel().isEmpty()){
            getErrorMap().get(Const.REGISTER_FIELD_EMAIL).showError(ValidationError.CAR_MODEL_EMPTY);
        } else if(fragment.getCarModel().getNumber().isEmpty()){
            getErrorMap().get(Const.REGISTER_FIELD_EMAIL).showError(ValidationError.CAR_NUMBER_EMPTY);
        } else isValidFields = true;
        return isValidFields;
    }

    private void showError(RegisterFieldViewHolder viewHolder, ValidationError validationError){
            if(validationError.equals(ValidationError.EMAIL_EMPTY)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_email_hint));
            } else if(validationError.equals(ValidationError.EMAIL_EXIST)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_email_exist_error));
            } else if(validationError.equals(ValidationError.EMAIL_INCORRECT)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.main_screen_invalid_email));
            } else if(validationError.equals(ValidationError.PASSWORD_EMPTY)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_password_hint));
            } else if(validationError.equals(ValidationError.PASSWORD_SHORT)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_email_password_short));
            } else if(validationError.equals(ValidationError.USERNAME_EMPTY)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_username_hint));
            } else if(validationError.equals(ValidationError.USERNAME_SHORT)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_email_username_short));
            } else if(validationError.equals(ValidationError.PHONENUMBER_EMPTY)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_phone_hint));
            } else if(validationError.equals(ValidationError.PHONENUMBER_SHORT)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_email_phone_number_short));
            } else if(validationError.equals(ValidationError.PHONENUMBER_EXIST)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_phone_number_exist_error));
            } else if(validationError.equals(ValidationError.CAR_COLOR_EMPTY)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_car_color_hint));
            } else if(validationError.equals(ValidationError.CAR_MODEL_EMPTY)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_car_model_hint));
            } else if(validationError.equals(ValidationError.CAR_NUMBER_EMPTY)){
                viewHolder.registerFieldLabelTextInputLayout.setError(context.getString(R.string.register_screen_car_number_hint));
            }
    }

    public Map<String, ShowRegistrationError> getErrorMap() {
        return errorMap;
    }

    @Override
    public int getItemCount() {
        return this.index == null ? 0 : this.index.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch(index.get(position)){
            case Const.REGISTER_BUTTON:
            case Const.REGISTER_BUTTON_NEXT_STEP:
                return VIEW_TYPE_BUTTON;
            case Const.REGISTER_LABEL_PERSONAL:
            case Const.REGISTER_LABEL_CAR:
                return VIEW_TYPE_LABEL;
            default:
                return VIEW_TYPE_FIELD;
        }
    }
}
