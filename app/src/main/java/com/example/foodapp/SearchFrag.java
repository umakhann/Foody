package com.example.foodapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.ClickingTuple;
import com.example.foodapp.adapter.SearchFragRecyclerAdapter;
import com.example.foodapp.model.Recipe;
import com.example.foodapp.repository.RecipesRepo;
import com.example.foodapp.viewmodel.RecipesViewModel;

import java.util.List;

public class SearchFrag extends Fragment implements ClickingTuple {

    List<Recipe> list;
    EditText edittext;
    RecipesRepo repo;
    RecyclerView recyclerView;
    SearchFragRecyclerAdapter adapter;
    RecipesViewModel recipesViewModel;
    ImageView back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_frag, container, false);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edittext = view.findViewById(R.id.editTextTextPersonName);
        back = view.findViewById(R.id.imagebackrecdet);

        back.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        recipesViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(RecipesViewModel.class);

        recipesViewModel.getSearchResult("thisIsForEmptyResult").observe(getActivity(), recipes -> {

            list = recipes;
        recyclerView = view.findViewById(R.id.searchrec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SearchFragRecyclerAdapter(recipes, getActivity(), this));




    });

        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = edittext.getText().toString();

                if(text.length() > 0)
                recipesViewModel.getSearchResult(text);
                else recipesViewModel.getSearchResult("thisIsForEmptyResult");
            }
        });
    }

    public static SearchFrag getInstance(){
        return new SearchFrag();
    }



    @Override
    public void clickedOnTuple(int a) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("rec")
                .replace(R.id.frameL, RecipeDetailFrag.getInstance(list.get(a).getId()))
                .commit();
    }
}
