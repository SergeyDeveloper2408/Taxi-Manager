package com.sergeydeveloper7.data.db;

import io.realm.Realm;

/**
 * Created by serg on 11.01.18.
 */

public class RealmService {

    private Realm realm;

    public RealmService() {
        realm = Realm.getDefaultInstance();
    }

}
