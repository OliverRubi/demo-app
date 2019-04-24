package com.example.movieyou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieyou.Adapters.AllMoviesRecyclerViewAdapter;
import com.example.movieyou.Adapters.EndlessRecyclerViewScrollListener;
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


    @BindView(R.id.allMovieTypeText)
    TextView allMovieType;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.allMovieListRecyclerView)
    RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AllMoviesRecyclerViewAdapter allMoviesRecyclerViewAdapter;
    private ArrayList<Movie> movieArrayList;
    @Inject
    MovieApiService movieApiService;
    private String movieType;
    private EndlessRecyclerViewScrollListener scrollListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_movies);

        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        movieType = intent.getStringExtra("MOVIETYPE");

        allMovieType.setText(movieType);
        //showProgress();



        loadMovies(1);
        allMoviesRecyclerViewAdapter = new AllMoviesRecyclerViewAdapter(this, movieArrayList);
        recyclerView.setAdapter(allMoviesRecyclerViewAdapter);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMovies(page);
            }
        });


    }

    private void loadMovies(int page) {

       /* switch (movieType) {
            case "NowPlaying":
                movieApiService.getNowPlayingMovies(URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
                    @Override
                    public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                        ArrayList<Movie> movieList = response.body().getMovies();


                        if (movieList == null) {
                            return;
                        }

                        movieArrayList = movieList;
                        Log.i("LOad", "onResponse: called");
                        showData();
                    }

                    @Override
                    public void onFailure(Call<MovieResults> call, Throwable t) {

                    }
                });
                break;
            case "Upcoming":
                movieApiService.getUpcomingMovies(URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
                    @Override
                    public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                        ArrayList<Movie> movieList = response.body().getMovies();


                        if (movieList == null) {
                            return;
                        }

                        movieArrayList = movieList;
                        allMoviesRecyclerViewAdapter.notifyDataSetChanged();
                        showData();
                    }

                    @Override
                    public void onFailure(Call<MovieResults> call, Throwable t) {

                    }
                });
                break;
            case "Popular":*/
                movieApiService.getPopularMovies(URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
                    @Override
                    public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                        ArrayList<Movie> movieList = response.body().getMovies();


                        if (movieList == null) {
                            Log.i("LOad", "onResponse: called");
                            return;

                        }

                        movieArrayList = movieList;
                        allMoviesRecyclerViewAdapter.notifyDataSetChanged();
                        showData();
                    }

                    @Override
                    public void onFailure(Call<MovieResults> call, Throwable t) {

                    }
                });
               /* break;
            case "TopRated":
                movieApiService.getTopRatedMovies(URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
                    @Override
                    public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                        ArrayList<Movie> movieList = response.body().getMovies();


                        if (movieList == null) {
                            return;
                        }

                        movieArrayList = movieList;
                        allMoviesRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MovieResults> call, Throwable t) {

                    }
                });
                break;
            default:
        }*/
    }

    void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    void showData() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
