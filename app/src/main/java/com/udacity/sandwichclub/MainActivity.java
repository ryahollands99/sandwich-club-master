package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private RecyclerView sandwiches;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       ArrayList<Sandwich> sandwiches = initSandwiches();

       this.sandwiches = (RecyclerView) findViewByID(R.id.sandwiches);
       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
       this.sandwiches.setLayoutManager(mLayoutManager);

       adapter = new SandwichAdapter(sandwiches);
       this.sandwiches.setAdapter(adapter);

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

    private ArrayList<Sandwich> initSandwiches() {
        ArrayList<Sandwich> list = new ArrayList<>();
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            finish();
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            }catch (JSONException e){
            e.printStackTrace();
            finish();
        }
            return list;
        }


}
