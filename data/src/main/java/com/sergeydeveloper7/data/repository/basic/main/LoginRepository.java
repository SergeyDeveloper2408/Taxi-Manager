package com.sergeydeveloper7.data.repository.basic.main;

import com.sergeydeveloper7.data.models.general.UserModel;

import io.reactivex.Observable;

public interface LoginRepository {
    Observable<UserModel> login(String email, String password);
}
