package com.cst2335.android_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cst2335.android_final_project.databinding.ActivityRegisterBinding;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    /**
     * A string constant representing the file name for storing user data using SharedPreferences.
     */
    public final static String PREFERENCES_FILE = "UserData";
    /**
   /*The line below declares a variable called binding of type ActivityRegisterBinding. ActivityRegisterBinding is a generated class that is used to bind views from an XML layout file to Java code using the data binding feature in Android. This line creates a reference to the binding object so that it can be used later in the code to access views from the associated layout file.
    ActivityRegisterBinding binding;

    /*The line below declares a variable called FavModel of type FavouriteProfileViewModel. FavouriteProfileViewModel is a custom ViewModel class that is used to manage data and state for a particular activity or fragment. This line creates a reference to the ViewModel so that it can be used later in the code to retrieve or modify data.
    FavouriteProfileViewModel FavModel;

    /*This line declares a private variable called myAdapter of type RecyclerView.Adapter. This variable is likely used to populate a RecyclerView with data. A RecyclerView.Adapter is an abstract class that provides the data and views necessary to populate a RecyclerView widget.
    private RecyclerView.Adapter myAdapter;

    /* This line declares an ArrayList called profileDetails that contains objects of type FavouriteProfileData. FavouriteProfileData is likely a custom class that represents data about a user's profile. This line creates an empty ArrayList that will be used to store profile details retrieved from a database or entered by the user in the app.
    ArrayList<FavouriteProfileData> profileDetails;
    */

    /** This holds the text at the centre of the screen*/
    private TextView tv = null;
    /** This holds the entered password at the centre of the screen underneath TextView*/
    private EditText et = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view for the activity
        setContentView(R.layout.activity_register);

        //Following is the code to populate the recycle view in the activity_favourites.xml file.


        //Following is the code to check the password requirement
        et = findViewById(R.id.Pass);

        TextView firstname = findViewById((R.id.firstname));
        TextView lastname = findViewById((R.id.lastname));
        TextView emailAddress = findViewById((R.id.emailAddress));
        TextView password = findViewById((R.id.Pass));

        android.content.SharedPreferences prefs = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);

        String fName = prefs.getString("FirstName", "");
        firstname.setText(fName);

        String lName = prefs.getString("LastName", "");
        lastname.setText(lName);

        String email = prefs.getString("EmailAddress", "");
        emailAddress.setText(email);

        String Pass = prefs.getString("Password", "");
        password.setText(Pass);

        Button btn = findViewById(R.id.register_button);



        //Following is the onClick Listener to store data in shared preferences.



        btn.setOnClickListener(  (  click ) ->
        {



            //EditText firstname = findViewById((R.id.firstname));
            String userFirstName = firstname.getText().toString();

            //EditText lastname = findViewById((R.id.lastname));
            String userLastName = lastname.getText().toString();

            //EditText emailAddress = findViewById((R.id.emailAddress));
            String userEmail = emailAddress.getText().toString();

            //EditText password = findViewById((R.id.pass));
            String userPassword = password.getText().toString();

            android.content.SharedPreferences.Editor writer = prefs.edit();
            writer.putString("FirstName", firstname.getText().toString());
            writer.putString("LastName", lastname.getText().toString());
            writer.putString("EmailAddress", emailAddress.getText().toString());
            writer.putString("Password", password.getText().toString());
            writer.apply(); //save to disk

            Intent goToProfile = new Intent(RegisterActivity.this,   ProfileActivity.class  );

            goToProfile.putExtra("FirstName", userFirstName);
            goToProfile.putExtra("LastName", userLastName);
            goToProfile.putExtra("EmailAddress", userEmail);
            goToProfile.putExtra("UserPassword", userPassword);



            startActivity(goToProfile);
            passwordComplexOrNot();

        } );

        /**
        btn.setOnClickListener(clk -> {
            // insert the new message into the database
            new Thread(() -> {
                FavouriteProfileDatabase db = FavouriteProfileDatabase.getInstance(RegisterActivity.this);
                FavouriteProfileDAO pDAO = db.FavouriteProfileDAO();
                pDAO.insertMessage(newMessage);
            }).start();
        });
         */

    }

    private void passwordComplexOrNot() {
        String ReqPassword = et.getText().toString();
        boolean isPasswordComplex = checkPasswordComplexity(ReqPassword);
        if (isPasswordComplex) {
            showToast("Your password meets the requirements");
        } else {
            showToast("Your password does not meet the requirements");
        }
    }


    /** This function checks the length requirement of the password typed by the
     * user and then checks if the password also contains an uppercase, a
     * lowercase letter, has at least a number and a special symbol like (#$%^&*!@?).
     *
     * @param password The String object that we are checking
     * @return Returns true if the password meets all the requirements
     * mentioned above.
     */
    private boolean checkPasswordComplexity(String password) {
        boolean LengthValid = (password.length() >= 4 && password.length() <= 20);
        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundNumber = false;
        boolean foundSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if (!LengthValid) {
            showToast(getString(R.string.password_length_invalid));
            return false;
        }
        if (!foundUpperCase) {
            showToast(getString(R.string.password_uppercase_missing));
            return false;
        }
        if (!foundLowerCase) {
            showToast(getString(R.string.password_lowercase_missing));
            return false;
        }
        if (!foundNumber) {
            showToast(getString(R.string.password_number_missing));
            return false;
        }
        if (!foundSpecial) {
            showToast(getString(R.string.password_specialchar_missing));
            return false;
        }

        return true;

    }

    /** This is the helper function that displays
     * the Toast message with the given text.
     *
     * @param message
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /** This is the helper function that checks if a given character is a special symbol.
     * It takes a char parameter called c and returns true if the character is one of the
     * special symbols defined in the specialSymbols string. The indexOf method is used to
     * check if the character is present in the string, and returns -1 if it is not present.
     * Therefore, we return true if the character is present (i.e., indexOf does not return -1).
     * @param c
     * @return true if the specialSymbol has at least one of the symbols
     * mentioned inside the quotes.
     */

    private boolean isSpecialCharacter(char c) {
        String specialChars = "#$%^&*!@?";
        return specialChars.contains(String.valueOf(c));
    }
}