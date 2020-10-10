package com.test.atostestapp.data.repositry;

import com.test.atostestapp.data.NetworkBoundResource;
import com.test.atostestapp.data.Resource;
import com.test.atostestapp.data.model.MovieApiResponse;
import com.test.atostestapp.data.model.Result;
import com.test.atostestapp.data.services.MovieApiService;
import com.test.atostestapp.utils.AppUtils;
import com.test.atostestapp.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class MovieRepository {
    private MovieApiService movieApiService;

    public MovieRepository(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    public Observable<Resource<List<MovieApiResponse>>> loadMovies(Long page) {
        return new NetworkBoundResource<List<MovieApiResponse>, MovieApiResponse>() {

            @Override
            protected void saveCallResult(MovieApiResponse item) {
                AppUtils.save("static", item);
            }

            @Override
            protected Flowable<List<MovieApiResponse>> loadFromDb() {
                MovieApiResponse movieEntities = null;
                try {
                    movieEntities = (MovieApiResponse) AppUtils.retrieve("static");
                } catch (Exception e) {
                    e.printStackTrace();
                    return Flowable.empty();
                }
                if (movieEntities == null || movieEntities.getResults().isEmpty()) {
                    return Flowable.empty();
                }
                List<MovieApiResponse> mList = new ArrayList<>();
                mList.add((MovieApiResponse) AppUtils.retrieve("static"));
                return Flowable.just(mList);
            }

            @Override
            protected Observable<Resource<MovieApiResponse>> createCall() {
                return movieApiService.fetchMovies(Constants.API_KEY, Constants.Lang, page)
                        .flatMap(movieApiResponse -> Observable.just(movieApiResponse == null
                                ? Resource.error("", new MovieApiResponse())
                                : Resource.success(movieApiResponse)));
            }
        }.getAsObservable();
    }


}
