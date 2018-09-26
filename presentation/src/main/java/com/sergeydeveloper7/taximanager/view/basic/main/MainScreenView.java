package com.sergeydeveloper7.taximanager.view.basic.main;

/**
 * Created by serg on 11.01.18.
 */

public interface MainScreenView {
    void showLogInProcessStart();
    void showLogInProcessEnd(String role);
    void showLogInProcessUserNotFound();
    void showLogInProcessError(Throwable throwable);
}
