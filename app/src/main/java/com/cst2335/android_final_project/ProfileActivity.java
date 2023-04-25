package com.cst2335.android_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the intent that started this activity
        Intent fromMain = getIntent();

        // Get the user's first name from the intent and set it to the appropriate TextView
        String input1 = fromMain.getStringExtra("FirstName");
        TextView userFirstName = findViewById(R.id.entered_fName);
        userFirstName.setText(input1);

        // Get the user's last name from the intent and set it to the appropriate TextView
        String input2 = fromMain.getStringExtra("LastName");
        TextView userLastName = findViewById(R.id.entered_lName);
        userLastName.setText(input2);

        // Get the user's email address from the intent and set it to the appropriate TextView
        String input3 = fromMain.getStringExtra("EmailAddress");
        TextView userEmailAddress = findViewById(R.id.entered_eMail);
        userEmailAddress.setText(input3);

        /**
        Intent intentFragment = new Intent(ProfileActivity.this, FavouriteFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("1", input1);
        bundle.putString("2", input2);
        bundle.putString("3", input3);
        intentFragment.putExtras(bundle);
        startActivity(intentFragment);
         */

    }
}