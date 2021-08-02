package com.example.foodapp.util;

import com.example.foodapp.BuildConfig;
import com.example.foodapp.R;
import com.example.foodapp.model.TypeModel;

import java.util.Arrays;
import java.util.List;

public class Util {

    public final static String apiKey = BuildConfig.API_KEY;

    public static List<TypeModel> list = Arrays.asList(
            new TypeModel("random", "Random", R.drawable.shuffling),
            new TypeModel("favourites", "Favourites", R.drawable.fav),
            new TypeModel("vegetarian", "Vegetarian", R.drawable.vegeterian),
            new TypeModel("vegan", "Vegan", R.drawable.vegann),
            new TypeModel("gluten", "Gluten Free", R.drawable.gluten),
            new TypeModel("italian", "Italian", R.drawable.spaghetti),
            new TypeModel("german", "German", R.drawable.sausages),
            new TypeModel("japanese", "Japanese", R.drawable.sushi),
            new TypeModel("mexican", "Mexican", R.drawable.taco),
            new TypeModel("thai", "Thai Food", R.drawable.tomyumgoong),
            new TypeModel("chinese", "Chinese", R.drawable.chinesefood));

}
