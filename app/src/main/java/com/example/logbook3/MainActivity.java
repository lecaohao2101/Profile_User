/*This line specifies the package of the class, indicating that it is located in the package com.example.logbook3.*/
package com.example.logbook3;

/*These lines import the classes and interfaces needed to work with SQLite and Android.*/
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Calendar;

/*This is the declaration of variables for the user interface elements (EditText, RadioGroup, RadioButton, Button)
and a variable of the DatabaseHelper class to work with the database.*/
public class MainActivity extends AppCompatActivity {
    private  DatabaseHelper databaseHelper;
    EditText edtName, edtDob, edtEmail;
    RadioGroup radioGroup;
    RadioButton avt1, avt2, avt3;
    Button save, view;

    /*The onCreate method is called when the activity is created. In this method, variables are bound to corresponding
    elements in the XML file (defined in R.layout.activity_main).*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.name);
        edtDob = findViewById(R.id.dob);
        edtEmail = findViewById(R.id.email);

        radioGroup = findViewById(R.id.radioGroup);
        avt1 = findViewById(R.id.avt1);
        avt2 = findViewById(R.id.avt2);
        avt3 = findViewById(R.id.avt3);

        save = findViewById(R.id.save);
        view = findViewById(R.id.view);

        /*This is an event handler code for clicking on the date of birth input box (edtDob).
        When the user clicks on the date of birth input box, a calendar dialog box (DatePickerDialog) will display,
        allowing the user to select the day, month and year.*/
        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a Calendar object to get the current date
                Calendar calendar = Calendar.getInstance();

                //Get the current year
                int year = calendar.get(Calendar.YEAR);

                //Get the current month
                int month = calendar.get(Calendar.MONTH);

                //Get the current year
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //Create a calendar dialog box to allow users to select dates
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,

                        //Listen for events when the user selects a date
                        (view, year1, monthOfYear, dayOfMonth) -> {

                    //When the user selects, create the selected date, month, and year string
                            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;

                            //Displays the selected date string in the date of birth input field
                            edtDob.setText(selectedDate);
                        },

                        //Set default day, month, and year
                        year, month, day);

                //Show the calendar dialog to the user
                datePickerDialog.show();
            }
        });

        /*This is setting up a listener for the "Save" button. When the user clicks the "Save" button,
        the saveUserData() method will be called to save the data to the database.*/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });


        /*This is setting up the listener for the "View" button. When the user clicks on the "View" button,
        the viewUserData() method will be called to open a new activity (DetailsActivity) to display data from the database.*/
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUserData();
            }
        });
    }


    /*This method is called when the user presses the "Save" button. It retrieves data from the input fields,
    creates a ContentValues object containing the data, opens the database to write the data to the "users" table,
    and displays the corresponding message based on the result of the insert method.*/
    private void saveUserData() {
        //Get data from input fields
        String name = edtName.getText().toString();
        String dob = edtDob.getText().toString();
        String email = edtEmail.getText().toString();
        String avatar = getSelectedAvatar();

        // Check if name, date of birth and email are not blank
        if (name.isEmpty() || dob.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if email does not contain "@gmail.com"
        if (!email.endsWith("@gmail.com")) {
            Toast.makeText(this, "Invalid email address. Please use a Gmail address.", Toast.LENGTH_SHORT).show();
            return;
        }

        //Get the ID of the selected RadioButton from the RadioGroup and assign the corresponding avatar value
        int selectedAvatarId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();

        //Set avatar to "avatar1" if avt1 RadioButton is selected
        if (selectedAvatarId == R.id.avt1) {
            avatar = "avatar1";

            //Set avatar to "avatar2" if avt2 RadioButton is selected
        } else if (selectedAvatarId == R.id.avt2) {
            avatar = "avatar2";

            //Set avatar to "avatar3" if avt3 RadioButton is selected
        } else if (selectedAvatarId == R.id.avt3) {
            avatar = "avatar3";
        }

        //Create a new object of DatabaseHelper class in MainActivity
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        //Open the database to write data to
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Create a ContentValues object to hold the data
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("DOB", dob);
        contentValues.put("Email", email);
        contentValues.put("Avatar", avatar);

        //Add data to "users" table
        long result = db.insert("users", null, contentValues);

        //Check the results and display the corresponding message
        if (result == -1) {
            Toast.makeText(this, "Error while saving data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "The data has been saved successfully", Toast.LENGTH_SHORT).show();
        }

        //Close the database
        db.close();
    }


    /*This method creates an Intent to open the Details Activity, where data from the database will be displayed.*/
    private void viewUserData() {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        startActivity(intent);
    }

    /*This method returns the value of the RadioButton selected in the RadioGroup. It determines which RadioButton is selected
    using the RadioGroup's getCheckedRadioButtonId() method and then gets the value of the selected RadioButton using getText().
    If no RadioButton is selected, the method returns an empty string.*/
    private String getSelectedAvatar() {

        //Find the RadioGroup in the layout using its ID
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        //Get the ID of the selected RadioButton from the RadioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

        //Find the selected RadioButton using its ID
        RadioButton selectedRadioButton = findViewById(selectedId);

        //Check if a RadioButton is selected
        if (selectedRadioButton != null) {

            //If a RadioButton is selected, return its text as the selected avatar
            return selectedRadioButton.getText().toString();
        }

        //If no RadioButton is selected, return an empty string
        return "";
    }
}