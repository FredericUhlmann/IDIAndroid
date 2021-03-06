package com.example.pr_idi.mydatabaseexample;

/**
 * FilmData
 * Created by pr_idi on 10/11/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FilmData {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    // allColumns actualitzat amb tots els camps necesaris
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE,
            MySQLiteHelper.COLUMN_COUNTRY,
            MySQLiteHelper.COLUMN_YEAR_RELEASE,
            MySQLiteHelper.COLUMN_DIRECTOR,
            MySQLiteHelper.COLUMN_PROTAGONIST,
            MySQLiteHelper.COLUMN_CRITICS_RATE
    };

    public FilmData(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Film createFilm(Film peli) {
        ContentValues values = new ContentValues();
        Log.d("Creating", "Creating " + peli.getTitle() + " " + peli.getDirector() + peli.getProtagonist());

        // Add data: Note that this method only provides title and director
        // Must modify the method to add the full data
        values.put(MySQLiteHelper.COLUMN_TITLE, peli.getTitle());
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, peli.getDirector());
        values.put(MySQLiteHelper.COLUMN_COUNTRY, peli.getCountry());
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, peli.getYear());
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, peli.getProtagonist());
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, peli.getCritics_rate());

        // Actual insertion of the data using the values variable
        long insertId = database.insert(MySQLiteHelper.TABLE_FILMS, null,
                values);

        // Main activity calls this procedure to create a new film
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the database
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Film newFilm = cursorToFilm(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newFilm;
    }

    public void deleteFilm(Film film) {
        long id = film.getId();
        System.out.println("Film deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Film> getAllFilms() {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public void modificarNota(Film peli, int nota){
        long id = peli.getId();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE,nota);
        database.update(MySQLiteHelper.TABLE_FILMS, values, MySQLiteHelper.COLUMN_ID + "=" + id, null );
    }

    private Film cursorToFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(0));
        film.setTitle(cursor.getString(1));
        film.setCountry(cursor.getString(2));
        film.setYear(cursor.getInt(3));
        film.setDirector(cursor.getString(4));
        film.setProtagonist(cursor.getString(5));
        film.setCritics_rate(cursor.getInt(6));

        return film;
    }

}