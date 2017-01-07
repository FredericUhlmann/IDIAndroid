package com.example.pr_idi.mydatabaseexample;


import android.os.Bundle;
import android.view.View;
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

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();

        if (values.size() == 0){
            Film peli = new Film();
            peli.setDirector("Frederic Uhlman");
            peli.setCountry("Catalunya");
            peli.setProtagonist("Pol Alejandre");
            peli.setTitle("Els putus");
            peli.setYear(2017);
            peli.setCritics_rate(10);
            Film peli2 = new Film();
            peli2.setDirector("Pol Alejandre");
            peli2.setCountry("Catalunya");
            peli2.setProtagonist("Frederic Uhlman");
            peli2.setTitle("Els putus 2");
            peli2.setYear(2017);
            peli2.setCritics_rate(10);
            Film peli3 = new Film();
            peli3.setDirector("Joan Vicent");
            peli3.setCountry("Catalunya");
            peli3.setProtagonist("Pol Alejandre");
            peli3.setTitle("El faker");
            peli3.setYear(2017);
            peli3.setCritics_rate(10);
            Film peli4 = new Film();
            peli4.setDirector("Frederic Uhlman");
            peli4.setCountry("Catalunya");
            peli4.setProtagonist("Pol Alejandre");
            peli4.setTitle("Els putus 3");
            peli4.setYear(2017);
            peli4.setCritics_rate(10);
            filmData.createFilm(peli.getTitle(),peli.getDirector(),peli.getCountry(),peli.getYear(),peli.getProtagonist(),peli.getCritics_rate());
            filmData.createFilm(peli2.getTitle(),peli2.getDirector(),peli2.getCountry(),peli2.getYear(),peli2.getProtagonist(),peli2.getCritics_rate());
            filmData.createFilm(peli3.getTitle(),peli3.getDirector(),peli3.getCountry(),peli3.getYear(),peli3.getProtagonist(),peli3.getCritics_rate());
            filmData.createFilm(peli4.getTitle(),peli4.getDirector(),peli4.getCountry(),peli4.getYear(),peli4.getProtagonist(),peli4.getCritics_rate());
            values = filmData.getAllFilms();
        }

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
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ListView lv = (ListView) findViewById(R.id.listmain);
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) lv.getAdapter();
        Film film;
        switch (view.getId()) {
            case R.id.delete:
                if (lv.getCount() > 0) {
                    film = (Film) lv.getAdapter().getItem(lv.getAdapter().getCount()-1);
                    filmData.deleteFilm(film);
                    adapter.remove(film);
                }
                break;
        }
        adapter.notifyDataSetChanged();
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