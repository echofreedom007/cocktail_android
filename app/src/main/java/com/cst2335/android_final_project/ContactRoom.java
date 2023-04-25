package com.cst2335.android_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.os.Bundle;
/**
 * ContactRoom is an abstract class that represents a database of Contact entities.
 * This class is implemented as a RoomDatabase, using the ContactDao interface to define database operations.
 *
 * @author Boling Zhang
 * @version 1.0
 * @since 2023-04-06
 */
@Database(entities = {Contact.class}, version = 1)
public abstract class ContactRoom extends RoomDatabase {


    /**
     * Returns a ContactDao instance that can be used to interact with the database.
     *
     * @return ContactDao instance.
     */
    public abstract ContactDao contactDao();

    private static ContactRoom instance;

    /**
     * Returns a singleton instance of ContactRoom.
     *
     * @param context The context used to create the database.
     * @return Singleton instance of ContactRoom.
     */
    public static ContactRoom getInstance(Context context) {
        synchronized (ContactRoom.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                                ContactRoom.class, "contact_database")
                        .build();
            }
        }
        return instance;
    }
}

