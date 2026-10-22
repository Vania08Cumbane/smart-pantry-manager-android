# Smart Pantry Manager

A Java Android application that helps reduce food waste by tracking leftover ingredients in your pantry and suggesting recipes you can make **strictly** with what you already have.

# Smart Pantry Manager (Android)

This is the Android application for the Smart Pantry Manager assignment.

## Setup
- Open in Android Studio
- Sync Gradle
- Run the app on an emulator or device

## Features

- **Pantry management**: Add, edit, delete ingredients (name, quantity, unit, optional expiry date)
- **Pantry list**: RecyclerView with custom adapter
- **Recipe collection**: 18 pre-seeded recipes
- **Strict matching**: A recipe only appears if **every** required ingredient is present in sufficient quantity
- **Recipe detail**: Full ingredients + method
- **Settings**: Expiry alerts toggle + unit preference
- **Navigation**: Bottom navigation bar
- **Input validation** on add/edit forms
- **SQLite** local database with full CRUD and persistence

## Database Choice

**SQLite** (via `SQLiteOpenHelper`) was chosen because:
- Fully offline – no network calls that could cause ANRs
- Simple, reliable, and matches the module’s persistent-data content
- Data stays on the device and survives app restarts

## How to Run

1. Open Android Studio
2. **File → Open** and select this project folder (`SmartPantryManager`)
3. Let Gradle sync (first time may take a minute)
4. Connect an emulator or physical device (API 24+)
5. Click **Run** ▶

## Project Structure

```
app/src/main/java/com/richfield/smartpantry/
  MainActivity.java              – Pantry list + FAB + bottom nav
  AddEditIngredientActivity.java – Add / Edit form with validation
  SuggestedRecipesActivity.java  – Strict-matching suggestions
  RecipeDetailActivity.java      – Recipe ingredients + method
  SettingsActivity.java          – Preferences
  DatabaseHelper.java            – SQLite + seed data + strict matching
  PantryAdapter.java / RecipeAdapter.java
  Model classes: PantryItem, Recipe, RecipeIngredient
```

## Strict-Matching Logic

Located in `DatabaseHelper.getSuggestedRecipes()`:
- Builds a map of normalised ingredient names → total quantity in pantry
- For each recipe, checks that **every** required ingredient exists with qty ≥ required
- Name normalisation: lowercase, trim, basic singular/plural handling (tomato/tomatoes etc.)
- Partial matches are **never** shown in the main suggestions list

## Testing the Strict Rule

1. Add ingredients that match a simple recipe (e.g. eggs, salt → Boiled Eggs)
2. Open Suggested Recipes – the recipe should appear
3. Delete one required ingredient
4. Return to Suggested Recipes – that recipe disappears
5. Close and reopen the app – pantry data is still there

## GitHub

GitHub
Repository: https://github.com/Vania08Cumbane/smart-pantry-manager-android

