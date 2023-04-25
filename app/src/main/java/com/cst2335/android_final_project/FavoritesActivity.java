package com.cst2335.android_final_project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity displays a list of favourite drinks, retrieved from the local SQLite database.
 * The list is displayed using a RecyclerView and each item is clickable.
 * When an item is clicked, the user is taken to the DrinkDetailsActivity, which displays
 * detailed information about the drink.
 * The activity also contains a custom action bar with a drawer button and a help button.
 * The drawer button opens the navigation drawer, which contains links to the home, search,
 * and favourites pages. The help button displays an alert dialog with information about the page.
 * The activity also contains two buttons: a load profile button and a clear button.
 * The load profile button displays a Snackbar when clicked, which allows the user to navigate
 * to a fragment that displays their profile information. The clear button removes all favourite
 * drinks from the RecyclerView and the local database.
 */

public class FavoritesActivity extends AppCompatActivity {

    private ImageView drawerButton;
    private RecyclerView favouritesRecyclerView;
    private FavoriteDrinksAdapter favouriteDrinksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        setupRecyclerView();
        loadFavouriteDrinks();

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
            titleTextView.setText(R.string.mayank_header);

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(FavoritesActivity.this);
                    builder.setTitle(R.string.home_help_button);
                    builder.setMessage(R.string.mayank_alert);
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
                            Intent homeIntent = new Intent(FavoritesActivity.this, MainActivity.class);
                            startActivity(homeIntent);
                            break;
                        case R.id.nav_search:
                            // Navigate to search page
                            Intent searchIntent = new Intent(FavoritesActivity.this, SearchActivity.class);
                            startActivity(searchIntent);
                            break;
                        case R.id.nav_favourites:
                            // Navigate to favourites page
                            Intent favouritesIntent = new Intent(FavoritesActivity.this, FavoritesActivity.class);
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
        Button loadProfileButton = findViewById(R.id.loadProfile);
        loadProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String snackBarText = getString(R.string.favouriteSnackbar1);
                String snackBarActionText = getString(R.string.favouriteSnackbar2);
                Snackbar snackbar = Snackbar.make(view, snackBarText, Snackbar.LENGTH_LONG);
                snackbar.setAction(snackBarActionText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Show fragment page
                        FragmentManager fragmentProfileManager = getSupportFragmentManager();
                        FragmentTransaction fragmentProfileTransaction = fragmentProfileManager.beginTransaction();
                        fragmentProfileTransaction.replace(R.id.fragment_Favourites, new FavouriteFragment());
                        fragmentProfileTransaction.commit();
                    }
                });
                snackbar.show();

                // Hide the button after the user clicks on it
                loadProfileButton.setVisibility(View.GONE);
            }
        });

        Button clearButton = findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteDrinksAdapter.clearData();
            }
        });





    }


    private void setupRecyclerView() {
        favouritesRecyclerView = findViewById(R.id.favourites_recycler_view);
        favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        favouriteDrinksAdapter = new FavoriteDrinksAdapter(new ArrayList<>(), this::onFavouriteDrinkClicked);
        favouritesRecyclerView.setAdapter(favouriteDrinksAdapter);
    }


    private void loadFavouriteDrinks() {
        AsyncTask.execute(() -> {
            List<FavouriteDrink> favouriteDrinks = AppDatabase.getInstance(FavoritesActivity.this).favoriteDrinkDao().getAllFavoriteDrinks();
            runOnUiThread(() -> {
                if (favouriteDrinks != null) {
                    favouriteDrinksAdapter.updateData(favouriteDrinks);
                }
            });
        });
    }




    private void onFavouriteDrinkClicked(FavouriteDrink favouriteDrink) {
        Intent intent = new Intent(this, DrinkDetailsActivity.class);
        intent.putExtra("drinkId", favouriteDrink.getDrinkId());
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadFavouriteDrinks();
    }



}
