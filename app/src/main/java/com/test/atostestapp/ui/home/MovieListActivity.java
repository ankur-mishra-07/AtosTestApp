package com.test.atostestapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.test.atostestapp.R;
import com.test.atostestapp.factory.ViewModelFactory;
import com.test.atostestapp.ui.home.adapter.MoviesListAdapter;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView mMovieList;

    @Inject
    ViewModelFactory viewModelFactory;

    MovieListViewModel moviesListViewModel;
    private MoviesListAdapter moviesListAdapter;
    private ConstraintLayout progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_movie_list);
        initViews();
        initModels();
    }

    private void initModels() {
        moviesListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel.class);
        progressView.setVisibility(View.VISIBLE);
        moviesListViewModel.loadMoreMovies((long) 1);
        moviesListViewModel.getMoviesLiveData().observe(this, resource -> {
            if (resource.isLoading()) {
                progressView.setVisibility(View.VISIBLE);
            } else if (!resource.data.isEmpty()) {
                moviesListAdapter.setData(resource.data.get(0).getResults());
                progressView.setVisibility(View.GONE);
            } else {
                progressView.setVisibility(View.GONE);
                Toast.makeText(this, getString(R.string.not_right), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initViews() {
        moviesListAdapter = new MoviesListAdapter(this);
        mMovieList = (RecyclerView) findViewById(R.id.movieList);
        progressView = (ConstraintLayout) findViewById(R.id.progressView);
        mMovieList.setAdapter(moviesListAdapter);
    }
}