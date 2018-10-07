package com.cybermongols.foodpicker;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    /**
     * Called when the user taps the Pick Your Lunch button.
     * Fills the predefined TextView with some random value.
     */
    public void returnRandomLunch(View view) {
        //gets all entrys from the food col so we can set up a maximum for our random range.
        Cursor c = mDb.rawQuery("select Food from food_types", null);
        int max = c.getCount();

        //generates random number
        Random r = new Random();
        int num = r.nextInt(max - 1) + 1;

        //selects one food from the db based on the random index we just created then stores in an
        //array of strings because for some reason even though there is only one string the
        //getString() method outputs to an array i guess.
        c = mDb.rawQuery("select Food from food_types where _id = " + num, null);
        c.moveToFirst();
        String[] foods = new String[1];
        foods[0] = c.getString(0);
        c.close();
        String randomLunch = foods[0];

        TextView yourLunchView = findViewById(R.id.textYourLunch);
        yourLunchView.setText(randomLunch);
    }
}
