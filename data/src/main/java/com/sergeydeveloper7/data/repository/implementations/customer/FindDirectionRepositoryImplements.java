package com.sergeydeveloper7.data.repository.implementations.customer;

import com.sergeydeveloper7.data.BuildConfig;
import com.sergeydeveloper7.data.db.models.Customer;
import com.sergeydeveloper7.data.db.models.User;
import com.sergeydeveloper7.data.models.general.BidModel;
import com.sergeydeveloper7.data.models.general.CustomerModel;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.data.models.map.response.FindDirectionResponse;
import com.sergeydeveloper7.data.network.RestClient;
import com.sergeydeveloper7.data.network.RestInterface;
import com.sergeydeveloper7.data.repository.basic.customer.FindDirectionRepository;
import com.sergeydeveloper7.domain.Util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import io.reactivex.ObservableEmitter;
import io.realm.Realm;
import retrofit2.Response;
import rx.Observable;

public class FindDirectionRepositoryImplements implements FindDirectionRepository {

    private static final String API_KEY = BuildConfig.API_KEY;
    private RestInterface       client;
    private Realm               realm;

    public FindDirectionRepositoryImplements() {
        realm = Realm.getDefaultInstance();
        client = new RestClient().getApiService();
    }

    @Override
    public Observable<Response<FindDirectionResponse>> findDirection(String from, String to) {
        return client.findDirection(from, to, API_KEY);
    }

    @Override
    public Observable<BidModel> addNewBid(BidModel bidModel) {

        return Observable.create((ObservableEmitter<BidModel> e) -> {
            realm.executeTransactionAsync(
                    realm -> {

                        //Register User
                        long userID;
                        try {
                            userID = realm.where(User.class).max("id")
                                    .intValue() + 1;
                        } catch (Exception ex) {
                            userID = 0L;
                        }

                        User user = realm.createObject(User.class, userID);
                        user.setEmailAddress(userModel.getEmailAddress());

                        try {
                            user.setPassword(Util.SHA1(userModel.getPassword()));
                        } catch (NoSuchAlgorithmException e1)  {
                            user.setPassword(userModel.getPassword());
                            e1.printStackTrace();
                        } catch (UnsupportedEncodingException e1) {
                            user.setPassword(userModel.getPassword());
                            e1.printStackTrace();
                        }

                        user.setUserName(userModel.getUserName());
                        user.setRole(userModel.getRole());
                        user.setRating(0);
                        user.setPhoneNumber(userModel.getPhoneNumber());

                        //Register Customer
                        long customerID;
                        try {
                            customerID = realm.where(Customer.class).max("id")
                                    .intValue() + 1;
                        } catch (Exception ex) {
                            customerID = 0L;
                        }

                        Customer customer = realm.createObject(Customer.class, customerID);
                        customer.setUserID(userModel.getId());
                    },
                    () -> {
                        e.onNext(userModel);
                        e.onComplete();
                    },
                    e::onError);
        });
    }
}
