package com.example.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.TypeModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragRecAdapter extends RecyclerView.Adapter<MainFragRecAdapter.ViewHolder> {

    List<TypeModel> list;
    ClickingTuple clickingTuple;

    public MainFragRecAdapter(List<TypeModel> list, ClickingTuple clickingTuple) {
        this.list = list;
        this.clickingTuple = clickingTuple;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tuple_main_frag_rec, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TypeModel tuple = list.get(position);


        if(position == 2){
            holder.diet.setVisibility(View.VISIBLE);
        } else if(position == 5){
            holder.cuisine.setVisibility(View.VISIBLE);
        }
        holder.image.setImageResource(tuple.getImage());
        holder.fullName.setText(tuple.getFullName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView fullName, cuisine, diet;
        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.mainfragcardtext);
            cuisine = itemView.findViewById(R.id.cuisinetextmainfrag);
            diet = itemView.findViewById(R.id.dietstextmainfrag);
            image = itemView.findViewById(R.id.mainfragcardimage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickingTuple.clickedOnTuple(getAdapterPosition());
        }
    }
}
