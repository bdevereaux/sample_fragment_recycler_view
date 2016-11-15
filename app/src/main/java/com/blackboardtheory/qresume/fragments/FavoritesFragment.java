package com.blackboardtheory.qresume.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blackboardtheory.qresume.R;
import com.blackboardtheory.qresume.activities.MainActivity;
import com.blackboardtheory.qresume.adapters.FavoritesAdapter;
import com.blackboardtheory.qresume.objects.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by bdevereaux3 on 11/7/16.
 */

public class FavoritesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private int page;
    private boolean loading, canLoadMore;

    private MainActivity activity;

    private ProgressBar progress;



    private List<Profile> projectList;

    /* Required Empty Constructor */
    public FavoritesFragment() {

    }


    private void fetchGithubData(int page) {
        String url ="https://api.github.com/search/repositories?q=tetris&page="+Integer.toString(page)+"&per_page=30";
        JSONObject projectData = new JSONObject();
        // Formulate the request and handle the response.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, projectData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseGithubData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Github", error.getMessage());
                        FavoritesFragment.this.canLoadMore = false;
                    }
                });
        // Add the request to the RequestQueue.
        activity.getRequestQueue().add(request);
    }

    private void parseGithubData(JSONObject data) {
        JSONArray projects;
        try {
            projects = data.getJSONArray("items");
            for(int i = 0; i < projects.length(); i++) {
                JSONObject project = projects.getJSONObject(i);
                Profile profile = new Profile(
                        project.getString("full_name"),
                        project.getJSONObject("owner").getString("avatar_url"),
                        project.getInt("id"));
                projectList.add(profile);
            }
            this.mAdapter.notifyDataSetChanged();
            this.loading = false;
            this.progress.setVisibility(View.INVISIBLE);
        } catch(JSONException e) {
            e.printStackTrace();
            Log.d("Github", e.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Github", "onCreate");
        this.page = 1;
        this.projectList = new ArrayList<>();
        this.activity = (MainActivity)getActivity();

        this.canLoadMore = true;

        this.mAdapter = new FavoritesAdapter(projectList, getContext(), activity.getImageLoader());
        this.loading = true;
        fetchGithubData(1);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Github", "onCreateView");
        View view = inflater.inflate(R.layout.view_favorites, container, false);
        progress = (ProgressBar) view.findViewById(R.id.progress_indicator);
        progress.setVisibility(View.INVISIBLE);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.favorites_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(getContext());
        //mRecyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);




        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount && !loading && canLoadMore) {
                    //End of list
                    loading = true;
                    progress.setVisibility(View.VISIBLE);
                    page++;
                    fetchGithubData(page);
                }
            }
        });


        return view;
    }


}
