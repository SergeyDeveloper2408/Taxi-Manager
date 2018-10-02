package com.sergeydeveloper7.data.repository.basic.customer;

import com.sergeydeveloper7.data.models.general.BidModel;
import com.sergeydeveloper7.data.models.general.UserModel;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface CustomerBidsRepository {
    Observable<BidModel> newBid(BidModel bidModel);
    Observable<ArrayList<BidModel>> getBids(long customersUserID, String bidsState);
}
