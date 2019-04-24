package com.example.movieyou.Dependency;

import android.app.Application;

public class App extends Application {
    public ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .retrofitModule(new RetrofitModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
