package com.test.atostestapp.di.module;

import com.test.atostestapp.ui.home.MovieListActivity;
import com.test.atostestapp.ui.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector()
    abstract MovieListActivity contributeMovieListActivity();


}
