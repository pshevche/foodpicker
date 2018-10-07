package com.cybermongols.foodpicker;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

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
        // TODO: generate random id
        Cursor c = mDb.rawQuery("select * from food_types", null);
        String randomLunch = "# of options: " + c.getCount();
        TextView yourLunchView = findViewById(R.id.textYourLunch);
        yourLunchView.setText(randomLunch);
    }
}
