package com.example.movieyou.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieyou.Model.MovieResults;
import com.example.movieyou.R;
import com.example.movieyou.ui.SeeAllMoviesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder> {


    private MovieResults[] movieResults;
    Context context;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private HomeMovieListRecyclerAdapter homeMovieListRecyclerAdapter;

    public HomeRecyclerViewAdapter(MovieResults[] movieResults, Context context) {
        this.movieResults = movieResults;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_movie_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (movieResults != null && movieResults.length > position) {
            if (getItemViewType(position) == 0) {
                if (movieResults[position] != null) {

                    holder.movieTypeTextView.setText("Popular Movies");

                    holder.seeAllTextView.setOnClickListener(v -> {
                        Intent intent = new Intent();
                        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle();
                        intent.putExtra("MOVIETYPE", "Popular");
                        intent.setClass(context, SeeAllMoviesActivity.class);
                        context.startActivity(intent, bundle);
                    });
                    homeMovieListRecyclerAdapter = new HomeMovieListRecyclerAdapter(context, movieResults[position].getMovies());
                    holder.recyclerView.setAdapter(homeMovieListRecyclerAdapter);

//                    mLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
//                    holder.recyclerView.setLayoutManager(mLayoutManager);
                    gridLayoutManager = new GridLayoutManager(context, 2);
                    holder.recyclerView.setLayoutManager(gridLayoutManager);

                }
            } else if (getItemViewType(position) == 1) {
                if (movieResults[position] != null) {

                    holder.movieTypeTextView.setText("Top Rated");
                    homeMovieListRecyclerAdapter = new HomeMovieListRecyclerAdapter(context, movieResults[position].getMovies());
                    holder.recyclerView.setAdapter(homeMovieListRecyclerAdapter);

//                    mLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
//                    holder.recyclerView.setLayoutManager(mLayoutManager);
                    gridLayoutManager = new GridLayoutManager(context, 2);
                    holder.recyclerView.setLayoutManager(gridLayoutManager);
                }
            } else if (getItemViewType(position) == 2) {
                if (movieResults[position] != null) {

                    holder.movieTypeTextView.setText("Now Playing");
                    homeMovieListRecyclerAdapter = new HomeMovieListRecyclerAdapter(context, movieResults[position].getMovies());
                    holder.recyclerView.setAdapter(homeMovieListRecyclerAdapter);

//                    mLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
//                    holder.recyclerView.setLayoutManager(mLayoutManager);
                    gridLayoutManager = new GridLayoutManager(context, 2);
                    holder.recyclerView.setLayoutManager(gridLayoutManager);
                }
            } else if (getItemViewType(position) == 3) {
                if (movieResults[position] != null) {

                    holder.movieTypeTextView.setText("Upcoming");
                    homeMovieListRecyclerAdapter = new HomeMovieListRecyclerAdapter(context, movieResults[position].getMovies());
                    holder.recyclerView.setAdapter(homeMovieListRecyclerAdapter);

//                    mLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
//                    holder.recyclerView.setLayoutManager(mLayoutManager);
                    gridLayoutManager = new GridLayoutManager(context, 2);
                    holder.recyclerView.setLayoutManager(gridLayoutManager);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return movieResults.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 4;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movieListRecyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.movieTypeTextView)
        TextView movieTypeTextView;
        @BindView(R.id.seeAllTextView)
        TextView seeAllTextView;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
