package com.sergeydeveloper7.taximanager.view.basic;

/**
 * Created by serg on 11.01.18.
 */

public interface RegisterView {
    void showRegistrationProcessStart();
    void showRegistrationProcessEnd();
    void showRegistrationProcessError(Throwable throwable);
}
