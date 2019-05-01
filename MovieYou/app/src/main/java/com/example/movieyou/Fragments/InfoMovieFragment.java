package com.example.movieyou.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieyou.Adapters.SimilarMovieRecyclerViewAdapter;
import com.example.movieyou.Dependency.App;
import com.example.movieyou.Model.AboutMovieResponse;
import com.example.movieyou.Model.Movie;
import com.example.movieyou.Model.MovieResults;
import com.example.movieyou.Networks.MovieApiService;
import com.example.movieyou.R;
import com.example.movieyou.URLConstant;
import com.example.movieyou.ui.SeeAllSimilarMovies;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoMovieFragment extends Fragment {


    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.revenueContent)
    TextView revenue;
    @BindView(R.id.dateRelease)
    TextView releaseDate;
    @BindView(R.id.similarRecyclerView)
    RecyclerView similarRecyclerView;
    @BindView(R.id.seeAllSimilar)
    TextView seeAllSimilar;
    @Inject
    MovieApiService apiService;

    SimilarMovieRecyclerViewAdapter recyclerViewAdapter;
    private int movieID;
    ArrayList<Movie> similarMovies;
    LinearLayoutManager layoutManager;

    public InfoMovieFragment() {
    }


    @Override
    public void onAttach(Context context) {
        ((App) context.getApplicationContext()).getComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("InfoMovieFragment", "onCreateView: Created");
        View view = inflater.inflate(R.layout.infomovie_layout, container, false);
       movieID = getArguments().getInt("movieID");
        Log.i("movieId", String.valueOf(movieID));

        ButterKnife.bind(this, view);

        loadText();


        seeAllSimilar.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("MOVIETYPE", "Similar");
            intent.putExtra("movieID", movieID);
            intent.putExtra("movieTitle", getArguments().getString("movieTitle"));
            intent.setClass(getContext(), SeeAllSimilarMovies.class);
            getContext().startActivity(intent);
        });

        similarMovies = new ArrayList<>();
        recyclerViewAdapter = new SimilarMovieRecyclerViewAdapter(similarMovies, getContext());
        similarRecyclerView.setAdapter(recyclerViewAdapter);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        similarRecyclerView.setLayoutManager(layoutManager);

        loadSimilar(1);
        return view;
    }

    private void loadSimilar(int page){
        apiService.getSimilarMovies(movieID, URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                ArrayList<Movie> movieList = response.body().getMovies();

               Log.i("Movie size", "onResponse: " + String.valueOf(movieList.size()));

                if (movieList == null) {
                    return;
                }

                for(Movie movie : movieList){
                    similarMovies.add(movie);
                }

                recyclerViewAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });
    }

    private void loadText(){
        apiService.getAboutMovie(movieID, URLConstant.API_KEY).enqueue(new Callback<AboutMovieResponse>() {
            @Override
            public void onResponse(Call<AboutMovieResponse> call, Response<AboutMovieResponse> response) {

                overview.setText(response.body().getOverview());
                releaseDate.setText(dateGenerator(response.body().getReleaseDate() ) );
                String rev = "$ " + String.format("%,d", response.body().getRevenue());
                revenue.setText(rev);
            }

            @Override
            public void onFailure(Call<AboutMovieResponse> call, Throwable t) {

            }
        });
    }
    private String dateGenerator(String date) {
        if (date.length() == 9 || date.length() == 10) {
            String month = date.substring(5, 7);
            String ans = "";
            switch (month) {
                case "01":
                    ans = "January";
                    break;
                case "02":
                    ans = "February";
                    break;
                case "03":
                    ans = "March";
                    break;
                case "04":
                    ans = "April";
                    break;
                case "05":
                    ans = "May";
                    break;
                case "06":
                    ans = "June";
                    break;
                case "07":
                    ans = "July";
                    break;
                case "08":
                    ans = "August";
                    break;
                case "09":
                    ans = "September";
                    break;
                case "10":
                    ans = "October";
                    break;
                case "11":
                    ans = "November";
                    break;
                case "12":
                    ans = "December";
                    break;

            }
            return (ans + " " + date.substring(8, 10) + "," + " " + date.substring(0, 4));
        } else
            return "NA";
    }
}
