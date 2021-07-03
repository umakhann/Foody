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

public class SearchFragRecyclerAdapter extends RecyclerView.Adapter<SearchFragRecyclerAdapter.ViewHolder> {

    private static ClickingTuple clickingTuple;
    private final List<Recipe> foodList;
    private final Context context;
    int rec = 0;

    public SearchFragRecyclerAdapter(List<Recipe> foodList, Context context, ClickingTuple tuple) {
        this.foodList = foodList;
        this.context = context;
        clickingTuple = tuple;
//        if(tuple instanceof RecipeDetailFrag) rec = 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_tuple, parent, false);
//        if(rec == 1) view = LayoutInflater
//                .from(parent.getContext()).inflate(R.layout.similartuple, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe rec = foodList.get(position);
        String str = new String();

        if(rec.getTitle().length() > 30){
            str = rec.getTitle().substring(0, 25).concat("...");
        } else {
            str = rec.getTitle();
        }

        holder.name.setText(str);

//        if(rec.getImage() != null) {
//            Picasso.get()
//                    .load(rec.getImage())
//                    .into(holder.mg);
//        }
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
//        ImageView mg;
        TextView name;
        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context = context;

//            mg = itemView.findViewById(R.id.imageofrecipe);
            name = itemView.findViewById(R.id.searchResultName);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickingTuple.clickedOnTuple(getAdapterPosition());
        }


    }


}
