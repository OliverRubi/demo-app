package com.example.movieyou.Fragments.Home;

import android.app.Application;

import com.example.movieyou.Contract.HomeFragmentContract;
import com.example.movieyou.Dependency.App;
import com.example.movieyou.Model.Movie;
import com.example.movieyou.Model.MovieResults;
import com.example.movieyou.Networks.MovieApiService;
import com.example.movieyou.URLConstant;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter, HomeFragmentContract {


    HomeFragmentContract.View view;

    @Inject
    MovieApiService movieApiService;

    private MovieResults[] allmovies;

    public HomeFragmentPresenter(Application application) {
        ((App) application).getComponent().inject(this);
    }

    public void setView(HomeFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void loadMovies() {
        allmovies = new MovieResults[4];
        movieApiService.getNowPlayingMovies(URLConstant.API_KEY, 1).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                ArrayList<Movie> movieList = response.body().getMovies();
                ArrayList<Movie> initialMovieList = new ArrayList<>();

                for (int i = 0; i < 4; i++) {
                    initialMovieList.add(movieList.get(i));
                }
                MovieResults nowPlayingMovies = new MovieResults();

                if (movieList == null) {
                    return;
                }

                nowPlayingMovies.setMovies(initialMovieList);
                allmovies[2] = nowPlayingMovies;
                view.showMovies(allmovies);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });
        movieApiService.getPopularMovies(URLConstant.API_KEY, 1).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                ArrayList<Movie> movieList = response.body().getMovies();
                ArrayList<Movie> initialMovieList = new ArrayList<>();

                for (int i = 0; i < 4; i++) {
                    initialMovieList.add(movieList.get(i));
                }

                MovieResults popularMovies = new MovieResults();

                if (movieList == null) {
                    return;
                }

                popularMovies.setMovies(initialMovieList);
                allmovies[1] = popularMovies;
                view.showMovies(allmovies);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });
        movieApiService.getTopRatedMovies(URLConstant.API_KEY, 1).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                ArrayList<Movie> movieList = response.body().getMovies();
                ArrayList<Movie> initialMovieList = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    initialMovieList.add(movieList.get(i));
                }
                MovieResults topRatedMovies = new MovieResults();

                if (movieList == null) {
                    return;
                }

                topRatedMovies.setMovies(initialMovieList);
                allmovies[0] = topRatedMovies;
                view.showMovies(allmovies);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });
        movieApiService.getUpcomingMovies(URLConstant.API_KEY, 1).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                ArrayList<Movie> movieList = response.body().getMovies();
                ArrayList<Movie> initialMovieList = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    initialMovieList.add(movieList.get(i));
                }
                MovieResults upcomingMovies = new MovieResults();

                if (movieList == null) {
                    return;
                }

                upcomingMovies.setMovies(initialMovieList);
                allmovies[3] = upcomingMovies;
                view.showMovies(allmovies);

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });
    }
}
