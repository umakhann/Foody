package com.example.foodapp.api.source;

import com.example.foodapp.util.Util;
import com.example.foodapp.model.Obyekt;
import com.example.foodapp.model.Recipe;

import java.util.List;
import com.example.foodapp.model.Random;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("recipes/complexSearch?apiKey=" + Util.apiKey + "&number=99")
    Call<Obyekt> getRecipes(@Query("cuisine") String cuisine);

    @GET("recipes/complexSearch?apiKey=" + Util.apiKey + "&number=99")
    Call<Obyekt> getDietRecipes(@Query("diet") String diet);

    @GET("recipes/complexSearch?apiKey=" + Util.apiKey + "&number=30")
    Call<Obyekt> getSearchRecipes(@Query("query") String query);

    @GET("recipes/{id}/information?includeNutrition=false&apiKey=" + Util.apiKey)
    Call<Recipe> getRecipeInfo(@Path("id") int id);

    @GET("recipes/{id}/similar?number=10&apiKey=" + Util.apiKey)
    Call<List<Recipe>> getSimilars(@Path("id") int id);

    @GET("recipes/random?number=51&apiKey=" + Util.apiKey)
    Call<Random> getRandomRecipes();




}
