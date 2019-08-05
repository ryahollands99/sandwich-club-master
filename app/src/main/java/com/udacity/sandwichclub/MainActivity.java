package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.udacity.sandwichclub.Adapter.SandwichAdapter;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

//    private RecyclerView sandwiches;
//    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }

      List<Sandwich> sandwiches = initSandwiches(intent);
//


       RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sandwiches);
       SandwichAdapter adapter = new SandwichAdapter(sandwiches, getApplication());
       recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

//       this.sandwiches = (RecyclerView) findViewById(R.id.sandwiches);
//       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//       this.sandwiches.setLayoutManager(mLayoutManager);

//       adapter = new SandwichAdapter(context, sandwiches);
//       this.sandwiches.setAdapter(adapter);

//        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);

//        ArrayList<Sandwich> sandwiches = initSandwiches();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, sandwiches);
//
//        // Simplification: Using a ListView instead of a RecyclerView
//        ListView listView = findViewById(R.id.sandwiches_listview);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                launchDetailActivity(position);
//            }
//        });
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }

   private List<Sandwich> initSandwiches(Intent intent) {

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


//        List<Sandwich> sandwiches = new List<String>;


//        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
//        if (position == DEFAULT_POSITION) {
//            // EXTRA_POSITION not found in intent
//            finish();
//        }
//
//        String[] sandwichString = getResources().getStringArray(R.array.sandwich_details);
//        String json = sandwichString[position];
//        Log.v("Intent", "Position is " + position);
//
//        try {
//            sandwich.add(JsonUtils.parseSandwichJson(json));
//
//                return sandwich;
//            }catch (JSONException e){
//            e.printStackTrace();
//            finish();
//        }
//          return sandwich;
//         }


}
