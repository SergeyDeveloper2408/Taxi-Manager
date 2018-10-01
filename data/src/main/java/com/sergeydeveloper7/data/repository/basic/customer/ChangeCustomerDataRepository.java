package com.sergeydeveloper7.data.repository.basic.customer;

import com.sergeydeveloper7.data.models.general.UserModel;

import io.reactivex.Observable;

public interface ChangeCustomerDataRepository {
    Observable<UserModel> changeData(UserModel userModel);
}
