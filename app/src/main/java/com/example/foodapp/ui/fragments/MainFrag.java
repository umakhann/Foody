package com.example.foodapp.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.util.Util;
import com.example.foodapp.adapter.ClickingTuple;
import com.example.foodapp.adapter.MainFragRecAdapter;

public class MainFrag extends BaseFragment implements ClickingTuple {

    RecyclerView recyclerView;
    MainFragRecAdapter adapter;
    ImageButton searchText;

    public static MainFrag getInstance(){
        return new MainFrag();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.frag_main, container, false);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchText = view.findViewById(R.id.searchmg);

        searchText.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameL, new SearchFrag())
                    .addToBackStack("search")
                    .commit();
        });

        recyclerView = view.findViewById(R.id.mainfragrec);
        adapter = new MainFragRecAdapter(Util.list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);






    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void clickedOnTuple(int a) {

        if(isInternetOn()){
                getActivity().getSupportFragmentManager()
                        .beginTransaction().add(R.id.frameL, TypeFragment.getInstance(a))
                        .addToBackStack("type")
                        .commit();}

        else {
            createToast("Bağlantı xətası");
        }
        }




    }
