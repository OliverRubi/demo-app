package com.example.movieyou.Dependency;

import com.example.movieyou.Networks.MovieApiService;
import com.example.movieyou.URLConstant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit(String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    MovieApiService provideMovieApiService(){
        return provideRetrofit(URLConstant.BASE_URL).create(MovieApiService.class);
    }


}
