/*This line specifies the package of the class, indicating that it is located in the package com.example.logbook3.*/
package com.example.logbook3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class DetailsActivity extends AppCompatActivity {

    Button button_back;
    private ListView listView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the layout for this activity from the XML file 'activity_details.xml'
        setContentView(R.layout.activity_details);

        // Initialize the 'button_back_at_add_book' button, 'listView', and 'databaseHelper' objects
        button_back = findViewById(R.id.button_back);
        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        // Set an OnClickListener for the 'button_back_at_add_book' button
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set an OnClickListener for the 'button_back_at_add_book' button
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);

                // Start the MainActivity
                startActivity(intent);
            }
        });

        // Retrieve all user data from the database using the 'getAllUserData()' method in 'DatabaseHelper'
        Cursor cursor = databaseHelper.getAllUserData();

        // Create a UserCursorAdapter to display the user data in the 'listView'
        Adapter cursorAdapter = new Adapter(this, cursor);

        // Set the UserCursorAdapter as the adapter for the 'listView'
        listView.setAdapter(cursorAdapter);
    }
}
