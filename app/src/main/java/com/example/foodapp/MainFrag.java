package com.example.foodapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.util.Util;
import com.example.foodapp.adapter.ClickingTuple;
import com.example.foodapp.adapter.MainFragRecAdapter;

public class MainFrag extends Fragment implements ClickingTuple {

    RecyclerView recyclerView;
    MainFragRecAdapter adapter;
    TextView searchText;

    public static MainFrag getInstance(){
        return new MainFrag();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.mainfrag, container, false);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchText = view.findViewById(R.id.searchtext);

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

        //        recipeViewModel = new ViewModelProvider(this)
//                .get(RecipesViewModel.class);
//
//        recipeViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
//            @Override
//            public void onChanged(List<Recipe> recipes) {
//
//
//            }
//        });




    }

    @Override
    public void clickedOnTuple(int a) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction().add(R.id.frameL, TypeFragment.getInstance(a))
                        .addToBackStack("type")
                        .commit();
        }
    }
