package com.sergeydeveloper7.data.repository.basic.main;

import com.sergeydeveloper7.data.models.CustomerModel;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.data.validation.Validation;

import io.reactivex.Observable;

public interface RegisterRepository {
    Observable<Validation> validateRegistration(UserModel userModel);
    Observable<UserModel> registerCustomer(UserModel userModel, CustomerModel customerModel);
}
