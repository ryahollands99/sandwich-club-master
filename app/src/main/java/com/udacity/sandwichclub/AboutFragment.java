package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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


        mDescription = rootView.findViewById(R.id.description_tv);
        mAlsoKnownAs = rootView.findViewById(R.id.also_known_tv);
        mOrigin = rootView.findViewById(R.id.origin_tv);


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
        String placeOfOrigin = sandwich.getPlaceOfOrigin();

        if (alsoKnownAsList.size() == 0){
           // alsoKnownAsList.add("No data is availble");
            mAlsoKnownAs.setText("Has no other name");
            mAlsoKnownAs.setTypeface(mAlsoKnownAs.getTypeface(), Typeface.ITALIC);
            mAlsoKnownAs.setLineSpacing(0.5f,0.9f);
        }


        SpannableStringBuilder mSSBuilderAlsoKnownAs;

        if (alsoKnownAsList.size() > 1) {

            String sentence = "";

            for (String otherName : alsoKnownAsList){
                if ((alsoKnownAsList.indexOf(otherName) + 1) == alsoKnownAsList.size()) {
                    sentence = sentence + otherName;
                }else{
                    sentence = sentence + otherName + ", ";
                }
            }
            mAlsoKnownAs.setText(sentence);
            mAlsoKnownAs.setLineSpacing(0f, 1.3f);
        } else if (alsoKnownAsList.size() == 1){

            for (String s : alsoKnownAsList){
                mAlsoKnownAs.setText(s);
                Log.v("Replaceable", "string is " + "\n");
                mAlsoKnownAs.setLineSpacing(0.5f,1.0f);
            }
        }

        if (placeOfOrigin.length() == 0){
            mOrigin.setText("Unknown");
            mOrigin.setTypeface(mOrigin.getTypeface(), Typeface.ITALIC);
        }else{
            mOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        mDescription.setText(sandwich.getDescription());

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


