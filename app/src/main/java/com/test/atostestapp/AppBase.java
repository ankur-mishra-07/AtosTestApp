package com.test.atostestapp;

import android.app.Activity;
import android.app.Application;

import com.test.atostestapp.di.component.DaggerAppComponent;
import com.test.atostestapp.di.module.NetworkModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class AppBase extends Application implements HasActivityInjector {

    private static AppBase mInstance;

    public static AppBase getAppInstance() {
        return mInstance;
    }

    private static synchronized void setInstance(AppBase appBase) {
        mInstance = appBase;
    }

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDaggerComponent();
        setInstance(this);
    }

    private void initializeDaggerComponent() {
        DaggerAppComponent.builder()
                .application(this)
                .apiModule(new NetworkModule())
                .build()
                .inject(this);
    }
}
