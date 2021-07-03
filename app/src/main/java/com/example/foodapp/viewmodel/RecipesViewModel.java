package com.example.foodapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodapp.util.Util;
import com.example.foodapp.model.Ingredient;
import com.example.foodapp.model.Recipe;
import com.example.foodapp.repository.RecipesRepo;

import java.util.List;

public class RecipesViewModel extends AndroidViewModel {

    private static RecipesRepo repo;
    private static Application application;
    MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    MutableLiveData<List<Recipe>> simRecipes = new MutableLiveData<>();
    MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();
    MutableLiveData<Recipe> currentRecipe = new MutableLiveData<>();
    LiveData<List<Recipe>> dbList;


    public RecipesViewModel(@NonNull Application application) {
        super(application);

        RecipesViewModel.application = application;
        repo = new RecipesRepo(application);
        dbList = repo.getFavRecipes();
    }

    public static RecipesViewModel getInstance(){
        return new ViewModelProvider.AndroidViewModelFactory(application)
                .create(RecipesViewModel.class);
    }





    public MutableLiveData<List<Recipe>> getSearchResult(String query){
        return repo.getSearchRecipes(query);
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

    public LiveData<Recipe> getCurrentRecipe(int id) {
        return repo.getRecipeInfo(id);
    }

    public MutableLiveData<List<Recipe>> getRecipes(int type) {
        if(type == 0) {
            return repo.getRandomRecipes();

        }

        else if(type >=2 && type < 5){
            return repo.getDietRecipes(Util.list.get(type).getKeyName());

        } else
           return repo.getRecipes(Util.list.get(type).getKeyName());

    }

    public LiveData<List<Recipe>> getSimRecipes(int id) {
        return repo.getSimilars(id);
    }




    public LiveData<List<Recipe>> getDbList() {
        return repo.getFavRecipes();
    }

    public static void insert(Recipe recipe){ repo.insert(recipe);}

    public static void delete(Recipe recipe){repo.delete(recipe);}

    public LiveData<Boolean> rowExists(int id){
        return repo.rowExists(id);
    }





    public void setRecipes(List<Recipe> recipeList) {
        recipes.setValue(recipeList);
    }

    public void setIngredients(MutableLiveData<List<Ingredient>> ingredients) {
        this.ingredients = ingredients;
    }

    public void setRecipes(MutableLiveData<List<Recipe>> recipes) {
        this.recipes = recipes;
    }
}
