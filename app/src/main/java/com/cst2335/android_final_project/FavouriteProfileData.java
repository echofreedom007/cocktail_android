package com.cst2335.android_final_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 This class represents a table entity in a Room database for storing favourite profile data.
 It contains fields for the first name, last name, and email status of a profile, and an auto-generated primary key ID.
 */
@Entity (tableName = "FavouriteProfileData")
public class FavouriteProfileData {

    // Field for the first name
    @ColumnInfo(name="FirstName")
    public String firstname;

    // Field for the last name
    @ColumnInfo(name="LastName")
    public String lastname;

    // Field for the email status
    @ColumnInfo(name="Email")
    public boolean email;

    // Auto-generated primary key ID
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    /**
     * Constructor for the class that sets the values for the first name, last name, and email fields.
     */
    public FavouriteProfileData(String firstname, String lastname, boolean email)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    /**
     * Returns the first name field value.
     */
    public String getMessage() {
        return firstname;
    }

    /**
     * Returns the last name field value.
     */
    public String getTimeSent() {
        return lastname;
    }

    /**
     * Returns the email field value.
     */
    public boolean isSentButton() {
        return email;
    }
}
