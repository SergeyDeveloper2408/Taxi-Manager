package com.sergeydeveloper7.data.repository.implementations.main;

import com.sergeydeveloper7.data.db.models.Customer;
import com.sergeydeveloper7.data.db.models.User;
import com.sergeydeveloper7.data.mapper.UserMapper;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.data.repository.basic.main.LoginRepository;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by serg on 05.02.18.
 */

public class LoginRepositoryImplements implements LoginRepository {

    private Realm     realm;
    private UserModel userModel = new UserModel();

    public LoginRepositoryImplements() {
        realm = Realm.getDefaultInstance();
    }

    public Observable<UserModel> login(String email, String password) {
        return Observable.create((ObservableEmitter<UserModel> e) -> realm.executeTransactionAsync(
            realm -> {
                RealmResults<User> users = realm.where(User.class).findAll();
                for(int i = 0; i < users.size(); i++){
                    if(users.get(i).getEmailAddress().equals(email) && users.get(i)
                            .getPassword().equals(password)){
                        userModel = UserMapper.mapUser(users.get(i));
                    }
                }
            },
            () -> e.onNext(userModel), e::onError));
    }
}
