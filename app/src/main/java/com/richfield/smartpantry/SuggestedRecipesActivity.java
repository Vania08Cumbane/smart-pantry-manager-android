package com.richfield.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView recycler;
    private TextView tvNoRecipes;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested);

        db = new DatabaseHelper(this);
        recycler = findViewById(R.id.recyclerRecipes);
        tvNoRecipes = findViewById(R.id.tvNoRecipes);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter(db.getSuggestedRecipes(), recipe -> {
            Intent i = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
            i.putExtra("recipe_id", recipe.getId());
            startActivity(i);
        });
        recycler.setAdapter(adapter);

        bottomNav.setSelectedItemId(R.id.nav_suggested);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_pantry) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_suggested) {
                return true;
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestions();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) bottomNav.setSelectedItemId(R.id.nav_suggested);
    }

    private void loadSuggestions() {
        List<Recipe> suggested = db.getSuggestedRecipes();
        adapter.updateData(suggested);
        if (suggested.isEmpty()) {
            recycler.setVisibility(View.GONE);
            tvNoRecipes.setVisibility(View.VISIBLE);
        } else {
            recycler.setVisibility(View.VISIBLE);
            tvNoRecipes.setVisibility(View.GONE);
        }
    }
}
