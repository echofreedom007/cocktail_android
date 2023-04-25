package com.cst2335.android_final_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;


import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass that displays a list of search results
 * for cocktails. Each result contains an image, name, and a button to save
 * the drink to favorites.
 */
public class fragment_search_results extends Fragment {

    private static final String ARG_DRINKS = "drinks";
    /**
     * Required empty public constructor.
     */
    public fragment_search_results() {
        // Required empty public constructor
    }
    /**
     * Creates a new instance of fragment_search_results with the given drinks data.
     * @param drinks JSON string representing an array of drinks.
     * @return A new instance of fragment_search_results.
     */
    public static fragment_search_results newInstance(String drinks) {
        fragment_search_results fragment = new fragment_search_results();
        Bundle args = new Bundle();
        args.putString(ARG_DRINKS, drinks);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Called to create the view hierarchy associated with the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate
     *                 any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's
     *                  UI should be attached to. The fragment should not add the view itself,
     *                  but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        LinearLayout resultsContainer = view.findViewById(R.id.results_container);

        if (getArguments() != null) {
            try {
                JSONArray drinksArray = new JSONArray(getArguments().getString(ARG_DRINKS));
                int resultCount = Math.min(4, drinksArray.length());

                for (int i = 0; i < resultCount; i++) {
                    JSONObject drink = drinksArray.getJSONObject(i);
                    String drinkName = drink.getString("strDrink");
                    String imageUrl = drink.getString("strDrinkThumb");
                    String drinkId = drink.getString("idDrink");

                    View drinkItemView = createDrinkItemView(drinkName, imageUrl, drinkId);
                    resultsContainer.addView(drinkItemView);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
    /**
     * Creates a view for a single drink item with the given information.
     * @param drinkName The name of the drink.
     * @param drinkImageUrl The URL of the drink image.
     * @param drinkId The unique identifier for the drink.
     * @return The created drink item view.
     */
    private View createDrinkItemView(String drinkName, String drinkImageUrl, String drinkId) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View drinkItemView = inflater.inflate(R.layout.item_drink, null);

        TextView drinkNameTextView = drinkItemView.findViewById(R.id.drink_name);
        drinkNameTextView.setText(drinkName);

        ImageView drinkImageView = drinkItemView.findViewById(R.id.drink_image);
        Glide.with(this)
                .load(drinkImageUrl)
                .centerCrop()
                .into(drinkImageView);

        // Set the OnClickListener for the drinkNameTextView
        drinkNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrinkDetailsActivity.class);
                intent.putExtra("drinkId", drinkId);
                startActivity(intent);
            }
        });
        // Set the OnClickListener for the save_to_favorites_button
        ImageButton saveToFavoritesButton = drinkItemView.findViewById(R.id.save_to_favorites_button_search_page);
        saveToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Drink object from the current drink's data
                FavouriteDrink drink = new FavouriteDrink(drinkId, drinkName, drinkImageUrl);

                // Save the drink to favorites
                saveDrinkToFavorites(drink);
            }
        });

        return drinkItemView;
    }

    /**
     * Saves the given drink to the favorites database.
     * @param drink The drink to be saved to the favorites database.
     */
    private void saveDrinkToFavorites(FavouriteDrink drink) {
        AsyncTask.execute(() -> {
            AppDatabase.getInstance(getActivity()).favoriteDrinkDao().insert(drink);
            // Find a suitable parent view for the Snackbar
            View rootView = getActivity().findViewById(android.R.id.content);
            // Create and show the Snackbar
            Snackbar.make(rootView, "Drink saved to favorites.", Snackbar.LENGTH_SHORT).show();
            });
        };
    }



