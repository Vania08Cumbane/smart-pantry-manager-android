package com.richfield.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    // Pantry table
    public static final String TABLE_PANTRY = "pantry";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QTY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY = "expiry";

    // Recipes table
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "id";
    public static final String COL_RECIPE_NAME = "name";
    public static final String COL_METHOD = "method";

    // Recipe ingredients table
    public static final String TABLE_RECIPE_ING = "recipe_ingredients";
    public static final String COL_RI_ID = "id";
    public static final String COL_RI_RECIPE_ID = "recipe_id";
    public static final String COL_RI_NAME = "ingredient_name";
    public static final String COL_RI_QTY = "quantity";
    public static final String COL_RI_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_QTY + " REAL NOT NULL, " +
                COL_UNIT + " TEXT, " +
                COL_EXPIRY + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (" +
                COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT NOT NULL, " +
                COL_METHOD + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_RECIPE_ING + " (" +
                COL_RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RI_RECIPE_ID + " INTEGER, " +
                COL_RI_NAME + " TEXT NOT NULL, " +
                COL_RI_QTY + " REAL NOT NULL, " +
                COL_RI_UNIT + " TEXT, " +
                "FOREIGN KEY(" + COL_RI_RECIPE_ID + ") REFERENCES " + TABLE_RECIPES + "(" + COL_RECIPE_ID + "))");

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_ING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        // 18 simple recipes – enough to meet 15-20 requirement
        addRecipe(db, "Tomato Scramble",
                "1. Beat eggs.\n2. Chop tomatoes and fry lightly.\n3. Add eggs and scramble.\n4. Season and serve.",
                new String[]{"eggs", "tomato", "salt"},
                new double[]{2, 1, 1},
                new String[]{"pcs", "pcs", "pinch"});

        addRecipe(db, "Simple Pasta",
                "1. Boil pasta.\n2. Heat oil, add garlic.\n3. Toss pasta with oil and salt.\n4. Serve hot.",
                new String[]{"pasta", "garlic", "oil", "salt"},
                new double[]{200, 2, 2, 1},
                new String[]{"g", "cloves", "tbsp", "pinch"});

        addRecipe(db, "Rice and Beans",
                "1. Cook rice.\n2. Heat beans with a little salt.\n3. Serve beans over rice.",
                new String[]{"rice", "beans", "salt"},
                new double[]{150, 200, 1},
                new String[]{"g", "g", "pinch"});

        addRecipe(db, "Fried Egg on Toast",
                "1. Toast bread.\n2. Fry egg in oil.\n3. Place egg on toast and season.",
                new String[]{"bread", "eggs", "oil", "salt"},
                new double[]{2, 1, 1, 1},
                new String[]{"slices", "pcs", "tbsp", "pinch"});

        addRecipe(db, "Banana Smoothie",
                "1. Peel banana.\n2. Blend with milk and a pinch of sugar.\n3. Serve cold.",
                new String[]{"banana", "milk", "sugar"},
                new double[]{1, 200, 1},
                new String[]{"pcs", "ml", "tsp"});

        addRecipe(db, "Cheese Omelette",
                "1. Beat eggs.\n2. Pour into pan, add cheese.\n3. Fold and cook until set.",
                new String[]{"eggs", "cheese", "oil", "salt"},
                new double[]{3, 50, 1, 1},
                new String[]{"pcs", "g", "tbsp", "pinch"});

        addRecipe(db, "Garlic Bread",
                "1. Mix oil and crushed garlic.\n2. Spread on bread slices.\n3. Toast until golden.",
                new String[]{"bread", "garlic", "oil"},
                new double[]{4, 3, 2},
                new String[]{"slices", "cloves", "tbsp"});

        addRecipe(db, "Potato Hash",
                "1. Dice potatoes.\n2. Fry in oil until golden.\n3. Season with salt and serve.",
                new String[]{"potato", "oil", "salt"},
                new double[]{2, 2, 1},
                new String[]{"pcs", "tbsp", "pinch"});

        addRecipe(db, "Milk Porridge",
                "1. Heat milk.\n2. Stir in oats.\n3. Cook until thick, add sugar if desired.",
                new String[]{"milk", "oats", "sugar"},
                new double[]{300, 50, 1},
                new String[]{"ml", "g", "tsp"});

        addRecipe(db, "Vegetable Stir Fry",
                "1. Chop onion, carrot and cabbage.\n2. Stir-fry in oil.\n3. Season and serve.",
                new String[]{"onion", "carrot", "cabbage", "oil", "salt"},
                new double[]{1, 1, 100, 2, 1},
                new String[]{"pcs", "pcs", "g", "tbsp", "pinch"});

        addRecipe(db, "Peanut Butter Toast",
                "1. Toast bread.\n2. Spread peanut butter.\n3. Optional: add banana slices.",
                new String[]{"bread", "peanut butter"},
                new double[]{2, 2},
                new String[]{"slices", "tbsp"});

        addRecipe(db, "Boiled Eggs",
                "1. Place eggs in water.\n2. Boil for 8-10 minutes.\n3. Cool and peel.",
                new String[]{"eggs", "salt"},
                new double[]{2, 1},
                new String[]{"pcs", "pinch"});

        addRecipe(db, "Cucumber Salad",
                "1. Slice cucumber and tomato.\n2. Add a little salt and oil.\n3. Toss and serve.",
                new String[]{"cucumber", "tomato", "oil", "salt"},
                new double[]{1, 1, 1, 1},
                new String[]{"pcs", "pcs", "tbsp", "pinch"});

        addRecipe(db, "Apple Cinnamon",
                "1. Slice apple.\n2. Sprinkle cinnamon and a little sugar.\n3. Eat as is or warm briefly.",
                new String[]{"apple", "cinnamon", "sugar"},
                new double[]{1, 0.5, 1},
                new String[]{"pcs", "tsp", "tsp"});

        addRecipe(db, "Simple Soup",
                "1. Boil water with salt.\n2. Add diced potato and onion.\n3. Simmer until soft.",
                new String[]{"potato", "onion", "salt", "water"},
                new double[]{2, 1, 1, 500},
                new String[]{"pcs", "pcs", "pinch", "ml"});

        addRecipe(db, "Yogurt with Honey",
                "1. Spoon yogurt into a bowl.\n2. Drizzle honey.\n3. Optional: add fruit.",
                new String[]{"yogurt", "honey"},
                new double[]{150, 1},
                new String[]{"g", "tbsp"});

        addRecipe(db, "Carrot Sticks",
                "1. Wash and peel carrots.\n2. Cut into sticks.\n3. Serve raw as a snack.",
                new String[]{"carrot"},
                new double[]{2},
                new String[]{"pcs"});

        addRecipe(db, "Onion Omelette",
                "1. Slice onion and fry lightly.\n2. Pour beaten eggs over.\n3. Cook until set, season.",
                new String[]{"eggs", "onion", "oil", "salt"},
                new double[]{2, 1, 1, 1},
                new String[]{"pcs", "pcs", "tbsp", "pinch"});
    }

    private void addRecipe(SQLiteDatabase db, String name, String method,
                           String[] ingredients, double[] qtys, String[] units) {
        ContentValues cv = new ContentValues();
        cv.put(COL_RECIPE_NAME, name);
        cv.put(COL_METHOD, method);
        long recipeId = db.insert(TABLE_RECIPES, null, cv);

        for (int i = 0; i < ingredients.length; i++) {
            ContentValues ing = new ContentValues();
            ing.put(COL_RI_RECIPE_ID, recipeId);
            ing.put(COL_RI_NAME, ingredients[i]);
            ing.put(COL_RI_QTY, qtys[i]);
            ing.put(COL_RI_UNIT, units[i]);
            db.insert(TABLE_RECIPE_ING, null, ing);
        }
    }

    // ---------- Pantry CRUD ----------

    public long addPantryItem(String name, double qty, String unit, String expiry) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name.trim());
        cv.put(COL_QTY, qty);
        cv.put(COL_UNIT, unit == null ? "" : unit.trim());
        cv.put(COL_EXPIRY, expiry == null ? "" : expiry.trim());
        long id = db.insert(TABLE_PANTRY, null, cv);
        db.close();
        return id;
    }

    public int updatePantryItem(long id, String name, double qty, String unit, String expiry) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name.trim());
        cv.put(COL_QTY, qty);
        cv.put(COL_UNIT, unit == null ? "" : unit.trim());
        cv.put(COL_EXPIRY, expiry == null ? "" : expiry.trim());
        int rows = db.update(TABLE_PANTRY, cv, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public int deletePantryItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_PANTRY, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_PANTRY, null, null, null, null, null, COL_NAME + " ASC");
        if (c.moveToFirst()) {
            do {
                list.add(new PantryItem(
                        c.getLong(c.getColumnIndexOrThrow(COL_ID)),
                        c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                        c.getDouble(c.getColumnIndexOrThrow(COL_QTY)),
                        c.getString(c.getColumnIndexOrThrow(COL_UNIT)),
                        c.getString(c.getColumnIndexOrThrow(COL_EXPIRY))
                ));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public PantryItem getPantryItem(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_PANTRY, null, COL_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        PantryItem item = null;
        if (c.moveToFirst()) {
            item = new PantryItem(
                    c.getLong(c.getColumnIndexOrThrow(COL_ID)),
                    c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                    c.getDouble(c.getColumnIndexOrThrow(COL_QTY)),
                    c.getString(c.getColumnIndexOrThrow(COL_UNIT)),
                    c.getString(c.getColumnIndexOrThrow(COL_EXPIRY))
            );
        }
        c.close();
        db.close();
        return item;
    }

    // ---------- Recipes & Strict Matching ----------

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_RECIPES, null, null, null, null, null, COL_RECIPE_NAME + " ASC");
        if (c.moveToFirst()) {
            do {
                long id = c.getLong(c.getColumnIndexOrThrow(COL_RECIPE_ID));
                String name = c.getString(c.getColumnIndexOrThrow(COL_RECIPE_NAME));
                String method = c.getString(c.getColumnIndexOrThrow(COL_METHOD));
                List<RecipeIngredient> ings = getIngredientsForRecipe(db, id);
                recipes.add(new Recipe(id, name, method, ings));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return recipes;
    }

    private List<RecipeIngredient> getIngredientsForRecipe(SQLiteDatabase db, long recipeId) {
        List<RecipeIngredient> list = new ArrayList<>();
        Cursor c = db.query(TABLE_RECIPE_ING, null, COL_RI_RECIPE_ID + "=?",
                new String[]{String.valueOf(recipeId)}, null, null, null);
        if (c.moveToFirst()) {
            do {
                list.add(new RecipeIngredient(
                        c.getString(c.getColumnIndexOrThrow(COL_RI_NAME)),
                        c.getDouble(c.getColumnIndexOrThrow(COL_RI_QTY)),
                        c.getString(c.getColumnIndexOrThrow(COL_RI_UNIT))
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public Recipe getRecipeById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_RECIPES, null, COL_RECIPE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        Recipe recipe = null;
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndexOrThrow(COL_RECIPE_NAME));
            String method = c.getString(c.getColumnIndexOrThrow(COL_METHOD));
            List<RecipeIngredient> ings = getIngredientsForRecipe(db, id);
            recipe = new Recipe(id, name, method, ings);
        }
        c.close();
        db.close();
        return recipe;
    }

    /**
     * STRICT matching: every required ingredient must be present in the pantry
     * with quantity >= required quantity.
     * Name matching is normalised (lowercase, trim, basic plural handling).
     */
    public List<Recipe> getSuggestedRecipes() {
        List<PantryItem> pantry = getAllPantryItems();
        Map<String, Double> pantryMap = new HashMap<>();
        for (PantryItem p : pantry) {
            String key = normalise(p.getName());
            // if same ingredient appears more than once, sum quantities
            pantryMap.put(key, pantryMap.getOrDefault(key, 0.0) + p.getQuantity());
        }

        List<Recipe> all = getAllRecipes();
        List<Recipe> suggested = new ArrayList<>();

        for (Recipe r : all) {
            boolean canMake = true;
            for (RecipeIngredient ri : r.getIngredients()) {
                String key = normalise(ri.getName());
                Double have = pantryMap.get(key);
                if (have == null || have < ri.getQuantity()) {
                    canMake = false;
                    break;
                }
            }
            if (canMake && !r.getIngredients().isEmpty()) {
                suggested.add(r);
            }
        }
        return suggested;
    }

    /** Simple normalisation for robust matching without NLP. */
    private String normalise(String name) {
        if (name == null) return "";
        String s = name.trim().toLowerCase(Locale.ROOT);
        // basic plural stripping
        if (s.endsWith("oes") && s.length() > 3) {          // tomatoes -> tomato
            s = s.substring(0, s.length() - 2);
        } else if (s.endsWith("ies") && s.length() > 3) {   // berries -> berry
            s = s.substring(0, s.length() - 3) + "y";
        } else if (s.endsWith("es") && s.length() > 2) {
            s = s.substring(0, s.length() - 2);
        } else if (s.endsWith("s") && s.length() > 1) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
