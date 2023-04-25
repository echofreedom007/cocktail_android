package com.cst2335.android_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import com.google.android.material.navigation.NavigationView;

/**
 * he MainActivity class represents the main activity of the application.
 *  It includes a custom action bar, a navigation drawer, and buttons to navigate
 *  to other activities, as well as to register, log in, and display a contact form.
 *  It also displays a snackbar with an action to show a fragment for the contact form.
 *
 * @author Boling Zhang
 * @version 1.0
 * @since 2023-04-06
 */

public class MainActivity extends AppCompatActivity {

    private ImageView drawerButton;

    /**
     * Initializes the main activity when created.
     * Sets up the custom action bar with a title and drawer button, a navigation drawer,
     * and onClickListeners for buttons to navigate to other activities, register, log in,
     * and display a contact form. Also displays a snackbar with an action to show a
     * fragment for the contact form.
     *
     * @param savedInstanceState the saved state of the activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_homepage);


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
            titleTextView.setText(R.string.echo_header);

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.home_help_button);
                    builder.setMessage(R.string.echo_alert);
                    builder.setPositiveButton(R.string.help_menu_action, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            });
            NavigationView navigationView = findViewById(R.id.nav_graph_home_page); // Replace 'nav_view' with the id of your NavigationView in the XML layout file
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Handle navigation view item clicks here.
                    int id = item.getItemId();

                    switch (id) {
                        case R.id.nav_home:
                            // Navigate to home page
                            Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(homeIntent);
                            break;
                        case R.id.nav_search:
                            // Navigate to search page
                            Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                            startActivity(searchIntent);
                            break;
                        case R.id.nav_favourites:
                            // Navigate to favourites page
                            Intent favouritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
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
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                String SearchToastMessage = getString(R.string.search_button_toast_message);
                Toast.makeText(MainActivity.this, SearchToastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        Button favouritesButton = findViewById(R.id.favourites_button);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favouritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(favouritesIntent);
                String FavouriteToastMessage = getString(R.string.favourite_button_toast_message);
                Toast.makeText(MainActivity.this, FavouriteToastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        Button RegisterButton = findViewById(R.id.register_button);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        Button contactButton = findViewById(R.id.contact_button);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snackBarText = getString(R.string.homeSnackbarText);
                String snackBarActionText = getString(R.string.snackbarActionText);
                Snackbar snackbar = Snackbar.make(view, snackBarText, Snackbar.LENGTH_LONG);
                snackbar.setAction(snackBarActionText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Show fragment page
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_contact, new ContactFragment());
                        fragmentTransaction.commit();
                    }
                });
//                snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));

                snackbar.show();
            }
        });

    }
}