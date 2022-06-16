package com.quangdau.greenhouse.ChildFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.quangdau.greenhouse.R;


public class fragment_child_graph1 extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_graph1, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("gh", "graph1 resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("gh", "graph1 paused");
    }
}