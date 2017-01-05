package com.example.pr_idi.mydatabaseexample;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class Home extends DrawerActivity {

    private FilmData filmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();
        //ListView lv = (ListView) findViewById(R.id.listmain);
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);


        ListView lv = (ListView) findViewById(R.id.llistapelis);
        lv.setAdapter(adapter);

        /*
        adapter = (ArrayAdapter<Film>) lv.getAdapter();
        Film film;
        String[] newFilm = new String[] { "Polla"};
        int nextInt = new Random().nextInt(4);
        // save the new film to the database
        film = filmData.createFilm(newFilm[0], newFilm[0]);
        adapter.add(film);*/
    }
/*
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ListView lv = (ListView) findViewById(R.id.llistapelis);
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) lv.getAdapter();
        Film film;
        switch (view.getId()) {
            case R.id.afegir:
                String[] newFilm = new String[] { "Blade Runner", "Ridley Scott", "Rocky Horror Picture Show", "Jim Sharman", "The Godfather", "Francis Ford Coppola", "Toy Story", "John Lasseter" };
                int nextInt = new Random().nextInt(4);
                // save the new film to the database
                film = filmData.createFilm(newFilm[nextInt*2], newFilm[nextInt*2 + 1]);
                adapter.add(film);
                break;
            /*
            case R.id.delete:
                if (lv.getCount() > 0) {
                    film = (Film) lv.getAdapter().getItem(lv.getAdapter().getCount()-1);
                    filmData.deleteFilm(film);
                    adapter.remove(film);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }*/
}
