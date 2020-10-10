package com.test.atostestapp.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.atostestapp.data.Resource;
import com.test.atostestapp.data.model.MovieApiResponse;
import com.test.atostestapp.data.repositry.MovieRepository;
import com.test.atostestapp.data.services.MovieApiService;
import com.test.atostestapp.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class MovieListViewModel extends BaseViewModel {

    private MovieRepository movieRepository;
    private MutableLiveData<Resource<List<MovieApiResponse>>> moviesLiveData = new MutableLiveData<>();
    @Inject
    public MovieListViewModel(MovieApiService movieApiService) {
        movieRepository = new MovieRepository(movieApiService);
    }

    public void loadMoreMovies(Long currentPage) {
        movieRepository.loadMovies(currentPage)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> getMoviesLiveData().postValue(resource));
    }

    public MutableLiveData<Resource<List<MovieApiResponse>>> getMoviesLiveData() {
        return moviesLiveData;
    }

}
