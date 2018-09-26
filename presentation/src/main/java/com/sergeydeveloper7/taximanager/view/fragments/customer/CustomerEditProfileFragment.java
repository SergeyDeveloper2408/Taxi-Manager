package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.data.utils.UtilMethods;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.presenter.customer.CustomerEditProfilePresenter;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;
import com.sergeydeveloper7.taximanager.view.basic.customer.CustomerEditProfileView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class CustomerEditProfileFragment extends Fragment implements CustomerEditProfileView {

    private CustomerActivity             activity;
    private CustomerEditProfilePresenter presenter;
    private UserModel                    user;
    private SharedPreferences            sharedPreferences;
    private MenuItem                     save;
    private String                       newUserName;
    private String                       newEmailAddress;
    private String                       newPhoneNumber;
    private CompositeDisposable          compositeDisposable = new CompositeDisposable();

    //Edit Texts
    @BindView(R.id.editProfileNameTextInputEditText)
    TextInputEditText editProfileNameTextInputEditText;

    @BindView(R.id.editProfileEmailTextInputEditText)
    TextInputEditText editProfileEmailTextInputEditText;

    @BindView(R.id.editProfilePhoneNumberTextInputEditText)
    TextInputEditText editProfilePhoneNumberTextInputEditText;

    //Text Input Layouts
    @BindView(R.id.editProfileEmailTextInputLayout)
    TextInputLayout editProfileEmailTextInputLayout;

    @BindView(R.id.editProfilePhoneNumberTextInputLayout)
    TextInputLayout editProfilePhoneNumberTextInputLayout;

    //Layouts
    @BindView(R.id.customerEditProfileMainLayout)
    RelativeLayout customerEditProfileMainLayout;

    @BindView(R.id.editCustomerProfileFieldsRelativeLayout)
    RelativeLayout editCustomerProfileFieldsRelativeLayout;

    @BindView(R.id.photoRelativeLayout)
    RelativeLayout photoRelativeLayout;

    //Progress bars
    @BindView(R.id.editCustomerProfileProgressBar)
    ProgressBar editCustomerProfileProgressBar;

    @BindView(R.id.editCustomerProfileEmailProgressBar)
    ProgressBar editCustomerProfileEmailProgressBar;

    @BindView(R.id.editCustomerProfilePhoneNumberProgressBar)
    ProgressBar editCustomerProfilePhoneNumberProgressBar;

    //ImageViews
    @BindView(R.id.customerProfilePhoto)
    CircleImageView customerProfilePhoto;

    @BindView(R.id.verifyEmailResultImageView)
    ImageView verifyEmailResultImageView;

    @BindView(R.id.verifyPhoneNumberResultImageView)
    ImageView verifyPhoneNumberResultImageView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeComponents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_customer_edit_profile, container,
                false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.customer, menu);
        save = menu.findItem(R.id.action_save);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_save) {
            changeData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnTextChanged(R.id.editProfileNameTextInputEditText)
    void onNameEditTextChanged(CharSequence text) {
        newUserName = text.toString();
        if(!newUserName.equals(user.getUserName()) ){
            if(save != null)
                save.setVisible(true);
        } else {
            if(save != null)
                save.setVisible(false);
        }
    }

    @Override
    public void showChangeDataProcessStart() {
        photoRelativeLayout.setVisibility(View.GONE);
        editCustomerProfileFieldsRelativeLayout.setVisibility(View.GONE);
        editCustomerProfileProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showChangeDataProcessError(Throwable throwable) {
        photoRelativeLayout.setVisibility(View.VISIBLE);
        editCustomerProfileFieldsRelativeLayout.setVisibility(View.VISIBLE);
        editCustomerProfileProgressBar.setVisibility(View.GONE);
        Snackbar.make(customerEditProfileMainLayout, getString(R.string.process_error),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showChangeDataProcessEnd() {
        Toast.makeText(activity, getString(R.string.customer_screen_edited_profile_success), Toast.LENGTH_SHORT).show();
        activity.onBackPressed();
    }

    @Override
    public void showValidateEmailAddressStart() {

        if(verifyEmailResultImageView.getVisibility() == View.VISIBLE)
            verifyEmailResultImageView.setVisibility(View.GONE);

        editCustomerProfileEmailProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmailAddressIsValid() {
        editCustomerProfileEmailProgressBar.setVisibility(View.GONE);
        verifyEmailResultImageView.setVisibility(View.VISIBLE);
        verifyEmailResultImageView.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_checkmark));
        save.setVisible(true);
    }

    @Override
    public void showEmailAddressIsInvalid() {
        editCustomerProfileEmailProgressBar.setVisibility(View.GONE);
        editProfileEmailTextInputLayout.setError(
                getString(R.string.register_screen_email_exist_error));
        verifyEmailResultImageView.setVisibility(View.VISIBLE);
        verifyEmailResultImageView.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_cancel));
    }

    @Override
    public void showEmailAddressValidationError(Throwable throwable) {
        editCustomerProfileEmailProgressBar.setVisibility(View.GONE);
        verifyEmailResultImageView.setVisibility(View.GONE);
        Snackbar.make(customerEditProfileMainLayout,
                R.string.process_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showValidatePhoneNumberStart() {
        if(verifyPhoneNumberResultImageView.getVisibility() == View.VISIBLE)
            verifyPhoneNumberResultImageView.setVisibility(View.GONE);

        editCustomerProfilePhoneNumberProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPhoneNumberIsValid() {
        editCustomerProfilePhoneNumberProgressBar.setVisibility(View.GONE);
        verifyPhoneNumberResultImageView.setVisibility(View.VISIBLE);
        verifyPhoneNumberResultImageView.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_checkmark));
        save.setVisible(true);
    }

    @Override
    public void showPhoneNumberIsInvalid() {
        editCustomerProfilePhoneNumberProgressBar.setVisibility(View.GONE);
        editProfilePhoneNumberTextInputLayout.setError(
                getString(R.string.register_screen_phone_number_exist_error));
        verifyPhoneNumberResultImageView.setVisibility(View.VISIBLE);
        verifyPhoneNumberResultImageView.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_cancel));
    }

    @Override
    public void showPhoneNumberValidationError(Throwable throwable) {
        editCustomerProfilePhoneNumberProgressBar.setVisibility(View.GONE);
        verifyPhoneNumberResultImageView.setVisibility(View.GONE);
        Snackbar.make(customerEditProfileMainLayout,
                R.string.process_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void changeData(){
        user.setUserName(newUserName);
        user.setEmail(newEmailAddress);
        user.setPhoneNumber(newPhoneNumber);
        presenter.changeData(user);
    }

    private void checkIfEmailAddressExist(){
        presenter.validateEmailAddress(newEmailAddress);
    }

    private void checkIfPhoneNumberExist(){
        presenter.validatePhoneNumber(newPhoneNumber);
    }

    private void initializeViews(){

        activity.setTitle(getString(
                R.string.customer_screen_profile_edit_profile));
        activity.setNavigationActionBackArrow();

        editCustomerProfileProgressBar.getIndeterminateDrawable()
                .setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        editCustomerProfileEmailProgressBar.getIndeterminateDrawable()
                .setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        editCustomerProfilePhoneNumberProgressBar.getIndeterminateDrawable()
                .setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        newUserName = !TextUtils.isEmpty(user.getUserName()) ? user.getUserName()
                : getString(R.string.no_data_error);

        newEmailAddress =  !TextUtils.isEmpty(user.getEmail()) ? user.getEmail()
                : getString(R.string.no_data_error);

        newPhoneNumber = !TextUtils.isEmpty(user.getPhoneNumber()) ? user.getPhoneNumber()
                : getString(R.string.no_data_error);

        editProfileNameTextInputEditText.setText(newUserName);
        editProfileEmailTextInputEditText.setText(newEmailAddress);
        editProfilePhoneNumberTextInputEditText.setText(newPhoneNumber);


        Disposable editProfileEmailDisposable = RxTextView
                .textChanges(editProfileEmailTextInputEditText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> {
                    validateEmail(text.toString());
                });

        Disposable editProfilePhoneNumberDisposable = RxTextView
                .textChanges(editProfilePhoneNumberTextInputEditText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> {
                    validatePhoneNumber(text.toString());
                });

        compositeDisposable.add(editProfileEmailDisposable);
        compositeDisposable.add(editProfilePhoneNumberDisposable);

    }

    private void validateEmail(String text){

        editProfileEmailTextInputLayout.setErrorEnabled(false);
        editProfileEmailTextInputLayout.setError(null);

        newEmailAddress = text;

        if(!UtilMethods.isValidEmail(newEmailAddress))
            showInvalidEmailAddressError();
        else if(newEmailAddress.equals(user.getEmail())){
            editCustomerProfileEmailProgressBar.setVisibility(View.GONE);
            verifyEmailResultImageView.setVisibility(View.GONE);

            if(newPhoneNumber.equals(user.getPhoneNumber()))
                save.setVisible(false);
        } else
            checkIfEmailAddressExist();
    }

    private void validatePhoneNumber(String text){

        editProfilePhoneNumberTextInputLayout.setErrorEnabled(false);
        editProfilePhoneNumberTextInputLayout.setError(null);

        newPhoneNumber = text;

        if(newPhoneNumber.isEmpty() || newPhoneNumber.length() < 6)
            showInvalidPhoneNumberError();
        else if(newPhoneNumber.equals(user.getPhoneNumber())){
            editCustomerProfilePhoneNumberProgressBar.setVisibility(View.GONE);
            verifyPhoneNumberResultImageView.setVisibility(View.GONE);

             if(newEmailAddress.equals(user.getEmail()))
                 save.setVisible(false);
        } else
            checkIfPhoneNumberExist();
    }

    private void initializeComponents(){
        activity = (CustomerActivity)getActivity();
        presenter = new CustomerEditProfilePresenter(this, activity);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        user = new Gson().fromJson(sharedPreferences.getString("user", null),
                UserModel.class);

    }

    private void showInvalidEmailAddressError(){
        editCustomerProfileEmailProgressBar.setVisibility(View.GONE);
        editProfileEmailTextInputLayout.setError(getString(R.string.main_screen_invalid_email));
        verifyEmailResultImageView.setVisibility(View.VISIBLE);
        verifyEmailResultImageView.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_cancel));

    }

    private void showInvalidPhoneNumberError(){
        editCustomerProfilePhoneNumberProgressBar.setVisibility(View.GONE);
        editProfilePhoneNumberTextInputLayout.setError(getString(
                R.string.register_screen_email_phone_number_short));
        verifyPhoneNumberResultImageView.setVisibility(View.VISIBLE);
        verifyPhoneNumberResultImageView.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_cancel));

    }
}
