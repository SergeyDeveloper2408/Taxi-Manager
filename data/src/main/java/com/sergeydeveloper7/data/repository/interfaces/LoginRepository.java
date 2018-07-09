package com.sergeydeveloper7.data.repository.interfaces;

import com.sergeydeveloper7.data.models.UserModel;

import io.reactivex.Observable;

public interface LoginRepository {
    Observable<UserModel> login(String email, String password);
}
