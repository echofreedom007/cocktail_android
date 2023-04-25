package com.cst2335.android_final_project;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Data access object for the favourite drinks table.
 * Provides methods for inserting, retrieving, and deleting favourite drinks from the database.
 */
@Dao
public interface DrinkDAO {
        /**
         * Inserts a new favourite drink into the database.
         * If a drink with the same ID already exists, it will be replaced.
         * @param FavouriteDrink The favourite drink to insert.
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(FavouriteDrink FavouriteDrink);

        /**
         * Retrieves all favourite drinks from the database.
         * @return A list of all favourite drinks in the database.
         */
        @Query("SELECT * FROM favorite_drinks")
        List<FavouriteDrink> getAllFavoriteDrinks();

        /**
         * Deletes a favourite drink from the database.
         * @param FavouriteDrink The favourite drink to delete.
         */
        @Delete
        void delete(FavouriteDrink FavouriteDrink);

        /**
         * Retrieves a favourite drink from the database by its ID.
         * @param drinkId The ID of the favourite drink to retrieve.
         * @return The favourite drink with the specified ID, or null if no such drink exists.
         */
        @Query("SELECT * FROM favorite_drinks WHERE drinkId = :drinkId")
        FavouriteDrink findByDrinkId(String drinkId);
    }

