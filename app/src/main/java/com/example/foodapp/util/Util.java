package com.example.foodapp.util;

import com.example.foodapp.R;
import com.example.foodapp.model.TypeModel;

import java.util.Arrays;
import java.util.List;

public class Util {

    public final static String apiKey = "f1562a6e338f42808f54f81403dd22bf";

    public static List<TypeModel> list = Arrays.asList(
            new TypeModel("random", "Random", R.drawable.random),
            new TypeModel("favourites", "Favourites", R.drawable.imgfav),
            new TypeModel("vegetarian", "Vegetarian", R.drawable.vegt),
            new TypeModel("vegan", "Vegan", R.drawable.vegan),
            new TypeModel("gluten", "Gluten Free", R.drawable.glutenfree),
            new TypeModel("italian", "Italian", R.drawable.italiya),
            new TypeModel("german", "German", R.drawable.almaniya),
            new TypeModel("japanese", "Japanese", R.drawable.yaponiya),
            new TypeModel("mexican", "Mexican", R.drawable.meksika),
            new TypeModel("thai", "Thai Food", R.drawable.thailand),
            new TypeModel("chinese", "Chinese", R.drawable.chin));

}
