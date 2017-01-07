package com.example.pr_idi.mydatabaseexample;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Cercar extends DrawerActivity {

    private FilmData filmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cercar);
        setTitle("Cercar");

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();

        //ordenem les pelis per titol
        Collections.sort(values, new Comparator<Film>() {
            @Override
            public int compare(Film peli1, Film peli2) {
                return peli1.getTitle().compareTo(peli2.getTitle());
            }
        });

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);

        ListView lv = (ListView) findViewById(R.id.llista_cercar);
        lv.setAdapter(adapter);


        SearchView searchView = (SearchView) findViewById(R.id.cerca_view);
        searchView.setQueryHint("Buscar titol...");

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!query.isEmpty()) {
                    search(query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onQueryTextSubmit(newText);
                return true;
            }

        });
    }

    protected void search(String query){
        List<Film> values = filmData.getAllFilms();
        List<Film> newvalues = new ArrayList<>();

        //per cada pelicula, mirar si conte el autor
        for (int i = 0; i < values.size(); i++) {
            Film peli = values.get(i);
            String prota = peli.getTitle();
            System.out.println(prota);
            //System.out.println(prota.toLowerCase().contains(query.toLowerCase()));
            if (prota.toLowerCase().contains(query.toLowerCase())){
                newvalues.add(peli);
            }

            ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, newvalues);

            ListView lv = (ListView) findViewById(R.id.llista_cercar);
            lv.setAdapter(adapter);
            //System.out.println(autor.toLowerCase().contains(query.toLowerCase()));
            //System.out.println(peli.);
        }
    }





}
