package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class Agregar extends DrawerActivity {

    private FilmData filmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        setTitle("Agregar pel·lícula");


        filmData = new FilmData(this);
        filmData.open();

        //Boton
        Button b_afegir = (Button) findViewById(R.id.b_afegir);

        //EditTexts
        final EditText et_afegir_titol = (EditText) findViewById(R.id.et_afegir_titol);
        final EditText et_afegir_pais = (EditText) findViewById(R.id.et_afegir_pais);
        final EditText et_afegir_any = (EditText) findViewById(R.id.et_afegir_any);
        final EditText et_et_afegir_director = (EditText) findViewById(R.id.et_afegir_director);
        final EditText et_afegir_prota = (EditText) findViewById(R.id.et_afegir_prota);
        final EditText et_afegir_critiques = (EditText) findViewById(R.id.et_afegir_critiques);


        //Listener al boton
        b_afegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String a_titol = et_afegir_titol.getText().toString();
                String a_pais = et_afegir_pais.getText().toString();
                String a_any = et_afegir_any.getText().toString();
                String a_director = et_et_afegir_director.getText().toString();
                String a_prota = et_afegir_prota.getText().toString();
                String a_critiques = et_afegir_critiques.getText().toString();
                Integer num_critiques = 0;
                Integer num_any = 0;

                if (!a_critiques.equals("")) {
                     num_critiques = Integer.parseInt(a_critiques);
                }

                if (!a_any.equals("")) {
                    num_any = Integer.parseInt(a_any);
                }

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);

                if(
                    a_titol.equals("") ||
                    a_pais.equals("") ||
                    a_any.equals("") ||
                    a_director.equals("") ||
                    a_prota.equals("") ||
                    a_critiques.equals("")

                    ) {

                    //Se muestra un toast indicando que todos los campos son obligatorios
                    Toast toast = Toast.makeText(getApplicationContext(), "Tots els camps són obligatoris", Toast.LENGTH_SHORT);
                    toast.show();

                }else if (num_critiques < 0 || num_critiques > 10){

                    //Se muestra un toast indicando que la nota tiene que estar entre 0 y 10
                    Toast toast = Toast.makeText(getApplicationContext(), "La nota ha de ser entre 0 i 10", Toast.LENGTH_SHORT);
                    toast.show();

                }else if (num_any < 1800 || num_any > year){

                    //Se muestra un toast indicando que el año de estrena tiene que estar entre 1800 y any actual
                    Toast toast = Toast.makeText(getApplicationContext(), "L'any d'estrena ha de ser entre 1800 i " + year, Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    Film peli = new Film();

                    peli.setTitle(a_titol);
                    peli.setCountry(a_pais);
                    peli.setYear(Integer.valueOf(a_any));
                    peli.setDirector(a_director);
                    peli.setProtagonist(a_prota);
                    peli.setCritics_rate(Integer.valueOf(a_critiques));

                    Boolean b = true;
                    List<Film> comprobacio = filmData.getAllFilms();
                    for (Film movie : comprobacio) {
                        if (movie.getTitle().equals(a_titol))b = false;
                    }
                    if (b) {
                        filmData.createFilm(peli);

                        Toast toast = Toast.makeText(getApplicationContext(), "CREADA", Toast.LENGTH_SHORT);
                        toast.show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else {
                        //Se muestra un toast indicando que esta palícula ya existe
                        Toast toast = Toast.makeText(getApplicationContext(), "Aquesta pel·lícula ja està afegida", Toast.LENGTH_SHORT);
                        toast.show();
                    }


                }
            }
        });

    }

}
