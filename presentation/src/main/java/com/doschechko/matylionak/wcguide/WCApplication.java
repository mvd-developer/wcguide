package com.doschechko.matylionak.wcguide;

import android.app.Application;

import io.realm.Realm;


public class WCApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
