/**

 The LoginActivity class is responsible for handling the login process of the user.
 It extends the AppCompatActivity class to enable support for older versions of Android.
 The login process starts when the user enters their email address and clicks on the login button.
 If the email address entered is valid, the user is redirected to the ProfileActivity page.
 */
package com.cst2335.android_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    /**
     * This method is called when the LoginActivity is created.
     * It sets the layout of the activity to activity_login.xml.
     * It also sets up the click listener for the login button.
     * @param savedInstanceState saved instance state of the activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn = findViewById(R.id.login);
        btn.setOnClickListener((click) ->
        {

            EditText userText = findViewById((R.id.email));
            String userTyped = userText.getText().toString();

            Intent goToProfile = new Intent(LoginActivity.this, ProfileActivity.class);
            goToProfile.putExtra("Email", userTyped);

            startActivity(goToProfile);
        });
    }
}