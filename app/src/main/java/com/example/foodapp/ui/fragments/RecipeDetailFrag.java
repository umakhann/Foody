package com.example.foodapp.ui.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.adapter.ClickingTuple;
import com.example.foodapp.adapter.IngredientAdapter;
import com.example.foodapp.adapter.RecAdapter;
import com.example.foodapp.source.RecipesRepo;
import com.example.foodapp.model.Ingredient;
import com.example.foodapp.model.Recipe;
import com.example.foodapp.source.RecipesViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailFrag extends BaseFragment implements ClickingTuple {

        public static final String TAG = "recipeDetTag";
        private RecyclerView recyclerView, simRec;
        private IngredientAdapter recyclerViewAdapter;
        private RecAdapter recAdapter;
        RecipesRepo repo;
        RecipesViewModel recipeViewModel;
        public List<Ingredient> list;
        public List<Recipe> recipeList;
        public ImageView vegInfo, share, favourite, back;
        private static Recipe recipe;
        public static List<Recipe> dbList;

        private ImageView recdetailimage;
        private ImageView vegan, vegetarian;
        private TextView summary;
        private CollapsingToolbarLayout title;
        public static boolean existence = false;


    public static RecipeDetailFrag getInstance(int id){

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        RecipeDetailFrag typeFragment = new RecipeDetailFrag();
        typeFragment.setArguments(bundle);


        return typeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_recipe_detail, container, false);

        share = view.findViewById(R.id.imagesharerecdet);
        favourite = view.findViewById(R.id.imagefavouritesrecdet);
        back = view.findViewById(R.id.imagebackrecdet);



        recdetailimage = view.findViewById(R.id.imageofrecdetail);
        title = view.findViewById(R.id.collapsingToolbar);

        title.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        title.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);



        summary = view.findViewById(R.id.summary);

        recipeViewModel = RecipesViewModel.getInstance();

        int id = getArguments().getInt("id");
        repo = new RecipesRepo(getActivity().getApplication());

        recipeViewModel.getCurrentRecipe(id).observe((LifecycleOwner) getActivity(),
                recipe -> {


                    RecipeDetailFrag.recipe = recipe;
                    Picasso.get().load(recipe.getImage()).into(recdetailimage);
                    title.setTitle(recipe.getTitle());

                    String instr = recipe.getInstructions();



                    if(instr != null){
                    summary.setText("INSTRUCTIONS\n\n" + instr);
                    } else  summary.setText("No Instructions!");


                    recyclerView = view.findViewById(R.id.ingrec);
                    recyclerViewAdapter = new IngredientAdapter(recipe.getExtendedIngredients(), RecipeDetailFrag.this.getActivity());

                    recyclerView.setLayoutManager(new LinearLayoutManager(RecipeDetailFrag.this.getActivity()));
                    recyclerView.setAdapter(recyclerViewAdapter);




                    repo.rowExists(id).observe((LifecycleOwner) this.getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean) {
                                Log.d("checkedExistence", "true");
                                favourite.setImageResource(R.drawable.fav);
                                existence = true;
                            }
                            else{
                                Log.d("checkedExistence", "true");
                                favourite.setImageResource(R.drawable.notfav);
                                existence = false;

                            }
                        }
                    });


                });



        recipeViewModel.getSimRecipes(id).observe((LifecycleOwner) getActivity(), recipes -> {

            recipeList = recipes;

            simRec = view.findViewById(R.id.similarrec);
            recAdapter = new RecAdapter(recipes, getActivity(), RecipeDetailFrag.this);

            simRec.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
            simRec.setAdapter(recAdapter);
        });

        share.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, recipe.getSourceUrl());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        favourite.setOnClickListener(v -> {

            Recipe ourRecipe = new Recipe();

            recipeViewModel.getCurrentRecipe(id).observe((LifecycleOwner) getActivity(), recipe -> {


                ourRecipe.setTitle(recipe.getTitle());
                ourRecipe.setIngredients(recipe.getExtendedIngredients().toString());
                ourRecipe.setId(recipe.getId());
                ourRecipe.setImage(recipe.getImage());

            });

            if (existence){
                RecipesViewModel.delete(recipe);
                favourite.setImageResource(R.drawable.notfav);
                existence = false;

            } else {
                RecipesViewModel.insert(ourRecipe);
                favourite.setImageResource(R.drawable.fav);
                existence = true;
            }

        });

        back.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());







        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void clickedOnTuple(int a) {

        if(isInternetOn()) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("rec")
                    .replace(R.id.frameL, getInstance(recipeList.get(a).getId()))
                    .commit();

            recipeViewModel.getCurrentRecipe(recipeList.get(a).getId());
            recipeViewModel.getSimRecipes(recipeList.get(a).getId());
            recipeViewModel.getIngredients();
            recipeViewModel.rowExists(recipeList.get(a).getId());

        } else {
            createToast("Ba??lant?? x??tas??");
        }
    }
}
