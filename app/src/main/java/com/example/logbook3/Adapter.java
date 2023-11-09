/*This line specifies the package of the UserCursorAdapter.java file.*/
package com.example.logbook3;

/*These import lines inject the necessary classes and interfaces from the Android library.
For example, Context, Cursor, View, ViewGroup, CursorAdapter, ImageView, and TextView are classes used in adapters.*/
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*Define a class UserCursorAdapter that extends from CursorAdapter, which is an adapter used to display data from a
Cursor (results of a database query) into a list (here, TextViews and ImageViews).*/
public class Adapter extends CursorAdapter {

/*This is the initialization function of UserCursorAdapter. This function assigns context and cursor values
to the parent adapter (CursorAdapter). The third parameter (here 0) is a flag that specifies the behavior mode of the adapter.*/
    public Adapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

/*This method creates a new View to display data from the cursor. It inflates a layout from the activity_item.xml
file into a View object and returns it.*/
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
    }

/*This method is called every time a View needs to display data from the cursor. It takes the values
from the cursor and places them into the corresponding TextView and ImageView. If the value of the "AVATAR"
field from the cursor is "avatar1", "avatar2" or "avatar3", the avatar image is set accordingly.*/
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Get the TextViews and ImageViews from the view
        TextView idTv = view.findViewById(R.id.id_txt);
        TextView nameTv = view.findViewById(R.id.name_txt);
        TextView dobTv = view.findViewById(R.id.dob_txt);
        TextView emailTv = view.findViewById(R.id.email_txt);
        ImageView avatarImage= view.findViewById(R.id.avatar_txt);

        //Get data from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
        String dob = cursor.getString(cursor.getColumnIndexOrThrow("DOB"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"));
        String avatar = cursor.getString(cursor.getColumnIndexOrThrow("AVATAR"));

        //Set data into the TextView
        idTv.setText("ID: " + cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        nameTv.setText("Name: " + name);
        dobTv.setText("DOB: " + dob);
        emailTv.setText("Email: " + email);

        //Set avatar image based on "avatar" value from cursor
        if (avatar.equals("avatar1")) {
            avatarImage.setImageResource(R.drawable.avatar1);
        } else if (avatar.equals("avatar2")) {
            avatarImage.setImageResource(R.drawable.avatar2);
        } else if (avatar.equals("avatar3")) {
            avatarImage.setImageResource(R.drawable.avatar3);
        }
    }
}
