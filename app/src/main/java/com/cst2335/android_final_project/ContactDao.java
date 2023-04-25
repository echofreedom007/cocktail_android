package com.cst2335.android_final_project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
/**
 * This is a DAO (Data Access Object) interface for the ContactRoom database.
 * It provides methods to perform CRUD (Create, Read, Update, Delete) operations on the "contacts" table.
 * @author Boling Zhang
 * @version 1.0
 * @since 2023-04-06
 */
@Dao
public interface ContactDao {

    /**
     * Inserts a new Contact into the database.
     *
     * @param contact the Contact object to be inserted
     */
    @Insert
    void insert(Contact contact);

    /**
     * Updates an existing Contact in the database.
     *
     * @param contact the Contact object to be updated
     */
    @Update
    void update(Contact contact);


    /**
     * Deletes a Contact from the database.
     *
     * @param contact the Contact object to be deleted
     */
    @Delete
    void delete(Contact contact);


    /**
     * Retrieves a list of all Contacts from the database.
     *
     * @return a List of all Contacts in the database
     */
    @Query("SELECT * FROM contacts")
    List<Contact> getAllContacts();

    /**
     * Retrieves a Contact object from the database with the specified ID.
     *
     * @param id the ID of the Contact to retrieve
     * @return the Contact object with the specified ID
     */
    @Query("SELECT * FROM contacts WHERE id = :id")
    Contact getContactById(int id);
}
