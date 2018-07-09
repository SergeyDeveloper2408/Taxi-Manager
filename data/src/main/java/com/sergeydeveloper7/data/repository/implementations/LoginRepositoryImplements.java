package com.sergeydeveloper7.data.repository.implementations;

import com.sergeydeveloper7.data.db.models.Customer;
import com.sergeydeveloper7.data.db.models.User;
import com.sergeydeveloper7.data.mapper.UserMapper;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.data.repository.interfaces.LoginRepository;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by serg on 05.02.18.
 */

public class LoginRepositoryImplements implements LoginRepository {

    private Realm realm;
    private UserModel userModel = new UserModel();

    public LoginRepositoryImplements() {
        realm = Realm.getDefaultInstance();
    }

    public Observable<UserModel> login(String email, String password) {
        return Observable.create((ObservableEmitter<UserModel> e) -> {
                realm.executeTransactionAsync(
                        realm -> {
                            RealmResults<User> users = realm.where(User.class).findAll();
                            for(int i = 0; i < users.size(); i++){
                                if(users.get(i).getEmail().equals(email) && users.get(i).getPass().equals(password)){
                                    userModel = UserMapper.mapUser(users.get(i));
                                }
                            }
                        },
                        () -> {
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
