package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeOfDay();

        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C4DFF")));


        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sandwiches);

        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView = findViewById(R.id.sandwiches_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position);
            }
        });
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }

    private void timeOfDay(){
        Calendar c = Calendar.getInstance();

        TextView introMessage = (TextView) findViewById(R.id.intro);

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
}
