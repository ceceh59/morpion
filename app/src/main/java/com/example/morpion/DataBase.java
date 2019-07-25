package com.example.morpion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Morpion.db";
    private static final int DATABASE_VERSION = 2;
    private final Context context;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql ="create table T_Scores("
                +"idScore integer primary key autoincrement,"
                +"name text not null,"
                +"score integer not null,"
                +"when_t integer not null"
                +")";
        db.execSQL(strSql);
        Log.i("DATABASE","onCreate invoked");
    }

    //mettre a jour la base

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strSql =  "drop table T_scores";
        db.execSQL(strSql);
        this.onCreate(db);
        Log.i("DATABASE","onUpgrade invoked");
    }
    //pour insérer un score

    public void insertScore(String name,int score){
        name = name.replace("'","''");
        String strSql ="insert into T_scores(name, score, when_t) values('"
    +name+"',"+score+","+ new Date().getTime()+")";

        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE","insertScore invoked");
    }

    public List<ScoreData> readTop10(){
        List<ScoreData> scores = new ArrayList<>();

        String strSql = "select * from T_Scores order by score desc limit 10";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ScoreData score = new ScoreData( cursor.getInt(0),cursor.getString(1),
                    cursor.getInt(2),new Date(cursor.getInt(3)));
            scores.add(score);
            cursor.moveToNext();
        }
        cursor.close();
        return scores;
    }

// pour recuperer en base et modifier

   /* public void update(Context context, String itemId, String dishName, String dishPrice, String dishQty) {

        SQLiteDatabase db = new SQLiteDatabase(context);
        SQLiteDatabase db = SQLiteDatabase.();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CART_DISH_ID, itemId);
        values.put(DBHelper.CART_DISH_NAME, dishName);
        values.put(DBHelper.CART_DISH_PRICE, dishPrice);
        values.put(DBHelper.CART_DISH_QTY, dishQty);

        try {

            String[] args = new String[]{dishName};
            db.update(DBHelper.TABLE_CART, values, DBHelper.CART_DISH_NAME + "=?", args);

            db.close();


            return true;
        } catch (SQLiteException e) {
            db.close();

            return false;
        }
    }*/


    }





