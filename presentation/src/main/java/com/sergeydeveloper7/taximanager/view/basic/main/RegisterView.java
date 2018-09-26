package com.sergeydeveloper7.taximanager.view.basic.main;

/**
 * Created by serg on 11.01.18.
 */

public interface RegisterView {
    void showLoadingProcessStart();
    void showLoadingProcessEnd();
    void showRegistrationProcessError(Throwable throwable);
    void showEmailExistError();
    void showPhoneNumberExistError();
    void setValidation(boolean validation);
}
