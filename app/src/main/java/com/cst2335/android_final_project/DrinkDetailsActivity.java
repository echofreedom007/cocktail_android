package com.cst2335.android_final_project;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 This class is responsible for the drink details page
 It loads and populates the drink details page, and includes methods to interact with the database using the DrinkDAO
 */

public class DrinkDetailsActivity extends AppCompatActivity {
    private ImageView drawerButton;


    /**
     * Initializes the activity and sets up the ActionBar, NavigationView, and onClickListeners for
     * the save and remove from favourites buttons. It also fetches the details of the selected drink
     * using the cocktail API and updates the UI with the details.
     *
     * @param savedInstanceState The saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater inflater = LayoutInflater.from(this);
            View actionBarView = inflater.inflate(R.layout.action_bar_custom, null);
            actionBar.setCustomView(actionBarView, new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT
            ));

            TextView titleTextView = actionBarView.findViewById(R.id.header);
            titleTextView.setText(R.string.grace_header);

            drawerButton = actionBarView.findViewById(R.id.drawer);
            drawerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            ImageView helpButton = findViewById(R.id.helpButton);
            helpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DrinkDetailsActivity.this);
                    builder.setTitle("Help");
                    builder.setMessage(R.string.grace_alert);
                    builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            });

            NavigationView navigationView = findViewById(R.id.nav_view); // Replace 'nav_view' with the id of your NavigationView in the XML layout file
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                /**
                 * Handles clicks on the NavigationView items.
                 *
                 * @param item The drink that was clicked.
                 * @return True if the item click was handled successfully, false otherwise.
                 */
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Handle navigation view item clicks here.
                    int id = item.getItemId();

                    switch (id) {
                        case R.id.nav_home:
                            // Navigate to home page
                            Intent homeIntent = new Intent(DrinkDetailsActivity.this, MainActivity.class);
                            startActivity(homeIntent);
                            break;
                        case R.id.nav_search:
                            // Navigate to search page
                            Intent searchIntent = new Intent(DrinkDetailsActivity.this, SearchActivity.class);
                            startActivity(searchIntent);
                            break;
                        case R.id.nav_favourites:
                            // Navigate to favourites page
                            Intent favouritesIntent = new Intent(DrinkDetailsActivity.this, FavoritesActivity.class);
                            startActivity(favouritesIntent);
                            break;
                        default:
                            break;
                    }

                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            });

        }

//    The saveToFavouritesButton onClickListener saves the current drink to the favorites list
//    by creating a new instance of the Drink class and adding it to the favorites list in the SharedPreferences.
        Button saveToFavouritesButton = findViewById(R.id.save_to_favorites);
        saveToFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDrinkToFavourites();
            }
        });


//     The removeFromFavouritesButton onClickListener removes the current drink from the favorites
//     list by creating a new instance of the Drink class and removing it from the favorites list
//     in the SharedPreferences.
        Button removeFromFavouritesButton = findViewById(R.id.rmv_from_favorites);
        removeFromFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveDrinkFromFavourites();
            }
        });

        Intent intent = getIntent();
        String drinkId = intent.getStringExtra("drinkId");

        fetchDrinkDetails(drinkId);

    }

    /**
     * This method retrieves the drink details from the API by making a GET request
     * to thecocktaildb.com using the provided drink ID, and then passes the JSON
     * response to the updateDrinkDetails method to update the UI elements.
     *
     * @param drinkId The ID of the drink whose details are to be fetched.
     */
    private void fetchDrinkDetails(String drinkId) {
        String baseUrl = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
        String searchUrl = baseUrl + drinkId;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                searchUrl,
                null,
                response -> {
                    try {
                        JSONArray drinksArray = response.getJSONArray("drinks");
                        JSONObject drink = drinksArray.getJSONObject(0);
                        updateDrinkDetails(drink);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(DrinkDetailsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(objectRequest);
    }

    /**
     * This method updates the UI elements in the drink details screen with the details of
     * the current drink, as retrieved from the JSON response of the API.
     *
     * @param drink The JSON object containing the drink details.
     */
    private void updateDrinkDetails(JSONObject drink) {
        try {
            // Update the views with the drink details
            // Name of the drink
            TextView drinkNameTextView = findViewById(R.id.drink_name);
            drinkNameTextView.setText(drink.getString("strDrink"));

            // Picture of the drink
            ImageView drinkImageView = findViewById(R.id.drink_image);
            String imageUrl = drink.getString("strDrinkThumb");
            Glide.with(this).load(imageUrl).into(drinkImageView);

            // Ingredients (top 3)
            TextView ingredient1TextView = findViewById(R.id.ingredient_1);
            TextView ingredient2TextView = findViewById(R.id.ingredient_2);
            TextView ingredient3TextView = findViewById(R.id.ingredient_3);

            ingredient1TextView.setText(drink.getString("strIngredient1"));
            ingredient2TextView.setText(drink.getString("strIngredient2"));
            ingredient3TextView.setText(drink.getString("strIngredient3"));

            // Description
            TextView descriptionTextView = findViewById(R.id.description);
            descriptionTextView.setText(drink.getString("strInstructions"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method saves the current drink to the favorites list by creating a new instance
     * of the FavouriteDrink class and adding it to the favorites list in the SharedPreferences.
     * It also displays a toast message to confirm that the drink has been saved to the favorites list.
     */
    private void saveDrinkToFavourites() {
        // Get the drink ID and name from the TextViews
        TextView drinkNameTextView = findViewById(R.id.drink_name);
        String drinkId = getIntent().getStringExtra("drinkId");
        String drinkName = drinkNameTextView.getText().toString();

        // Save the drink ID and name to the local Room database
        AsyncTask.execute(() -> {
            FavouriteDrink FavouriteDrink = new FavouriteDrink();
            FavouriteDrink.setDrinkId(drinkId);
            FavouriteDrink.setDrinkName(drinkName);
            AppDatabase.getInstance(DrinkDetailsActivity.this).favoriteDrinkDao().insert(FavouriteDrink);
            runOnUiThread(() -> Toast.makeText(DrinkDetailsActivity.this, "Drink saved to favorites.", Toast.LENGTH_SHORT).show());
        });
    }


    /**
     * This method removes a drink from the user's favorites list. It retrieves the drink ID and name
     * from the TextViews in the activity, checks if the drink is currently in the user's favorites list
     * using the isDrinkInFavourites() method, and if so, removes it from the favorites list using an
     * AsyncTask. A toast message is displayed to notify the user of the successful removal. If the drink
     * is not in the favorites list, a Snackbar message is displayed to notify the user.
     */
    private void RemoveDrinkFromFavourites() {
        // Get the drink ID and name from the TextViews
        TextView drinkNameTextView = findViewById(R.id.drink_name);
        String drinkId = getIntent().getStringExtra("drinkId");
        String drinkName = drinkNameTextView.getText().toString();
        if (isDrinkInFavourites(drinkId)) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    FavouriteDrink RemoveFavouriteDrink = new FavouriteDrink();
                    RemoveFavouriteDrink.setDrinkId(drinkId);
                    RemoveFavouriteDrink.setDrinkName(drinkName);
                    AppDatabase.getInstance(DrinkDetailsActivity.this).favoriteDrinkDao().delete(RemoveFavouriteDrink);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DrinkDetailsActivity.this, "Drink removed from favorites.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        else {
            Snackbar.make(findViewById(R.id.drawer_layout),
                                "This drink is not currently in your Favourites list.",
                                     Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Checks if a drink with the specified ID is already in the user's list of favorite drinks.
     * @param drinkId the ID of the drink to check for
     * @return true if the drink is in the user's list of favorites, false otherwise
     */
    private boolean isDrinkInFavourites(String drinkId) {
        AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                FavouriteDrink favouriteDrink = AppDatabase.getInstance(DrinkDetailsActivity.this).favoriteDrinkDao().findByDrinkId(drinkId);
                return favouriteDrink != null;
            }
        };
        try {
            return asyncTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

