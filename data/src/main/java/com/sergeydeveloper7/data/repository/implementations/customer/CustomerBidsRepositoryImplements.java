package com.sergeydeveloper7.data.repository.implementations.customer;

import com.sergeydeveloper7.data.db.models.Bid;
import com.sergeydeveloper7.data.db.models.User;
import com.sergeydeveloper7.data.mapper.BidMapper;
import com.sergeydeveloper7.data.mapper.UserMapper;
import com.sergeydeveloper7.data.models.general.BidModel;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.data.repository.basic.customer.CustomerBidsRepository;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.realm.Realm;
import io.realm.RealmResults;

public class CustomerBidsRepositoryImplements implements CustomerBidsRepository {

    private Realm realm;

    public CustomerBidsRepositoryImplements() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<BidModel> newBid(BidModel bidModel) {
        return null;
    }

    @Override
    public Observable<ArrayList<BidModel>> getBids(long customersUserID, String bidsState) {

        ArrayList<BidModel> bidsArrayList = new ArrayList<>();

        return Observable.create((ObservableEmitter<ArrayList<BidModel>> e) ->
            realm.executeTransactionAsync(realm -> {
                RealmResults<Bid> bids = realm.where(Bid.class).findAll();
                for(int i = 0; i < bids.size(); i++){
                    if(bids.get(i).getCustomer().getUserID() == customersUserID
                            && bids.get(i).getState().equals(bidsState))
                        bidsArrayList.add(BidMapper.mapBid(bids.get(i)));
                }
            },
            () -> e.onNext(bidsArrayList), e::onError));
    }
}
