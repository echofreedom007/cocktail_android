package com.cst2335.android_final_project;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
/**
 The FavoriteDrinksAdapter class is a RecyclerView adapter that is responsible for populating a list
 of favourite drinks. It extends the RecyclerView.Adapter<FavoriteDrinksAdapter.FavouriteDrinkViewHolder>
 class and contains an inner static class FavouriteDrinkViewHolder that defines the ViewHolder for the
 RecyclerView items.
 */

public class FavoriteDrinksAdapter extends RecyclerView.Adapter<FavoriteDrinksAdapter.FavouriteDrinkViewHolder> {
    // instance variables
    private List<FavouriteDrink> favouriteDrinks;
    private OnItemClickListener onItemClickListener;
    private List<FavouriteDrink> removedItems = new ArrayList<>();

    /**
     * Constructor for the FavoriteDrinksAdapter class.
     *
     * @param favouriteFavouriteDrinks    The list of favourite drinks to be displayed
     * @param onItemClickListener   Listener for when an item is clicked
     */
    public FavoriteDrinksAdapter(List<FavouriteDrink> favouriteFavouriteDrinks, OnItemClickListener onItemClickListener) {
        this.favouriteDrinks = favouriteFavouriteDrinks;
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent    The ViewGroup into which the new View will be added
     * @param viewType  The type of the new view
     * @return  A new ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public FavouriteDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new FavouriteDrinkViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder    The ViewHolder which should be updated to represent the contents of the item at the given position
     * @param position  The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull FavouriteDrinkViewHolder holder, int position) {
        holder.bind(favouriteDrinks.get(position), onItemClickListener);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return  The total number of items in this adapter
     */
    @Override
    public int getItemCount() {
        return favouriteDrinks.size();
    }

    /**
     * Interface for when an item in the RecyclerView is clicked.
     */
    public interface OnItemClickListener {
        void onItemClick(FavouriteDrink favouriteDrink);
    }

    /**
     * Inner static class that defines the ViewHolder for the RecyclerView items.
     */
    public static class FavouriteDrinkViewHolder extends RecyclerView.ViewHolder {
        TextView drinkNameTextView;

        /**
         * Constructor for the FavouriteDrinkViewHolder class.
         *
         * @param itemView  The View that represents an individual RecyclerView item
         */
        public FavouriteDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkNameTextView = itemView.findViewById(android.R.id.text1);
        }

        /**
         * Binds the data from a FavouriteDrink object to the ViewHolder's View.
         *
         * @param favouriteDrink            The FavouriteDrink object to bind
         * @param onItemClickListener      Listener for when an item is clicked
         */
        public void bind(FavouriteDrink favouriteDrink, OnItemClickListener onItemClickListener) {
            drinkNameTextView.setText(favouriteDrink.getDrinkName());
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(favouriteDrink));
        }
    }

    /**
     Updates the data of the RecyclerView with new favourite drinks.
     @param newFavouriteDrinks A List of FavouriteDrink objects containing the new data.
     */
    public void updateData(List<FavouriteDrink> newFavouriteDrinks) {
        this.favouriteDrinks = newFavouriteDrinks;
        notifyDataSetChanged();
    }

    /**
     Clears the data of the RecyclerView.
     */
    public void clearData() {
        int itemCount = favouriteDrinks.size();
        favouriteDrinks.clear();
        notifyItemRangeRemoved(0, itemCount);
    }

}

