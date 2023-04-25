package com.cst2335.android_final_project;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/**

 The FavouriteProfileDatabase class is a Room database class that extends RoomDatabase.
 This class is responsible for creating and managing the database for the favourite drink profiles
 that the user saves in the app. It includes a method for getting the singleton instance of the
 database, as well as a method for getting the Data Access Object (DAO) for the database.
 */
@Database(entities = {FavouriteProfileData.class}, version=1)
public abstract class FavouriteProfileDatabase extends RoomDatabase {

    public static final String DB_NAME = "Drink_Profile_DB";

    public abstract FavouriteProfileDAO FavouriteProfileDAO();

    private static FavouriteProfileDatabase Instance;
    public static synchronized FavouriteProfileDatabase getInstance(Context ctx) {
        if(Instance == null) {
            Log.d("FavouriteProfileDatabase", "mInstance is null, creating new instance");
            Instance = Room.databaseBuilder(ctx.getApplicationContext(),
                            FavouriteProfileDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }
}
