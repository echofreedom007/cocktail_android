package com.cst2335.android_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * SearchActivity allows the user to search for cocktails
 * by providing a keyword. It displays a fragment of the top 4 most related drinks where you
 * could save to myfavourites（favourite drinks table) and click to see the details of the drink.
 * It also handles navigation between different pages (home, search, and favorites) using a Navigation Drawer.
 */
public class SearchActivity extends AppCompatActivity {
    private ImageView drawerButton;
    /**
     * Called when the activity is starting. Initializes the UI, sets up action bar, navigation drawer,
     * and search functionality.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle). Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText searchEditText = findViewById(R.id.search_edit_text);
        Button searchButton = findViewById(R.id.search_button);

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
            titleTextView.setText(R.string.sophie_header);

            drawerButton = actionBarView.findViewById(R.id.drawer);
            drawerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            ImageView helpButton = actionBarView.findViewById(R.id.helpButton);
            helpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                    builder.setTitle(R.string.home_help_button);
                    builder.setMessage(R.string.sophie_alert);
                    builder.setPositiveButton(R.string.help_menu_action, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            });
            NavigationView navigationView = findViewById(R.id.nav_view); // Replace 'nav_view' with the id of your NavigationView in the XML layout file
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Handle navigation view item clicks here.
                    int id = item.getItemId();

                    switch (id) {
                        case R.id.nav_home:
                            // Navigate to home page
                            Intent homeIntent = new Intent(SearchActivity.this, MainActivity.class);
                            startActivity(homeIntent);
                            break;
                        case R.id.nav_search:
                            // Navigate to search page
                            Intent searchIntent = new Intent(SearchActivity.this, SearchActivity.class);
                            startActivity(searchIntent);
                            break;
                        case R.id.nav_favourites:
                            // Navigate to favourites page
                            Intent favouritesIntent = new Intent(SearchActivity.this, FavoritesActivity.class);
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

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString().trim();

                if (!searchText.isEmpty()) {
                    saveSearchText(searchText);
                    Toast.makeText(SearchActivity.this, R.string.sophie_toast, Toast.LENGTH_SHORT).show();
                    performSearch(searchText);
                } else {
                    Toast.makeText(SearchActivity.this, "Please enter a word", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadSearchText();
    }
    /**
     * Performs the search operation using the provided search keyword, makes an API request,
     * and updates the UI with the search results.
     * @param searchText The search keyword to be used for the search operation.
     */
    private void performSearch(String searchText) {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        // Make the API request to get the search results
        String baseUrl = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";
        String searchUrl = baseUrl + searchText;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                searchUrl,
                null,
                response -> {
                    try {
                        JSONArray drinksArray = response.getJSONArray("drinks");

                        // Create a new instance of fragment_search_results
                        fragment_search_results searchResultsFragment = fragment_search_results.newInstance(drinksArray.toString());

                        // Replace the existing fragment container with the new search results fragment
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, searchResultsFragment)
                                .commit();

                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle the error here
                    // For example, display a message to the user indicating that something went wrong
                    Toast.makeText(SearchActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

        requestQueue.add(objectRequest);
    }

    /**
     * Saves the provided search keyword to shared preferences for future use.
     * @param searchText The search keyword to be saved.
     */
    private void saveSearchText(String searchText) {
        SharedPreferences sharedPreferences = getSharedPreferences("search_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("search_text", searchText);
        editor.apply();
    }
    /**
     * Loads the previously saved search keyword from shared preferences and updates
     * the search input field with the saved value.
     */
    private void loadSearchText() {
        SharedPreferences sharedPreferences = getSharedPreferences("search_preferences", MODE_PRIVATE);
        String savedSearchText = sharedPreferences.getString("search_text", "");
        EditText searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.setText(savedSearchText);
    }



}


