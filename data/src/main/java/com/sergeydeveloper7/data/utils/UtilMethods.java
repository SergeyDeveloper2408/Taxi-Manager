package com.sergeydeveloper7.data.utils;

import com.sergeydeveloper7.data.db.models.Car;
import com.sergeydeveloper7.data.db.models.Customer;
import com.sergeydeveloper7.data.db.models.Driver;
import com.sergeydeveloper7.data.db.models.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class UtilMethods {
    public static void showTables(Realm realm){
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

        RealmResults<Driver> drivers = realm.where(Driver.class).findAll();
        System.out.println("========= Table Drivers =========");
        for(int i = 0; i < drivers.size(); i++){
            System.out.println("==================");
            System.out.println("i: " + i);
            System.out.println("id: " + drivers.get(i).getId());
            System.out.println("userName: " + drivers.get(i).getUserName());
            System.out.println("userState: " + drivers.get(i).getUserState());
            System.out.println("carID: " + drivers.get(i).getCar().getId());
            System.out.println("==================");
        }

        RealmResults<Car> cars = realm.where(Car.class).findAll();
        System.out.println("========= Table Cars =========");
        for(int i = 0; i < cars.size(); i++){
            System.out.println("==================");
            System.out.println("i: " + i);
            System.out.println("id: " + cars.get(i).getId());
            System.out.println("color: " + cars.get(i).getColor());
            System.out.println("model: " + cars.get(i).getModel());
            System.out.println("number: " + cars.get(i).getNumber());
            System.out.println("==================");
        }
    }

}
