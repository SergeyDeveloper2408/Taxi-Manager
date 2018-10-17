package com.sergeydeveloper7.taximanager.view.base.customer;

import com.sergeydeveloper7.data.models.general.BidModel;

import java.util.ArrayList;

public interface CustomerBidsView {

    void showFindBidsProcessStart();
    void showFindBidsProcessEnd(ArrayList<BidModel> bids);
    void showFindBidsProcessError(Throwable throwable);

}