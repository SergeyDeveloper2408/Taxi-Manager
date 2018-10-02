package com.sergeydeveloper7.taximanager.view.base.customer;

public interface CustomerBidsView {

    void showFindBidsProcessStart();
    void showFindBidsProcessEnd();
    void showFindBidsProcessError(Throwable throwable);

}