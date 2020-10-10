package com.test.atostestapp.ui.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.atostestapp.R;
import com.test.atostestapp.data.model.MovieApiResponse;
import com.test.atostestapp.data.model.Result;
import com.test.atostestapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {
    private Activity mActivity;
    private List<Result> movies;

    public MoviesListAdapter(Activity activity) {
        mActivity = activity;
        this.movies = new ArrayList<>();
    }

    @NonNull
    @Override
    public MoviesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(view);
    }

    public Result getItem(int position) {
        return movies.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesListAdapter.ViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setData(List<Result> data) {
        this.movies = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView movieImage;
        TextView movieName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movieImg);
            movieName = itemView.findViewById(R.id.movieName);
        }

        public void bindTo(Result item) {
            Glide.with(mActivity)
                    .load(Constants.IMG_BASE_URL + item.getPosterPath())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(movieImage);
            movieName.setText(item.getOriginalTitle());
        }
    }
}
