package com.cst2335.android_final_project;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * This class is to store drinkID, drinkName and drinkImageUrl to a table named FavouriteDrink
 * It has simple getters and setters.
 *
 */
@Entity(tableName = "favorite_drinks")
public class FavouriteDrink {

    @PrimaryKey
    @NonNull
    private String drinkId;
    private String drinkName;

    private String drinkImageUrl;

    public FavouriteDrink() {
    }

    public FavouriteDrink(String drinkId, String drinkName) {
        this.drinkId = drinkId;
        this.drinkName = drinkName;

    }
    public FavouriteDrink(String drinkId, String drinkName, String drinkImageUrl) {
        this.drinkId = drinkId;
        this.drinkName = drinkName;
        this.drinkImageUrl =drinkImageUrl;

    }



    public String getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }
    public String getDrinkImageUrl() {
        return drinkImageUrl;
    }
    public void setDrinkImageUrl(String drinkImageUrl) {
        this.drinkImageUrl = drinkImageUrl;
    }

}
