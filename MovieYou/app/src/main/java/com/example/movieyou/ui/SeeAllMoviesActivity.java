package com.example.movieyou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieyou.Adapters.EndlessRecyclerViewScrollListener;
import com.example.movieyou.Adapters.HomeMovieListRecyclerAdapter;
import com.example.movieyou.Dependency.App;
import com.example.movieyou.Model.Movie;
import com.example.movieyou.Model.MovieResults;
import com.example.movieyou.Networks.MovieApiService;
import com.example.movieyou.R;
import com.example.movieyou.URLConstant;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllMoviesActivity extends AppCompatActivity {

    private EndlessRecyclerViewScrollListener scrollListener;
    HomeMovieListRecyclerAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    String movieType;
    ArrayList<Movie> movieArrayList;
    @BindView(R.id.movieTypeTextView)
    TextView movieTypeText;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @Inject
    MovieApiService movieApiService;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_content_movie);

        ((App) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        Slide slide = new Slide(Gravity.BOTTOM);
        getWindow().setEnterTransition(slide);
        getWindow().setAllowEnterTransitionOverlap(true);


        Intent intent = getIntent();
        movieType = intent.getStringExtra("MOVIETYPE");
        this.setTitle("");
        movieTypeText.setText(movieType);
        Log.i("hehe", movieType);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.movieListRecyclerView);
        movieArrayList = new ArrayList<Movie>();

        recyclerViewAdapter = new HomeMovieListRecyclerAdapter(this, movieArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        final LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        showProgress();
        loadData(1);
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadData(page);
            }

        };

        recyclerView.addOnScrollListener(scrollListener);


    }


    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

    }

    private void showData() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void loadData(int page) {


        if (movieType.equals("Popular Movies")) {

            movieApiService.getPopularMovies(URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    ArrayList<Movie> movieList = response.body().getMovies();


                    if (movieList == null) {
                        return;
                    }
                    for (Movie obj : movieList) {
                        movieArrayList.add(obj);
                    }

                    recyclerViewAdapter.notifyDataSetChanged();
                    showData();
                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {

                }
            });
        } else if (movieType.equals("Now Playing")) {
            movieApiService.getNowPlayingMovies(URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    ArrayList<Movie> movieList = response.body().getMovies();


                    if (movieList == null) {
                        return;
                    }
                    for (Movie obj : movieList) {
                        movieArrayList.add(obj);
                    }

                    recyclerViewAdapter.notifyDataSetChanged();
                    showData();

                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {

                }
            });
        } else if (movieType.equals("Top Rated")) {
            movieApiService.getTopRatedMovies(URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    ArrayList<Movie> movieList = response.body().getMovies();


                    if (movieList == null) {
                        return;
                    }
                    for (Movie obj : movieList) {
                        movieArrayList.add(obj);
                    }

                    recyclerViewAdapter.notifyDataSetChanged();
                    showData();

                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {

                }
            });
        } else if (movieType.equals("Upcoming")) {
            movieApiService.getUpcomingMovies(URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    ArrayList<Movie> movieList = response.body().getMovies();


                    if (movieList == null) {
                        return;
                    }
                    for (Movie obj : movieList) {
                        movieArrayList.add(obj);
                    }

                    recyclerViewAdapter.notifyDataSetChanged();
                    showData();

                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {

                }
            });

        }


    }
}