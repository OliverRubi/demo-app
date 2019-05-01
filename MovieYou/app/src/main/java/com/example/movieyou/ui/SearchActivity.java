package com.example.movieyou.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
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

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.searchback)
    ImageButton searchBack;
    @BindView(R.id.search_results)
    RecyclerView searchResults;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    HomeMovieListRecyclerAdapter movieSearchViewAdapter;
    @BindView(R.id.search_view)
    SearchView searchView;
    ArrayList<Movie> movieArrayList;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Inject
    MovieApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);

        ((App) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setQueryHint("Search Movies");
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        searchView.setImeOptions(searchView.getImeOptions() | EditorInfo.IME_ACTION_SEARCH |
                EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        movieArrayList = new ArrayList<>();
        movieSearchViewAdapter = new HomeMovieListRecyclerAdapter(this, movieArrayList);
        searchResults.setAdapter(movieSearchViewAdapter);

        final LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        searchResults.setLayoutManager(mLayoutManager);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                movieArrayList.clear();
                search(query, 1);
                searchResults.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        search(query, page);
                    }
                });
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.GONE);
                return true;
            }
        });


        searchBack.setOnClickListener(v -> onBackPressed());


    }

    private void search(String query, int page) {
        if (page == 1) {
            progressBar.setVisibility(View.VISIBLE);
        }

        apiService.getSearchedMovies(URLConstant.API_KEY, query, page).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                progressBar.setVisibility(View.GONE);
                ArrayList<Movie> searchMovieList = response.body().getMovies();
                if (searchMovieList == null) {
                    return;
                }
                for (Movie obj : searchMovieList) {
                    movieArrayList.add(obj);
                }

                movieSearchViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }

        });
    }
}
