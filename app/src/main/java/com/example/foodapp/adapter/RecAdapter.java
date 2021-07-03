package com.example.foodapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.RecipeDetailFrag;
import com.example.foodapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.foodapp.R.color.black;
import static com.example.foodapp.R.color.naiveblue;
import static com.example.foodapp.R.color.purple_200;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

    private static ClickingTuple clickingTuple;
    private final List<Recipe> foodList;
    private final Context context;
    int rec = 0;

    public RecAdapter(List<Recipe> foodList, Context context, ClickingTuple tuple) {
        this.foodList = foodList;
        this.context = context;
        clickingTuple = tuple;
        if(tuple instanceof RecipeDetailFrag) rec = 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tuple, parent, false);
        if(rec == 1) view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.similartuple, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Recipe recipant = foodList.get(position);
            String str = recipant.getTitle().substring(0, 1).toUpperCase() + recipant.getTitle().substring(1).toLowerCase();

            if(recipant.getTitle().length() > 30 && rec == 0){
                str = str.substring(0, 25).concat("...");
            } else if(recipant.getTitle().length() > 50 && rec == 1) {
                str = str.substring(0, 50).concat("...");

            }

            if(rec == 1 && position == 0){
                holder.basliq.setVisibility(View.VISIBLE);
            }

            holder.name.setText(str);

            if(recipant.getImage() != null) {
                Picasso.get()
                        .load(recipant.getImage())
                        .into(holder.mg);
            }
    }

    @Override
    public int getItemCount() {
        if(foodList == null){
            Log.d("tag", "null list");
            return 0;
        }
        else
        return foodList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

        List<Integer> selectedList;
        ImageView mg;
        TextView name, basliq;
        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context = context;

            mg = itemView.findViewById(R.id.imageofrecipe);
            name = itemView.findViewById(R.id.nameofrecipe);
            basliq = itemView.findViewById(R.id.bas);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickingTuple.clickedOnTuple(getAdapterPosition());
        }


    }


}
