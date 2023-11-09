/*This line specifies the package of the class, indicating that it is located in the package com.example.logbook3.*/
package com.example.logbook3;

/*These lines import the classes and interfaces needed to work with SQLite and Android.*/
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*This is the declaration of the DatabaseHelper class, inheriting from SQLiteOpenHelper.
SQLiteOpenHelper is a helper class that helps create and manage SQLite databases.*/
public class DatabaseHelper extends SQLiteOpenHelper {

    /*These are constants used to name the database (Logbook3.db), table names (users), and table columns (ID, NAME, DOB, EMAIL, AVATAR).*/
    private static final String DATABASE_NAME = "Logbook3.db";
    private static final String TABLE_NAME = "users";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String DOB = "DOB";
    private static final String COL_EMAIL = "EMAIL";
    private static final String AVATAR = "AVATAR";

    /*This is the constructor of the DatabaseHelper class. It calls the constructor of the SQLiteOpenHelper base class
    with the parameters context, database name, null (for CursorFactory, not used), and database version.*/
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /*The onCreate method is called when the database is first created. It contains the SQL command to create the users
    table with predefined columns.*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                DOB + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                AVATAR + " TEXT)"
        );
    }

    /*The onUpgrade method is called when the version of the database already exists and is upgraded.
    In this case, it deletes the old table (if it exists) and calls the onCreate method again to create the new table.*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*This method is used to insert data into the users table. It creates a ContentValues object, sets the values for the columns
    from the parameters passed in, and then uses SQLiteDatabase's insert method to add data to the table. If the insertion is successful,
    it returns true, otherwise it returns false.*/
    public boolean insertData(String name, String dob, String email, String avatar) {

        //Get a writable database instance to perform the insertion operation
        SQLiteDatabase db = this.getWritableDatabase();

        //Create a ContentValues object to store the user data
        ContentValues contentValues = new ContentValues();

        //Put the name into ContentValues
        contentValues.put(NAME, name);
        contentValues.put(DOB, dob);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(AVATAR, avatar);

        //Insert the data into the database table
        long result = db.insert(TABLE_NAME, null, contentValues);

        //Check if the insertion was successful (result is not -1) and return the result
        return result != -1;
    }

    /*This method returns a Cursor containing all the data from the users table. A Cursor is a pointer to a collection of
    data from the database. Data is queried through the SQL statement defined in the query variable.
    The ID column was renamed _id for use with Android UI elements. Finally, the rawQuery method returns a Cursor containing
    the result of the SQL query.*/
    public Cursor getAllUserData() {
        //Get a readable database instance to perform the read operation
        SQLiteDatabase db = this.getReadableDatabase();

        //SQL query to select all data from the users table, aliasing the ID column as "_id"
        String query = "SELECT ID as _id, NAME, DOB, EMAIL, AVATAR FROM " + TABLE_NAME;

        //Execute the query and return the resulting Cursor containing the user data
        return db.rawQuery(query, null);
    }
}
