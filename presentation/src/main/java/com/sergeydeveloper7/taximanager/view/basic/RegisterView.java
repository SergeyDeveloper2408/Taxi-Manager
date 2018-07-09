package com.sergeydeveloper7.taximanager.view.basic;

import com.sergeydeveloper7.data.models.UserModel;

/**
 * Created by serg on 11.01.18.
 */

public interface RegisterView {
    void showRegistrationProcessStart();
    void showRegistrationProcessEnd(UserModel userModel);
    void showRegistrationProcessError(Throwable throwable);
    void showEmailExistError();
    void showPhoneNumberExistError();
}
