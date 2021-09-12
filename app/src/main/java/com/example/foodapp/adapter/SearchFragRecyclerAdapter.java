package com.example.foodapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.Recipe;

import java.util.List;

public class SearchFragRecyclerAdapter extends RecyclerView.Adapter<SearchFragRecyclerAdapter.ViewHolder> {

    private static ClickingTuple clickingTuple;
    private final List<Recipe> foodList;
    private final Context context;
    int rec = 0;

    public SearchFragRecyclerAdapter(List<Recipe> foodList, Context context, ClickingTuple tuple) {
        this.foodList = foodList;
        this.context = context;
        clickingTuple = tuple;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tuple_search, parent, false);

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


        TextView name;
        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context = context;
            name = itemView.findViewById(R.id.searchResultName);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickingTuple.clickedOnTuple(getAdapterPosition());
        }


    }


}
