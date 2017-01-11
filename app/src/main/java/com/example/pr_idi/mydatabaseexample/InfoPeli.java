package com.example.pr_idi.mydatabaseexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class InfoPeli extends DrawerActivity {

    private FilmData filmData;
    private Film peli;
    private String nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_peli);
        setTitle("Informació de la pel·lícula");

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();

        //agafem el intent que ens han enviat amb el titol de la peli
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        //separem el titol de la peli
        String[] separated = title.split("-");
        title = separated[0];
        title = title.replaceAll("\\s+$", ""); //eliminar espai final en blanc

        //busquem la pelicula en questio
        for (Film movie : values) {
            if (movie.getTitle().equals(title)){
                peli = movie;
            }
        }

        //busquem els textviews
        TextView tvtitol = (TextView) findViewById(R.id.titol);
        TextView tvnota = (TextView) findViewById(R.id.nota);
        TextView tvdirector = (TextView) findViewById(R.id.director);
        TextView tvprota = (TextView) findViewById(R.id.prota);
        TextView tvany = (TextView) findViewById(R.id.any);
        TextView tvpais = (TextView) findViewById(R.id.pais);

        //assignem el text als camps
        tvtitol.setText(peli.getTitle());
        tvnota.setText(String.valueOf(peli.getCritics_rate()));
        tvdirector.setText(peli.getDirector());
        tvprota.setText(peli.getProtagonist());
        tvany.setText(String.valueOf(peli.getYear()));
        tvpais.setText(peli.getCountry());
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modificar_nota:
                modificarNota();
                break;

            case R.id.eliminar:
                eliminarPeli();
                break;
        }

    }

    private void canviaNota(String nota){
        int notaint = Integer.parseInt(nota);
        //canviem la nota si esta entre 0-10
        if (notaint >= 0 && notaint <= 10) {
            filmData.modificarNota(peli,notaint);
            String title = peli.getTitle();
            Intent intent = new Intent(getApplicationContext(), InfoPeli.class);
            intent.putExtra("title", title);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "La nota ha d'estar entre 0 i 10.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void modificarNota() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nova puntuació");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Cuadre de dialog preguntant la nova nota
        builder.setPositiveButton("MODIFICAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nota = input.getText().toString();
                canviaNota(nota);
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void eliminarPeli(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        filmData.deleteFilm(peli);
                        Toast toast = Toast.makeText(getApplicationContext(), "Pel·lícula " + peli.getTitle() + " eliminada.", Toast.LENGTH_SHORT);
                        toast.show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        //dialolg per a confirmar l'esborrat de la peli
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("Segur que vols eliminar la pel·lícula?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
