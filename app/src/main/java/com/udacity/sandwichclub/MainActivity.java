package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.udacity.sandwichclub.Adapter.SandwichAdapter;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements SandwichAdapter.RecyclerViewOnClickListener {

    private List<Sandwich> mSandwiches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }

        mSandwiches = initSandwiches();

       RecyclerView recyclerView = findViewById(R.id.sandwiches);
       SandwichAdapter adapter = new SandwichAdapter(mSandwiches, getApplication(), this);
       recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        timeOfDay();

        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C4DFF")));
        ab.setElevation(0f);
    }

   private List<Sandwich> initSandwiches() {

        List<Sandwich> sandwiches = new ArrayList<>();

       String[] sandwichString = getResources().getStringArray(R.array.sandwich_details);

       for(int i=0; i < sandwichString.length; i++ ){
            String json = sandwichString[i];
            try{
                Sandwich sandwich = JsonUtils.parseSandwichJson(json);
                sandwiches.add(sandwich);

            }catch (JSONException e){
                e.printStackTrace();
            }
       }
       return sandwiches;
   }

    private void timeOfDay(){
        Calendar c = Calendar.getInstance();

        TextView introMessage = findViewById(R.id.intro);

        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            introMessage.setText("Breakfast Time!  Let\'s pick a sandwich");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            introMessage.setText("Lunch Time!  Let\'s pick a sandwich for lunch");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            introMessage.setText("Dinner Time! Let\'s pick a sandwich for dinner");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            introMessage.setText("Midnight snack! Let\'s pick a sandwich");
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);

    }
}
