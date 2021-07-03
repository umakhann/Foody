package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.ClickingTuple;
import com.example.foodapp.adapter.IngredientAdapter;
import com.example.foodapp.adapter.RecAdapter;
import com.example.foodapp.repository.RecipesRepo;
import com.example.foodapp.model.Ingredient;
import com.example.foodapp.model.Recipe;
import com.example.foodapp.viewmodel.RecipesViewModel;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecipeDetailFrag extends Fragment implements ClickingTuple {


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

    private CircleImageView recdetailimage;
    private ImageView vegan, vegetarian;
    private TextView title, summary;
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

        View view = inflater.inflate(R.layout.recipedetail, container, false);

        vegInfo = view.findViewById(R.id.imagevegtrecdet);
        share = view.findViewById(R.id.imagesharerecdet);
        favourite = view.findViewById(R.id.imagefavouritesrecdet);
        back = view.findViewById(R.id.imagebackrecdet);



        recdetailimage = view.findViewById(R.id.imageofrecdetail);
        title = view.findViewById(R.id.nameofrecipedetail);
        summary = view.findViewById(R.id.summary);

        recipeViewModel = RecipesViewModel.getInstance();

        int id = getArguments().getInt("id");
        repo = new RecipesRepo(getActivity().getApplication());

        recipeViewModel.getCurrentRecipe(id).observe(getActivity(),
                recipe -> {


                    RecipeDetailFrag.recipe = recipe;
                    Picasso.get().load(recipe.getImage()).into(recdetailimage);
                    title.setText(recipe.getTitle());

                    if(recipe.getInstructions() != null)
                    summary.setText("Instructions\n\n " + recipe.getInstructions());
                    else  summary.setText("No Instructions!");

//                        Log.d("instructionlog", recipe.getInstructions());


                    recyclerView = view.findViewById(R.id.ingrec);
                    recyclerViewAdapter = new IngredientAdapter(recipe.getExtendedIngredients(), RecipeDetailFrag.this.getActivity());

                    recyclerView.setLayoutManager(new LinearLayoutManager(RecipeDetailFrag.this.getActivity()));
                    recyclerView.setAdapter(recyclerViewAdapter);




                    repo.rowExists(id).observe(getActivity(), new Observer<Boolean>() {
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



        recipeViewModel.getSimRecipes(id).observe(getActivity(), recipes -> {

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

            recipeViewModel.getCurrentRecipe(id).observe(getActivity(), recipe -> {


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
//
//    private boolean checkForExistence(Recipe recipe) {
//        if(repo.rowExists(recipe.getId()))
//        return true;
//        else
//        return false;
//    }

    @Override
    public void clickedOnTuple(int a) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("rec")
                .replace(R.id.frameL, getInstance(list.get(a).getId()))
                .commit();

        recipeViewModel.getCurrentRecipe(list.get(a).getId());
        recipeViewModel.getSimRecipes(list.get(a).getId());
        recipeViewModel.getIngredients();
        recipeViewModel.rowExists(list.get(a).getId());
    }
}
