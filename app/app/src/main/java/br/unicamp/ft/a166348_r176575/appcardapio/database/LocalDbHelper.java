package br.unicamp.ft.a166348_r176575.appcardapio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by andre on 06/06/2018.
 */

public class LocalDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MYDB";
    private static final int DB_VERSION = 1;

    public LocalDbHelper(Context context) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE client_data ( " +
                "data_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name char(50)," +
                "insertion_date datetime DEFAULT CURRENT_TIMESTAMP," +
                "people_on_table tinyint(8) DEFAULT '1'," +
                "client_sex char NOT NULL DEFAULT 'O'" +
                " )" );


        db.execSQL( "CREATE TABLE visit_data (" +
                "visit_id INTEGER PRIMARY KEY," +
                "insertion_date datetime DEFAULT CURRENT_TIMESTAMP" +
                ")" );


        db.execSQL( " CREATE TABLE product_on_cart ( " +
                "cart_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "insertion_date datetime DEFAULT CURRENT_TIMESTAMP, " +
                "product_id INTEGER, " +
                "amount iNTEGER, " +
                "state CHAR(50))" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
