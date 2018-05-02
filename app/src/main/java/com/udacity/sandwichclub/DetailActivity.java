package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mPlaceOfOriginTv;
    TextView mAlsoKnownAsTv;
    TextView mDescriptionTv;
    TextView mIngredientsTv;

    Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mPlaceOfOriginTv = findViewById(R.id.origin_tv);
        mAlsoKnownAsTv = findViewById(R.id.also_known_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);


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
        mSandwich = null;
        try {
            mSandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mPlaceOfOriginTv.setText(mSandwich.getPlaceOfOrigin());

        List<String> ingredientsList = mSandwich.getIngredients();
        StringBuilder ingredientsString = new StringBuilder();
        for(String str : ingredientsList){
            ingredientsString.append(str).append("\n");
        }
        mIngredientsTv.setText(ingredientsString);

        List<String> alsoKnownAsList = mSandwich.getAlsoKnownAs();
        StringBuilder alsoKnownAsString = new StringBuilder();
        for(String str : alsoKnownAsList){
            alsoKnownAsString.append(str).append("\n");
        }
        mAlsoKnownAsTv.setText(alsoKnownAsString);

        mDescriptionTv.setText(mSandwich.getDescription());

    }
}
