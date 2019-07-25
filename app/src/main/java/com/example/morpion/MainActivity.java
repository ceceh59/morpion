package com.example.morpion;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Tableau a 2 dimensions
    //plateau[colonne][ligne]
    // 0 : case vide
    // 1 : X
    // 2 : O
    private int plateau[][] = new int[3][3];

    //1 : X
    //2 : O
    private int joueurEnCours = 1;

    private TextView tvJoueur;

    ArrayList<Button> all_buttons = new ArrayList<>();

    // attributs connexion à la base pour enregistrer les scores et les noms

    private TextView scoresView;
    private DataBase dataBase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue);

        tvJoueur = (TextView) findViewById(R.id.joueur);

        Button bt1 = (Button) findViewById(R.id.bt1);
        Button bt2 = (Button) findViewById(R.id.bt2);
        Button bt3 = (Button) findViewById(R.id.bt3);
        Button bt4 = (Button) findViewById(R.id.bt4);
        Button bt5 = (Button) findViewById(R.id.bt5);
        Button bt6 = (Button) findViewById(R.id.bt6);
        Button bt7 = (Button) findViewById(R.id.bt7);
        Button bt8 = (Button) findViewById(R.id.bt8);
        Button bt9 = (Button) findViewById(R.id.bt9);

        all_buttons.add(bt1);
        all_buttons.add(bt2);
        all_buttons.add(bt3);
        all_buttons.add(bt4);
        all_buttons.add(bt5);
        all_buttons.add(bt6);
        all_buttons.add(bt7);
        all_buttons.add(bt8);
        all_buttons.add(bt9);

        for (Button bt : all_buttons) {
            bt.setBackgroundDrawable(null);
            bt.setOnClickListener(this);
        }
// insertion des scores

        scoresView = (TextView) findViewById(R.id.scoresView);
        dataBase = new DataBase(this);

        //dataBase.insertScore("Bruno", 100);
        //dataBase.insertScore("Christelle", 50);
        //dataBase.insertScore("Dominique", 20);
        //  dataBase.insertScore("Celine",450);
        // dataBase.insertScore("didier",100);
        //dataBase.insertScore("roger",1500);

        List<ScoreData> scores = dataBase.readTop10();
        for (ScoreData score : scores) {
            scoresView.append(score.toString() +"\n\n");
        }

        dataBase.close();
    }

    // insertion des noms et scores






    @Override
    public void onClick(View view) {

        if (view.getBackground() != null)

            return;


        switch (view.getId()) {

            case R.id.bt1:
                plateau[0][0] = joueurEnCours;
                break;
            case R.id.bt2:
                plateau[1][0] = joueurEnCours;
                break;
            case R.id.bt3:
                plateau[2][0] = joueurEnCours;
                break;
            case R.id.bt4:
                plateau[0][1] = joueurEnCours;
                break;
            case R.id.bt5:
                plateau[1][1] = joueurEnCours;
                break;
            case R.id.bt6:
                plateau[2][1] = joueurEnCours;
                break;
            case R.id.bt7:
                plateau[0][2] = joueurEnCours;
                break;
            case R.id.bt8:
                plateau[1][2] = joueurEnCours;
                break;
            case R.id.bt9:
                plateau[2][2] = joueurEnCours;
                break;

            default:
                return;


        }


        Drawable drawableJoueur;
        if (joueurEnCours == 1)
            drawableJoueur = ContextCompat.getDrawable(this, R.drawable.iconex);
        else
            drawableJoueur = ContextCompat.getDrawable(this, R.drawable.rondvide);
        view.setBackgroundDrawable(drawableJoueur);

        if (joueurEnCours == 1) {
            joueurEnCours = 2;
            tvJoueur.setText("O");
        } else {
            joueurEnCours = 1;
            tvJoueur.setText("X");
        }


        int res = checkWinner();
        displayAlertDialog(res);


    }
    //0 : partie non fini
    //1 : X
    //2 : O
    //3 : egalité


    private int checkWinner() {


        // on regarde s'il y a un gagnant sur les colonnes
        for (int col = 0; col <= 2; col++) {

            if (plateau[col][0] != 0 && plateau[col][0] == plateau[col][1] && plateau[col][0] == plateau[col][2])
                return plateau[col][0];

        } // on regarde s'il y a un gagnant sur les lignes
        for (int line = 0; line <= 2; line++) {

            if (plateau[0][line] != 0 && plateau[0][line] == plateau[1][line] && plateau[0][line] == plateau[2][line])
                return plateau[0][line];

        } // on regarde s'il y a un gagnant sur la diagonale haut/gauche -> bas/droite
        if (plateau[0][0] != 0 && plateau[0][0] == plateau[1][1] && plateau[0][0] == plateau[2][2])
            return plateau[0][0];

        // on regarde s'il y a un gagnant sur la diagonale haut/droite -> bas/gauche
        if (plateau[2][0] != 0 && plateau[2][0] == plateau[1][1] && plateau[2][0] == plateau[0][2])
            return plateau[2][0];

        // égalité
        boolean isPlateauPlein = true;
        for (int col = 0; col <= 2; col++) {
            for (int line = 0; line <= 2; line++) {
                if (plateau[col][line] == 0) {
                    isPlateauPlein = false;
                    break;
                }
            }
            if (!isPlateauPlein)
                break;
        }
        if (isPlateauPlein)
            return 3;

        // partie non finie
        return 0;
    }

    //0 : partie non fini
    //1 : X
    //2 : O
    //3 : egalité
    private void displayAlertDialog(int res) {
        if (res == 0) // partie non terminée
            return;

        String strToDisplay = "";
        if (res == 1)
            strToDisplay = "Les X ont gagnés ! ";

        if (res == 2)
            strToDisplay = "Les O ont gagnés ! ";
        if (res == 3)
            strToDisplay = "Egalité ! ";

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Fin de la partie");
        alertDialog.setMessage(strToDisplay);

        alertDialog.setNeutralButton("Recommencer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void resetGame() {
        for (int col = 0; col <= 2; col++) {
            for (int line = 0; line <= 2; line++) {
                plateau[col][line] = 0;
            }
        }
        for (Button bt : all_buttons) {
            bt.setBackgroundDrawable(null);
        }
    }


}