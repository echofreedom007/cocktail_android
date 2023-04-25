package com.cst2335.android_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import android.os.Bundle;

/**
 * A class representing a contact, including name, email, and message.
 * This class is used in conjunction with the ContactDao and ContactRoom classes to
 * save contact information to a local SQLite database.
 *
 * @author Boling Zhang
 * @version 1.0
 * @since 2023-04-06
 */
@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "message")
    private String message;

    /**
     * Constructor for a Contact object.
     *
     * @param name The name of the contact.
     * @param email The email address of the contact.
     * @param message The message sent by the contact.
     */
    public Contact(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    /**
     * Returns the ID of the contact.
     *
     * @return The ID of the contact.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the email address of the contact.
     *
     * @return The email address of the contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the message sent by the contact.
     *
     * @return The message sent by the contact.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the ID of the contact.
     *
     * @param id The ID of the contact.
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Sets the name of the contact.
     *
     * @param name The name of the contact.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the email address of the contact.
     *
     * @param email The email address of the contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the message sent by the contact.
     *
     * @param message The message sent by the contact.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
