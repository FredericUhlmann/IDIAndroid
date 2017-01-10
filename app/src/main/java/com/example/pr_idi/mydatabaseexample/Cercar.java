package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        System.out.println(values.get(0).getProtagonist());

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


        SearchView searchView = (SearchView) findViewById(R.id.cerca_view);
        searchView.setQueryHint("Buscar actor...");

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
        for (Film peli : values) {

            String prota = peli.getProtagonist();

            if (prota.toLowerCase().contains(query.toLowerCase())){
                newvalues.add(peli);
            }

            ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, newvalues);

            ListView lv = (ListView) findViewById(R.id.llista_cercar);
            lv.setAdapter(adapter);
        }
    }





}
