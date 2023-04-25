package com.cst2335.android_final_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * This fragment allows the user to submit a contact form.
 *
 * @author Boling Zhang
 * @version 1.0
 * @since 2023-04-06
 */

public class ContactFragment extends Fragment {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText messageEditText;

    private Button submitButton;

    private ContactRoom database;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public void onAttach(Context context) {
        super.onAttach(context);
        database = ContactRoom.getInstance(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        // Get references to the UI elements
        nameEditText = view.findViewById(R.id.name_input);
        emailEditText = view.findViewById(R.id.email_input);
        messageEditText = view.findViewById(R.id.message_input);

        submitButton = view.findViewById(R.id.submit_button);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Retrieve the saved values of the name and email EditText fields from SharedPreferences
        String savedName = sharedPreferences.getString("name", "");
        String savedEmail = sharedPreferences.getString("email", "");

        // Set the text of the name and email EditText fields to the saved values
        nameEditText.setText(savedName);
        emailEditText.setText(savedEmail);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the user clicks the submit button, retrieve the data
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String message = messageEditText.getText().toString();

                // Save the name and email to SharedPreferences
                editor.putString("name", name);
                editor.putString("email", email);
                editor.apply();

                nameEditText.setText(sharedPreferences.getString("name", ""));
                emailEditText.setText(sharedPreferences.getString("email", ""));


                // Create a new record in the database on a background thread
                new InsertContactTask().execute(new Contact(name, email, message));
                getFragmentManager().beginTransaction().hide(ContactFragment.this).commit();
            }
        });

        // Get a reference to the database
        database = ContactRoom.getInstance(getContext());

        return view;
    }

    /**
     * A background task for inserting a new contact into the database.
     */
    private class InsertContactTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(Contact... contacts) {
            // Insert the contact into the database on a background thread
            database.contactDao().insert(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Display a success message to the user on the main thread
            Toast.makeText(getContext(), "Message sent!", Toast.LENGTH_SHORT).show();
        }
    }
}
