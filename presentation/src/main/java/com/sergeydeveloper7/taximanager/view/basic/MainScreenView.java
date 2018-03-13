package com.sergeydeveloper7.taximanager.view.basic;

/**
 * Created by serg on 11.01.18.
 */

public interface MainScreenView {
    void showLogInProcessStart();
    void showLogInProcessEnd();
    void showLogInProcessError(Throwable throwable);
}
