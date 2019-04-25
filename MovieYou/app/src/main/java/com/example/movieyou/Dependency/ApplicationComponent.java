package com.example.movieyou.Dependency;

import com.example.movieyou.Fragments.Home.HomeFragmentPresenter;
import com.example.movieyou.ui.AboutMovie;
import com.example.movieyou.ui.SeeAllMoviesActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    void inject(HomeFragmentPresenter target);
    void inject(SeeAllMoviesActivity target);
    void inject(AboutMovie target);
}
