package com.sergeydeveloper7.data.mapper;

import com.sergeydeveloper7.data.db.models.User;
import com.sergeydeveloper7.data.models.general.UserModel;

/**
 * Created by serg on 29.03.18.
 */

public class UserMapper {

    public static UserModel mapUser(User user){
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setEmail(user.getEmail());
        userModel.setPass(user.getPass());
        userModel.setUserName(user.getUserName());
        userModel.setRating(user.getRating());
        userModel.setPhoneNumber(user.getPhoneNumber());
        userModel.setRole(user.getRole());
        return userModel;
    }
}
