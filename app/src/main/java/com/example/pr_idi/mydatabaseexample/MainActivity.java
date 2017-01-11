package com.example.pr_idi.mydatabaseexample;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends DrawerActivity {
    private FilmData filmData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("Llista de pel·lícules");

        filmData = new FilmData(this);
        filmData.open();

        creaPelisBase();

        List<Film> values = filmData.getAllFilms();

        //ordenem les pelis per titol
        Collections.sort(values, new Comparator<Film>() {
            @Override
            public int compare(Film film1, Film film2) {
                return film1.getTitle().compareTo(film2.getTitle());
            }
        });

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);

        ListView lv = (ListView) findViewById(R.id.listmain);
        lv.setAdapter(adapter);

        //implementem clicklistener per a quan clikem a una peli
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = adapterView.getAdapter().getItem(i).toString();
                Intent intent = new Intent(getApplicationContext(), InfoPeli.class);
                //li passem el intent amb el titol de la peli
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ListView lv = (ListView) findViewById(R.id.listmain);
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) lv.getAdapter();
        List<Film> values = filmData.getAllFilms();
        switch (view.getId()) {
            case R.id.ordertitol:
                //ordenem per titol
                Collections.sort(values, new Comparator<Film>() {
                    @Override
                    public int compare(Film film1, Film film2) {
                        return film1.getTitle().compareTo(film2.getTitle());
                    }
                });

                // use the SimpleCursorAdapter to show the
                // elements in a ListView
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, values);

                lv.setAdapter(adapter);
                break;
            case R.id.orderdirector:
                //ordenem per director
                Collections.sort(values, new Comparator<Film>() {
                    @Override
                    public int compare(Film film1, Film film2) {
                        return film1.getDirector().compareTo(film2.getDirector());
                    }
                });

                // use the SimpleCursorAdapter to show the
                // elements in a ListView
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, values);

                lv.setAdapter(adapter);
                break;
            case R.id.orderany:
                //ordenem per any
                Collections.sort(values, new Comparator<Film>() {
                    @Override
                    public int compare(Film film1, Film film2) {
                        return String.valueOf(film1.getYear()).compareTo(String.valueOf(film2.getYear()));
                    }
                });

                // use the SimpleCursorAdapter to show the
                // elements in a ListView
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, values);

                lv.setAdapter(adapter);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private void creaPelisBase(){

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("primercop", true)) {
            // first time task
            Film peli = new Film();
            peli.setDirector("Christopher Nolan");
            peli.setCountry("USA");
            peli.setProtagonist("Leonardo DiCaprio");
            peli.setTitle("Inception");
            peli.setYear(2010);
            peli.setCritics_rate(9);
            Film peli2 = new Film();
            peli2.setDirector("Quentin Tarantino");
            peli2.setCountry("USA");
            peli2.setProtagonist("Jamie Foxx");
            peli2.setTitle("Django Unchained");
            peli2.setYear(2012);
            peli2.setCritics_rate(8);
            Film peli3 = new Film();
            peli3.setDirector("Hermanas Wachowski");
            peli3.setCountry("USA / Australia");
            peli3.setProtagonist("Keanu Reeves");
            peli3.setTitle("Matrix");
            peli3.setYear(1999);
            peli3.setCritics_rate(10);
            Film peli4 = new Film();
            peli4.setDirector("Robert Zemeckis");
            peli4.setCountry("USA");
            peli4.setProtagonist("Tom Hanks");
            peli4.setTitle("Forrest Gump");
            peli4.setYear(1994);
            peli4.setCritics_rate(10);
            filmData.createFilm(peli);
            filmData.createFilm(peli2);
            filmData.createFilm(peli3);
            filmData.createFilm(peli4);
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("primercop", false).commit();
        }

    }

    @Override
    protected void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

}