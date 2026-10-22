package com.richfield.smartpantry;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        long recipeId = getIntent().getLongExtra("recipe_id", -1);
        if (recipeId < 0) {
            finish();
            return;
        }

        DatabaseHelper db = new DatabaseHelper(this);
        Recipe recipe = db.getRecipeById(recipeId);
        if (recipe == null) {
            finish();
            return;
        }

        TextView tvTitle = findViewById(R.id.tvRecipeTitle);
        TextView tvIngredients = findViewById(R.id.tvIngredients);
        TextView tvMethod = findViewById(R.id.tvMethod);

        tvTitle.setText(recipe.getName());

        StringBuilder sb = new StringBuilder();
        for (RecipeIngredient ri : recipe.getIngredients()) {
            String qty = (ri.getQuantity() == (long) ri.getQuantity())
                    ? String.valueOf((long) ri.getQuantity())
                    : String.valueOf(ri.getQuantity());
            sb.append("• ").append(qty).append(" ")
                    .append(ri.getUnit() != null ? ri.getUnit() : "")
                    .append(" ").append(ri.getName()).append("\n");
        }
        tvIngredients.setText(sb.toString().trim());
        tvMethod.setText(recipe.getMethod());
    }
}
