package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichJson = new JSONObject(json);
        JSONObject name = sandwichJson.getJSONObject("name");
        String mainName = name.getString("mainName");
        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAsList = new ArrayList<>();
        for(int i = 0; i < alsoKnownAs.length(); i++){
            alsoKnownAsList.add(alsoKnownAs.getString(i));
        }
        String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
        String description = sandwichJson.getString("description");
        String image = sandwichJson.getString("image");
        JSONArray ingredients = sandwichJson.getJSONArray("ingredients");
        List<String> ingredientList = new ArrayList<>();
        for(int i = 0; i < ingredients.length(); i++){
            ingredientList.add(ingredients.getString(i));
        }
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientList);
        return sandwich;
    }
}
