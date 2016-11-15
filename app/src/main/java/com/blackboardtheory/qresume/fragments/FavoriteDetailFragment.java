package com.blackboardtheory.qresume.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.blackboardtheory.qresume.R;
import com.blackboardtheory.qresume.activities.MainActivity;

/**
 * Created by bdevereaux3 on 11/14/16.
 */

public class FavoriteDetailFragment extends Fragment {

    private MainActivity activity;

    /*Needs blank default constructor*/
    public FavoriteDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_favorite_detail, container, false);
        NetworkImageView poster = (NetworkImageView)view.findViewById(R.id.poster);
        String url = getArguments().getString("url");
        poster.setImageUrl(url, activity.getImageLoader());
        return view;
    }
}
