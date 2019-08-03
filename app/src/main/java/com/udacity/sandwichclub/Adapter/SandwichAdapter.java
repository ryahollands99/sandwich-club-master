package com.udacity.sandwichclub.Adapter;

import android.support.v7.widget.RecyclerView;
import=7 android.view.LayoutInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;

public class SandwichAdapter extends RecyclerView.Adapter{



    private ArrayList<Sandwich> sandwiches;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sandwich, parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sandwich sandwich = sandwiches.get(position);

        holder.name.setText(sandwich.getMainName());

        Picasso.load(sandwich.getImage()).into(R.id.sandwich_image));
    }7

    @Override
    public int getItemCount() {
        if (sandwiches != null){
            return sandwiches.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView name;
        public final ImageView image;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            name = view.findViewById(R.id.sandwich_name);
            image = view.findViewById(R.id.sandwich_image)

        }


}
