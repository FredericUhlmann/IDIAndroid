package com.example.pr_idi.mydatabaseexample;


import android.content.Intent;
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
        setTitle("Llista de pelicules");

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();

        if (values.size() == 0){
            creaPelisBase();
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
        System.out.println("Hola");
        System.out.println(peli4.getProtagonist());
        filmData.createFilm(peli);
        filmData.createFilm(peli2);
        filmData.createFilm(peli3);
        filmData.createFilm(peli4);
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