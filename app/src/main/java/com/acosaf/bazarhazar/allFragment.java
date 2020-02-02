package com.acosaf.bazarhazar;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class allFragment extends Fragment {

    ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        arrayList.clear();
        arrayList.add("food");
        arrayList.add("drinks");
        arrayList.add("hotel");
        arrayList.add("cinema");
        arrayList.add("bar");


        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_all);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter( new adapter_allAds(arrayList));
        return view;
    }

}
