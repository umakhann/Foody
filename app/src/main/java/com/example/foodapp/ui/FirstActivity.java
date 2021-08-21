package com.example.foodapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.foodapp.R;
import com.example.foodapp.source.RecipesViewModel;
import com.example.foodapp.ui.fragments.MainFrag;

public class FirstActivity extends AppCompatActivity {

    private static int typeNum;
    public RecipesViewModel recipeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_empty);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameL, MainFrag.getInstance())
                .commit();

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.frameL, SearchFrag.getInstance())
//                .addToBackStack("ty").commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("interv", "onResume");
    }
}