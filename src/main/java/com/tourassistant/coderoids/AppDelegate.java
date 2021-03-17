package com.tourassistant.coderoids;

import android.app.Application;

import com.tourassistant.coderoids.appdb.AppDatabase;
import com.tourassistant.coderoids.appdb.DatabaseClient;

public class AppDelegate extends Application {

    private boolean appRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();

    }

}
