package com.example.movieyou.Contract;


import com.example.movieyou.Model.MovieResults;

public interface HomeFragmentContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showMovies(MovieResults[] allMovies);

    }

    interface Presenter{
        void loadMovies();
    }
}
