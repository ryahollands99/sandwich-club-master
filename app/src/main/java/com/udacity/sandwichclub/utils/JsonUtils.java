package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json)
            throws JSONException {

        //Names of JSON objects to be extracted
        final String NAME = "name";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";


        //Check if the String json is null or empty
        if (json == null || json.isEmpty()) {
            return null;
        }


        //Create a JSON object
        JSONObject sandwichJSON = new JSONObject(json);


        JSONObject name = sandwichJSON.getJSONObject(NAME);
        String placeOfOrigin = sandwichJSON.getString(PLACE_OF_ORIGIN);
        String description = sandwichJSON.getString(DESCRIPTION);
        String image = sandwichJSON.getString(IMAGE);

        JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");

        List<String> alsoKnownAs = convertToList(alsoKnownAsArray);


        JSONArray ingredientsArray = sandwichJSON.getJSONArray(INGREDIENTS);
        List<String> ingredientsList = convertToList(ingredientsArray);


        String mainName = name.getString("mainName");


        //Create a new Sandwich Object
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredientsList);


        return sandwich;

    }


    /**
     * A method that transforms a jsonArray into a List<String>
     * @param jsonArray
     * @return a List<String> that can be passed into the Sandwich object.
     */
    public static List<String> convertToList(JSONArray jsonArray) {
        List<String> itemList = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                String item = jsonArray.getString(i);
                Log.v("Tag", item);
                itemList.add("• " + item + "\n");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.v("Tag", itemList.toString());
        return itemList;
    }

}
