package com.blackboardtheory.qresume.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.blackboardtheory.qresume.R;
import com.blackboardtheory.qresume.fragments.*;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private CoordinatorLayout coordinatorLayout;
    private Fragment scannerFragment, candidatesFragment, favoritesFragment, settingsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_activity);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

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

}
