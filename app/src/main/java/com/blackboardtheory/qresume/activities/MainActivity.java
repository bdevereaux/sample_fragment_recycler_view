package com.blackboardtheory.qresume.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.MenuItem;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.blackboardtheory.qresume.R;
import com.blackboardtheory.qresume.fragments.*;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private CoordinatorLayout coordinatorLayout;
    private Fragment scannerFragment, candidatesFragment, favoritesFragment, settingsFragment;

    private RequestQueue mRequestQueue;
    private Cache cache;
    private Network network;
    private ImageLoader mImageLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_activity);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Instantiate the cache
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        // Start the queue
        mRequestQueue.start();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        if (savedInstanceState == null) {
            scannerFragment = new ScannerFragment();
            candidatesFragment = new CandidatesFragment();
            favoritesFragment = new FavoritesFragment();
            settingsFragment = new SettingsFragment();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.view_container, scannerFragment).commit();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.camera_item:
                ft.replace(R.id.view_container, scannerFragment).commit();
                Snackbar.make(coordinatorLayout, "Scan item selected", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.candidates_item:
                ft.replace(R.id.view_container, candidatesFragment).commit();
                Snackbar.make(coordinatorLayout, "Candidates item selected", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.favorites_item:
                ft.replace(R.id.view_container, favoritesFragment).commit();
                Snackbar.make(coordinatorLayout, "Favorites item selected", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.settings_item:
                ft.replace(R.id.view_container, settingsFragment).commit();
                Snackbar.make(coordinatorLayout, "Settings item selected", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
