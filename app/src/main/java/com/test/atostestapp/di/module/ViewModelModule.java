package com.test.atostestapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.test.atostestapp.factory.ViewModelFactory;
import com.test.atostestapp.ui.base.BaseViewModel;
import com.test.atostestapp.ui.home.MovieListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    protected abstract ViewModel baseViewModelViewModel(MovieListViewModel moviesListViewModel);

}
