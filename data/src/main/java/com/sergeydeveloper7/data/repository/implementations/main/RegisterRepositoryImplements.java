package com.sergeydeveloper7.data.repository.implementations.main;

import com.sergeydeveloper7.data.db.models.Car;
import com.sergeydeveloper7.data.db.models.Customer;
import com.sergeydeveloper7.data.db.models.Driver;
import com.sergeydeveloper7.data.db.models.User;
import com.sergeydeveloper7.data.errors.EmailAddressExistException;
import com.sergeydeveloper7.data.errors.PhoneNumberExistException;
import com.sergeydeveloper7.data.models.general.CarModel;
import com.sergeydeveloper7.data.models.general.CustomerModel;
import com.sergeydeveloper7.data.models.general.DriverModel;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.data.repository.basic.main.RegisterRepository;
import com.sergeydeveloper7.data.validation.Validation;
import com.sergeydeveloper7.domain.Util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by serg on 05.02.18.
 */

public class RegisterRepositoryImplements implements RegisterRepository {

    private Realm realm;

    public RegisterRepositoryImplements() {
        realm = Realm.getDefaultInstance();
    }

    public Observable<Validation> validateRegistration(UserModel userModel) {

        return Observable.create((ObservableEmitter<Validation> e) -> {
            Validation registerValidation = new Validation();
            realm.executeTransactionAsync(
                    realm -> {
                        RealmResults<User> users = realm.where(User.class).findAll();
                        for(int i = 0; i < users.size(); i++){
                            if(users.get(i).getEmailAddress().equalsIgnoreCase(
                                    userModel.getEmailAddress())){
                                registerValidation.setValid(false);
                                registerValidation.setException(new EmailAddressExistException());
                            } else if(users.get(i).getPhoneNumber().equalsIgnoreCase(
                                    userModel.getPhoneNumber())){
                                registerValidation.setValid(false);
                                registerValidation.setException(new PhoneNumberExistException());
                            }
                        }
                    },
                    () -> e.onNext(registerValidation),
                    e::onError);
        });
    }

    public Observable<UserModel> registerCustomer(UserModel userModel,
                                                  CustomerModel customerModel) {

        return Observable.create((ObservableEmitter<UserModel> e) -> {
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

    public Observable<UserModel> registerDriver(UserModel userModel, DriverModel driverModel,
                                                CarModel carModel) {
        return Observable.create((ObservableEmitter<UserModel> e) -> {
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

                        //Register Driver
                        long driverID;
                        try {
                            driverID = realm.where(Driver.class).max("id")
                                    .intValue() + 1;
                        } catch (Exception ex) {
                            driverID = 0L;
                        }

                        Driver driver = realm.createObject(Driver.class, driverID);
                        driver.setUserID(userModel.getId());
                        driver.setUserState("free");

                        //Register Car
                        long carID;
                        try {
                            carID = realm.where(Car.class).max("id")
                                    .intValue() + 1;
                        } catch (Exception ex) {
                            carID = 0L;
                        }

                        Car car = realm.createObject(Car.class, carID);
                        car.setColor(carModel.getColor());
                        car.setModel(carModel.getModel());
                        car.setNumber(carModel.getNumber());

                        driver.setCar(car);
                    },
                    () -> {
                        e.onNext(userModel);
                        e.onComplete();
                    },
                    e::onError);
        });
    }
}
