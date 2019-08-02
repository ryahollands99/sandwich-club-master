package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class AboutFragment extends Fragment {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mDescription;
    private TextView mAlsoKnownAs;
    private TextView mOrigin;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        intent = getActivity().getIntent();

        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return getView();
        }

        mDescription = (TextView) rootView.findViewById(R.id.description_tv);
        mAlsoKnownAs = (TextView) rootView.findViewById(R.id.also_known_tv);
        mOrigin = (TextView) rootView.findViewById(R.id.origin_tv);


        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return getView();
            }

            populateUI(sandwich);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return rootView;
    }

    private void closeOnError() {
        getActivity().getSupportFragmentManager().popBackStack();
        Toast.makeText(getContext(), R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {





        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();




        SpannableStringBuilder mSSBuilderAlsoKnownAs;


        mSSBuilderAlsoKnownAs = showBullet(alsoKnownAsList);


      mAlsoKnownAs.setText(mSSBuilderAlsoKnownAs, TextView.BufferType.SPANNABLE);
        mDescription.setText(sandwich.getDescription());
        mOrigin.setText(sandwich.getPlaceOfOrigin());

        Log.v("Tag", sandwich.getPlaceOfOrigin());
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
            //Log.v("Bullet", "Start and end index are " + start + " " + end + " for word " + s);
            mSSBuilder.setSpan(bulletSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        return mSSBuilder;
    }

}


