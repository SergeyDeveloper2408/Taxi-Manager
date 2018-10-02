package com.sergeydeveloper7.data.repository.implementations.customer;

import com.sergeydeveloper7.data.db.models.User;
import com.sergeydeveloper7.data.errors.EmailAddressExistException;
import com.sergeydeveloper7.data.errors.PhoneNumberExistException;
import com.sergeydeveloper7.data.mapper.UserMapper;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.data.repository.basic.customer.ChangeCustomerDataRepository;
import com.sergeydeveloper7.data.validation.Validation;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.realm.Realm;
import io.realm.RealmResults;

public class ChangeCustomerDataRepositoryImplements implements ChangeCustomerDataRepository {


    private Realm     realm;
    private UserModel userModel = new UserModel();

    public ChangeCustomerDataRepositoryImplements() {
        realm = Realm.getDefaultInstance();
    }


    public Observable<Validation> validateEmailAddress(String emailAddress) {

        return Observable.create((ObservableEmitter<Validation> e) -> {
            Validation registerValidation = new Validation();
            realm.executeTransactionAsync(
                    realm -> {
                        RealmResults<User> users = realm.where(User.class).findAll();
                        for(int i = 0; i < users.size(); i++){
                            if(users.get(i).getEmailAddress().equalsIgnoreCase(emailAddress)){
                                registerValidation.setValid(false);
                                registerValidation.setException(new EmailAddressExistException());
                            }
                        }
                    },
                    () -> e.onNext(registerValidation),
                    e::onError);
        });
    }

    public Observable<Validation> validatePhoneNumber(String phoneNumber) {

        return Observable.create((ObservableEmitter<Validation> e) -> {
            Validation registerValidation = new Validation();
            realm.executeTransactionAsync(
                    realm -> {
                        RealmResults<User> users = realm.where(User.class).findAll();
                        for(int i = 0; i < users.size(); i++){
                            if(users.get(i).getPhoneNumber().equalsIgnoreCase(phoneNumber)){
                                registerValidation.setValid(false);
                                registerValidation.setException(new PhoneNumberExistException());
                            }
                        }
                    },
                    () -> e.onNext(registerValidation),
                    e::onError);
        });
    }

    @Override
    public Observable<UserModel> changeData(UserModel editedUser) {
        return Observable.create((ObservableEmitter<UserModel> e) -> {
            realm.executeTransactionAsync(
                    realm -> {
                        User user = realm.where(User.class).equalTo("id", editedUser.getId()).findFirst();
                        user.setUserName(editedUser.getUserName());
                        user.setEmailAddress(editedUser.getEmailAddress());
                        user.setPhoneNumber(editedUser.getPhoneNumber());
                        userModel = UserMapper.mapUser(user);
                    },
                    () -> e.onNext(userModel), e::onError);
        });
    }
}
