package com.example.pr_idi.mydatabaseexample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Filmoteca extends DrawerActivity {

    private FilmData filmData;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmoteca);
        setTitle("Filmoteca");
        //LECTURA DE LES DADES DE LA BD I TRACTAMENT PER A POSARLOS AL ADAPTER
        //list of films = ArrayList? si, suposo
        filmData = new FilmData(this);
        filmData.open();
        List<Film> dades = filmData.getAllFilms();

        Collections.sort(dades, new Comparator<Film>() {
            @Override
            public int compare(Film lhs, Film rhs) {
                Integer a1 = lhs.getYear();
                Integer a2 = rhs.getYear();
                Integer resta = a1 - a2;
                if (a1.compareTo(a2) < 0) {
                    return 1;
                } else if (a1.compareTo(a2) > 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });


        //CREACIO DEL RECYCLER VIEW

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_filmoteca);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        //myDataset: llegir bd i passar per aqui els valors.
        mAdapter = new FilmAdapter(dades);
        mRecyclerView.setAdapter(mAdapter);
    }
}

