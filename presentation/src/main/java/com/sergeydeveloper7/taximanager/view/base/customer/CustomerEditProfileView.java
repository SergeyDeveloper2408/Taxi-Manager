package com.sergeydeveloper7.taximanager.view.base.customer;

public interface CustomerEditProfileView {

    //processes
    void showChangeDataProcessStart();
    void showChangeDataProcessError(Throwable throwable);
    void showChangeDataProcessEnd();

    //Email Address
    void showValidateEmailAddressStart();
    void showEmailAddressIsValid();
    void showEmailAddressIsInvalid();
    void showEmailAddressValidationError(Throwable throwable);

    //Phone number
    void showValidatePhoneNumberStart();
    void showPhoneNumberIsValid();
    void showPhoneNumberIsInvalid();
    void showPhoneNumberValidationError(Throwable throwable);
}
