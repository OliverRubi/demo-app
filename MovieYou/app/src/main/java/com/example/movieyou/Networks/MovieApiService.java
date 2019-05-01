package com.example.movieyou.Networks;

import com.example.movieyou.Model.AboutMovieResponse;
import com.example.movieyou.Model.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MovieResults> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/now_playing")
    Call<MovieResults> getNowPlayingMovies(@Query("api_key") String api_key,
                                            @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieResults> getTopRatedMovies(@Query("api_key") String api_key,
                                           @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieResults> getUpcomingMovies(@Query("api_key") String api_key,
                                         @Query("page") int page);

    @GET("movie/{id}")
    Call<AboutMovieResponse> getAboutMovie(@Path("id") int id, @Query("api_key") String api_key);

    @GET("movie/{id}/similar")
    Call<MovieResults> getSimilarMovies(@Path("id") int id, @Query("api_key") String api_key, @Query("page") int page);

    @GET("search/movie")
    Call<MovieResults> getSearchedMovies(@Query("api_key") String api_key, @Query("query") String query, @Query("page") int page);



}
