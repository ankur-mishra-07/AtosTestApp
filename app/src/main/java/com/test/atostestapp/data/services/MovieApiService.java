package com.test.atostestapp.data.services;

import com.test.atostestapp.data.model.MovieApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/popular")
    Observable<MovieApiResponse> fetchMovies(@Query("apiToken") String apiToken,
                                             @Query("language") String lang,
                                             @Query("page") long page);
}
