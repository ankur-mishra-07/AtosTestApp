package com.test.atostestapp.di.component;

import android.app.Application;

import com.test.atostestapp.AppBase;
import com.test.atostestapp.di.module.ActivityModule;
import com.test.atostestapp.di.module.NetworkModule;
import com.test.atostestapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {NetworkModule.class, ViewModelModule.class,
        AndroidSupportInjectionModule.class, ActivityModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(NetworkModule apiModule);

        AppComponent build();
    }

    void inject(AppBase appController);
}
