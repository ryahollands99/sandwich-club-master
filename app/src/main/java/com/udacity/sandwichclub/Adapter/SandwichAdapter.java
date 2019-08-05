package com.udacity.sandwichclub.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.View_Holder> {


    private List<Sandwich> sandwiches = Collections.emptyList();
    Context context;

    public SandwichAdapter(List<Sandwich> sandwiches, Context context) {
        this.sandwiches = sandwiches;
        this.context = context;
    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initiate the ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sandwich, parent, false);
        return new View_Holder(v);
    }


    @Override
    public void onBindViewHolder(View_Holder holder, int position) {


        holder.name.setText(sandwiches.get(position).getMainName());


        Picasso.with(context).load(sandwiches.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (sandwiches != null) {
            return sandwiches.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Sandwich sandwich) {
        sandwiches.add(position, sandwich);
    }

    public void remove (Sandwich sandwich){
        int position = sandwiches.indexOf(sandwich);
        sandwiches.remove(sandwich);
        notifyItemRemoved(position);
    }

    public static class View_Holder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView name;
        ImageView image;

        public View_Holder(View view) {
            super(view);
            mCardView = (CardView) view.findViewById(R.id.cardView);
            name = view.findViewById(R.id.sandwich_name);
            image = view.findViewById(R.id.sandwich_image);

        }


    }
}
