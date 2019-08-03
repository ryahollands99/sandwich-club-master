package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONStringer;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    //Create variables for Tab
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    //Create variables for the views


    private ImageView mIngredientsIv;


    //Return to main page when back button is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId()) {
           case android.R.id.home:
               Intent intent = new Intent (this, MainActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
            return true;
           default:
           return super.onOptionsItemSelected(item);
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Show a back button in the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mIngredientsIv = (ImageView) findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
//            toolbar.setTitle(sandwich.getMainName());
            actionBar.setTitle(sandwich.getMainName());

        }catch(JSONException e){
            e.printStackTrace();
        }



        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutFragment(), "About");
        adapter.addFragment(new IngredientsFragment(),  "Ingredients");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientsIv);

//        setTitle(sandwich.getMainName());

    }

    //Custom method to generate a bulleted list
    private SpannableStringBuilder showBullet (List<String> textList){



        //Create a spannable string builder
        SpannableStringBuilder mSSBuilder = new SpannableStringBuilder();

       int end = 0;

        //take each word out of the list, replace the comma with a space, add the word to mSSBuilder with a new line
        // and add a bulletspan to the word
        for (String s : textList){
            //Initialize a new BulletSpan
            BulletSpan bulletSpan = new BulletSpan(10, Color.BLACK);
            s.replace(", ", "");
            mSSBuilder.append(s + "\n");
            int start = end;
            end = start + s.length() + 1;
            Log.v("Bullet", "Start and end index are " + start + " " + end + " for word " + s);
            mSSBuilder.setSpan(bulletSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        return mSSBuilder;
    }

}
