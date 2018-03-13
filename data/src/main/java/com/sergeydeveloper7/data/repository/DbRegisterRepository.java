package com.sergeydeveloper7.data.repository;

import com.sergeydeveloper7.data.db.models.Customer;
import com.sergeydeveloper7.data.db.models.User;
import com.sergeydeveloper7.data.models.CustomerModel;
import com.sergeydeveloper7.data.models.UserModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by serg on 05.02.18.
 */

public class DbRegisterRepository {

    private Realm realm;

    public DbRegisterRepository() {
        realm = Realm.getDefaultInstance();
    }

    public Observable<UserModel> registerCustomer(UserModel userModel, CustomerModel customerModel) {

        return Observable.create((ObservableEmitter<UserModel> e) -> {
                realm.executeTransactionAsync(
                        realm -> {
                            //Register User
                            long userID;
                            try {
                                userID = realm.where(User.class).max("id").intValue() + 1;
                            } catch (Exception ex) {
                                userID = 0L;
                            }
                            User user = realm.createObject(User.class, userID);
                            user.setEmail(userModel.getEmail());
                            user.setPass(userModel.getPass());
                            user.setUserName(userModel.getUserName());
                            user.setRole(userModel.getRole());
                            user.setRating(0);
                            user.setPhoneNumber(userModel.getPhoneNumber());

                            //Register Customer
                            long customerID;
                            try {
                                customerID = realm.where(Customer.class).max("id").intValue() + 1;
                            } catch (Exception ex) {
                                customerID = 0L;
                            }
                            Customer customer = realm.createObject(Customer.class, customerID);
                            customer.setUserName(customerModel.getUserName());
                        },
                        () -> {
                            e.onComplete();
                            e.onNext(userModel);

                            RealmResults<User> users = realm.where(User.class).findAll();
                            System.out.println("========= Table Users =========");
                            for(int i = 0; i < users.size(); i++){
                                System.out.println("==================");
                                System.out.println("i: " + i);
                                System.out.println("id: " + users.get(i).getId());
                                System.out.println("email: " + users.get(i).getEmail());
                                System.out.println("pass: " + users.get(i).getPass());
                                System.out.println("userName: " + users.get(i).getUserName());
                                System.out.println("rating: " + users.get(i).getRating());
                                System.out.println("phoneNumber: " + users.get(i).getPhoneNumber());
                                System.out.println("==================");
                            }

                            RealmResults<Customer> customers = realm.where(Customer.class).findAll();
                            System.out.println("========= Table Customers =========");
                            for(int i = 0; i < users.size(); i++){
                                System.out.println("==================");
                                System.out.println("i: " + i);
                                System.out.println("id: " + customers.get(i).getId());
                                System.out.println("userName: " + customers.get(i).getUserName());
                                System.out.println("==================");
                            }
                        },
                        e::onError);
        });

    }

}
