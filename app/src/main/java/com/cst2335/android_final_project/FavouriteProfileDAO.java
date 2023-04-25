package com.cst2335.android_final_project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
/**
 This interface represents the Data Access Object (DAO) for FavouriteProfileData.
 It provides methods for inserting, querying, and deleting data from the FavouriteProfileData table in the database.
 */
@Dao
public interface FavouriteProfileDAO {

    /**
     * Inserts a FavouriteProfileData object into the database.
     * @param p the FavouriteProfileData object to insert
     */
    @Insert
    public void insertMessage(FavouriteProfileData p);

    /**
     * Retrieves all FavouriteProfileData objects from the database.
     * @return a List of FavouriteProfileData objects
     */
    @Query("Select * from FavouriteProfileData")
    public List<FavouriteProfileData> getAllMessages();

    /**
     * Returns the number of FavouriteProfileData objects in the database.
     * @return the number of messages
     */
    @Query("SELECT COUNT(*) FROM FavouriteProfileData")
    int getNumberOfMessages();

    /**
     * Deletes a FavouriteProfileData object from the database.
     * @param p the FavouriteProfileData object to delete
     */
    @Delete
    public void deleteMessage(FavouriteProfileData p);
}
