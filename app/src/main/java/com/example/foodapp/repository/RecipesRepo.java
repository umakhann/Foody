package com.example.foodapp.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.api.source.ApiInterface;
import com.example.foodapp.api.source.Retro;
import com.example.foodapp.database.RecipeDao;
import com.example.foodapp.database.RecipeDatabase;
import com.example.foodapp.model.Obyekt;
import com.example.foodapp.model.Random;
import com.example.foodapp.model.Recipe;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesRepo {


    private String b;
    private Application context;
    private Obyekt obyekt;
    private RecipeDao dao;
    MutableLiveData<Recipe> recipe = new MutableLiveData<>();
    LiveData<List<Recipe>> dbList = new MutableLiveData<>();;
    MutableLiveData<List<Recipe>> list = new MutableLiveData<>();

    public RecipesRepo(Application application) {
        context = application;
    }


    public MutableLiveData<List<Recipe>> getDietRecipes(String diet) {
        ApiInterface apiInterface = Retro.getRetrofit().create(ApiInterface.class);
        Call<Obyekt> call = apiInterface.getDietRecipes(diet);

        call.enqueue(new Callback<Obyekt>() {
            @Override
            public void onResponse(Call<Obyekt> call, Response<Obyekt> response) {

                list.setValue(response.body().getResults());


                for(int i=0; i<response.body().getResults().size(); i++) {
                    Log.d("tag", response.body().getResults().get(i).getTitle());
                }
            }
            @Override
            public void onFailure(Call<Obyekt> call, Throwable t) {
                Log.d("tag", "onFailure");
            }
        });


        return list;
    }


    public LiveData<Boolean> rowExists(int id){
        RecipeDatabase db = RecipeDatabase.getDatabase(context);
        dao = db.recipeDao();

        LiveData<Boolean> a = dao.rowExists(id);

//            if(dbList.getValue().size() > 0){
//        Log.d("taggg", dbList.getValue().get(0).getTitle());}

        return a;
    }

    public MutableLiveData<List<Recipe>> getRecipes(String cuisine) {
        ApiInterface apiInterface = Retro.getRetrofit().create(ApiInterface.class);
        Call<Obyekt> call = apiInterface.getRecipes(cuisine);

        call.enqueue(new Callback<Obyekt>() {
            @Override
            public void onResponse(Call<Obyekt> call, Response<Obyekt> response) {

                list.setValue(response.body().getResults());


                for(int i=0; i<response.body().getResults().size(); i++) {
                    Log.d("tag", response.body().getResults().get(i).getTitle());
                }
            }
            @Override
            public void onFailure(Call<Obyekt> call, Throwable t) {
                Log.d("tag", "onFailure");
            }
        });


        return list;
    }

    public MutableLiveData<Recipe> getRecipeInfo(int id) {
        ApiInterface apiInterface = Retro.getRetrofit().create(ApiInterface.class);
        Call<Recipe> call = apiInterface.getRecipeInfo(id);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {

                recipe.setValue(response.body());

                    Log.d("taggg", response.body().getTitle());


    }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }


        });
//        Log.d("taggg", recipe.getValue().getTitle());
        return recipe;
    }

    public MutableLiveData<List<Recipe>> getSimilars(int id) {
        ApiInterface apiInterface = Retro.getRetrofit().create(ApiInterface.class);
        Call<List<Recipe>> call = apiInterface.getSimilars(id);

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                list.setValue(response.body());

                for(int i=0; i<response.body().size(); i++) {
                    Log.d("tag", response.body().get(i).getTitle());
                }
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("tag", "onFailure");
            }
        });


        return list;
    }

    public MutableLiveData<List<Recipe>> getRandomRecipes() {
        ApiInterface apiInterface = Retro.getRetrofit().create(ApiInterface.class);
        Call<Random> call = apiInterface.getRandomRecipes();

        call.enqueue(new Callback<Random>() {
            @Override
            public void onResponse(Call<Random> call, Response<Random> response) {

                list.setValue(response.body().getRecipes());

                for(int i=0; i<response.body().getRecipes().size(); i++) {
                    Log.d("tag", response.body().getRecipes().get(i).getTitle());
                }
            }
            @Override
            public void onFailure(Call<Random> call, Throwable t) {
                Log.d("tag", "onFailure");
            }
        });


        return list;
    }

    public MutableLiveData<List<Recipe>> getSearchRecipes(String query) {
        ApiInterface apiInterface = Retro.getRetrofit().create(ApiInterface.class);
        Call<Obyekt> call = apiInterface.getSearchRecipes(query);

        call.enqueue(new Callback<Obyekt>() {
            @Override
            public void onResponse(Call<Obyekt> call, Response<Obyekt> response) {

                list.setValue(response.body().getResults());


                for(int i=0; i<response.body().getResults().size(); i++) {
                    Log.d("tag", response.body().getResults().get(i).getTitle());
                }
            }
            @Override
            public void onFailure(Call<Obyekt> call, Throwable t) {
                Log.d("tag", "onFailure");
            }
        });


        return list;
    }




    public LiveData<List<Recipe>> getFavRecipes() {

            RecipeDatabase db = RecipeDatabase.getDatabase(context);
            dao = db.recipeDao();

            dbList = dao.getAllRecipes();

//            if(dbList.getValue().size() > 0){
//        Log.d("taggg", dbList.getValue().get(0).getTitle());}

        return dbList;
    }

    public void insert(Recipe recipe) {

        RecipeDatabase.databaseWriteExecutor.execute(() ->
                dao.insert(recipe));

    }

    public void delete(Recipe recipe) {
        RecipeDatabase.databaseWriteExecutor.execute(() ->
                dao.delete(recipe));
    }

    private static class InsertRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDao recipeDao;
        private InsertRecipeAsyncTask(RecipeDao noteDao) {
            this.recipeDao = recipeDao;
        }
        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.insert(recipes[0]);
            return null;
        }
    }

    private static class DeleteRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDao recipeDao;
        private DeleteRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }
        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.delete(recipes[0]);
            return null;
        }
    }

    private static class GetAllRecipesAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecipeDao recipeDao;
        private GetAllRecipesAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            recipeDao.getAllRecipes();
            return null;
        }
    }
}
