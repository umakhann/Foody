package com.example.foodapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.util.Util;
import com.example.foodapp.adapter.ClickingTuple;
import com.example.foodapp.adapter.RecAdapter;
import com.example.foodapp.model.Recipe;
import com.example.foodapp.viewmodel.RecipesViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TypeFragment extends Fragment implements ClickingTuple {

    private RecyclerView recyclerView;
    private RecAdapter recyclerViewAdapter;
    RecipesViewModel recipeViewModel;
    public static List<Recipe> list;
    TextView typename;
    CircleImageView typeimage;
    ImageView back;



    public static TypeFragment getInstance(int type){

        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        TypeFragment typeFragment = new TypeFragment();
        typeFragment.setArguments(bundle);


        return typeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.typefrag, container, false);

        typename = view.findViewById(R.id.typename);
        typeimage = view.findViewById(R.id.typeimage);

        int type = getArguments().getInt("type");

        recipeViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(RecipesViewModel.class);

        if(type == 1){
            recipeViewModel.getDbList().observe(getActivity(), recipes -> {
                setAdapter(recipes, view, type);
            });
        } else {

            recipeViewModel.getRecipes(type).observe(getActivity(), recipes -> {
                setAdapter(recipes, view, type);
            });
        }




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void setAdapter(List<Recipe> recipes, View view, int type) {
        list = recipes;

        typename.setText(Util.list.get(type).getFullName());
        typeimage.setImageResource(Util.list.get(type).getImage());


        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerViewAdapter = new RecAdapter(recipes, getActivity(), TypeFragment.this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    @Override
    public void clickedOnTuple(int pos) {



       getActivity().getSupportFragmentManager()
               .beginTransaction()
               .addToBackStack("rec")
               .replace(R.id.frameL, RecipeDetailFrag.getInstance(list.get(pos).getId()))
       .commit();
    }

}
