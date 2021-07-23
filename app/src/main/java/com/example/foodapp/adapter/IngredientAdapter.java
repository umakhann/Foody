package com.example.foodapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.Ingredient;

import java.text.DecimalFormat;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    List<Ingredient> ingredients;
    Context context;

    public IngredientAdapter(List<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.ingredienttuple, parent, false);



        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        if(position == 0){
            holder.lin.setVisibility(View.VISIBLE);
        }

        holder.name.setText(ingredient.getName());

        DecimalFormat numberFormat = new DecimalFormat("#.00");
        holder.count.setText(numberFormat.format(ingredient.getAmount()) + " "
                + ingredient.getUnit());


    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, count;
        LinearLayout lin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lin = itemView.findViewById(R.id.lin);
            name = itemView.findViewById(R.id.ingrName);
            count = itemView.findViewById(R.id.ingrCount);


        }
    }
}
