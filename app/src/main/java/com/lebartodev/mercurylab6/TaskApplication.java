package com.lebartodev.mercurylab6;

import android.app.Application;

/**
 * Created by Александр on 28.11.2016.
 */

public class TaskApplication extends Application {
    protected static TaskApplication instance;

    public static TaskApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}
