package com.blackboardtheory.qresume.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackboardtheory.qresume.R;
import com.blackboardtheory.qresume.adapters.FavoritesAdapter;
import com.blackboardtheory.qresume.objects.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdevereaux3 on 11/7/16.
 */

public class FavoritesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /* Required Empty Constructor */
    public FavoritesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_favorites, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.favorites_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Profile> favorites = new ArrayList<>();
        favorites.add(new Profile("John Smith", "Devry University"));
        favorites.add(new Profile("Jane Doe","Phoenix University"));

        mAdapter = new FavoritesAdapter(favorites, getContext());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


}
