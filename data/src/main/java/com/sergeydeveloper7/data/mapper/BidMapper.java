package com.sergeydeveloper7.data.mapper;

import com.sergeydeveloper7.data.db.models.Bid;
import com.sergeydeveloper7.data.db.models.User;
import com.sergeydeveloper7.data.models.general.BidModel;
import com.sergeydeveloper7.data.models.general.UserModel;

/**
 * Created by serg on 29.03.18.
 */

public class BidMapper {

    public static BidModel mapBid(Bid bid){
        BidModel bidModel = new BidModel();
        bidModel.setId(bid.getId());
        bidModel.setCustomer(bid.getCustomer());
        bidModel.setDriver(bid.getDriver());
        bidModel.setState(bid.getState());
        bidModel.setPointFrom(bid.getPointFrom());
        bidModel.setPointTo(bid.getPointTo());
        bidModel.setDistance(bid.getDistance());
        bidModel.setDuration(bid.getDuration());
        bidModel.setCost(bid.getCost());
        bidModel.setReview(bid.getReview());
        return bidModel;
    }
}
