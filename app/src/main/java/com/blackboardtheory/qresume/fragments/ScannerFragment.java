package com.blackboardtheory.qresume.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackboardtheory.qresume.R;

/**
 * Created by bdevereaux3 on 11/7/16.
 */

public class ScannerFragment extends Fragment {

    /* Required Empty Constructor */
    public ScannerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_scan, container, false);
        return view;
    }


}
