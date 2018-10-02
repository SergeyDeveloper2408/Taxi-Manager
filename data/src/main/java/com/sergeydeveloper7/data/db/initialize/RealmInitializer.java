package com.sergeydeveloper7.data.db.initialize;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by serg on 09.01.18.
 */

public class RealmInitializer {

    public static void initializeRealm(Context context){
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
