package com.sergeydeveloper7.data.repository.interfaces;

import com.sergeydeveloper7.data.models.CustomerModel;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.data.validation.RegisterValidation;

import io.reactivex.Observable;

public interface RegisterRepository {
    Observable<RegisterValidation> validateRegistration(UserModel userModel);
    Observable<UserModel> registerCustomer(UserModel userModel, CustomerModel customerModel);
}
