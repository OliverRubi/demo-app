package com.example.movieyou.Fragments.Home;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieyou.Adapters.HomeRecyclerViewAdapter;
import com.example.movieyou.Contract.HomeFragmentContract;
import com.example.movieyou.Model.MovieResults;
import com.example.movieyou.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeFragmentContract.View {


    HomeFragmentPresenter mpresenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    HomeRecyclerViewAdapter homeRecyclerViewAdapter;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        mpresenter = new HomeFragmentPresenter(getActivity().getApplication());
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mpresenter.setView(this);
        showProgress();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        mpresenter.loadMovies();
        return view;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMovies(MovieResults[] allMovies) {

        if(allMovies[0] != null && allMovies[1] != null && allMovies[2] != null && allMovies[3] != null ){
            hideProgress();
            homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(allMovies, getActivity());
            recyclerView.setAdapter(homeRecyclerViewAdapter);
        }
    }


}
