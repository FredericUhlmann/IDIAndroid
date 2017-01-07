package com.example.pr_idi.mydatabaseexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Agregar extends DrawerActivity {

    private FilmData filmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        setTitle("Agregar");

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
                if (!a_critiques.equals("")) {
                     num_critiques = Integer.parseInt(a_critiques);
                }


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

                    //Se muestra un toast indicando que todos los campos son obligatorios
                    Toast toast = Toast.makeText(getApplicationContext(), "La nota ha de ser entre 0 i 10", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    /////////peta en el film data!!!!!(sols falta conectarho a la BD)
                    //filmData.createFilm(a_titol, a_director);
                    Toast toast = Toast.makeText(getApplicationContext(), "CREADA", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });

    }

}
