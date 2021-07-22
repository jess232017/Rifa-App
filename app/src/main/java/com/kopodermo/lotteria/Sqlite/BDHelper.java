package com.kopodermo.lotteria.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.kopodermo.lotteria.Sqlite.Constants.Numeros;
import com.kopodermo.lotteria.Sqlite.Constants.Rifas;
import com.kopodermo.lotteria.Sqlite.Constants.Usuario;

public class BDHelper extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "pedidos.db";

    private static final int VERSION_ACTUAL = 1;

    private Context contexto;

    interface Tablas{
        String USUARIO = "usuario";
        String RIFA = "rifa";
        String NUMERO = "numero";
    }

    public BDHelper(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL UNIQUE,%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL)",
                Tablas.USUARIO, BaseColumns._ID,
                Usuario.ID, Usuario.CORREO, Usuario.NOMBRE, Usuario.PASS));

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL UNIQUE,%s TEXT NOT NULL,%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL,%s TEXT NOT NULL)",
                Tablas.RIFA, BaseColumns._ID,
                Rifas.ID, Rifas.TITULO, Rifas.PREMIO,
                Rifas.FECHA, Rifas.VALOR));

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL UNIQUE,%s TEXT NOT NULL," +
                        "%s INTEGER NOT NULL,%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL)",
                Tablas.NUMERO, BaseColumns._ID,
                Numeros.ID,Numeros.ID_RIFA,
                Numeros.NUMERO, Numeros.CLIENTE,
                Numeros.PAGO));

        ContentValues valores = new ContentValues();
        String idUSUARIO = Usuario.generarIdNumero();
        valores.put(Usuario.ID, idUSUARIO);
        valores.put(Usuario.NOMBRE, "Default");
        valores.put(Usuario.CORREO, "Default");
        valores.put(Usuario.PASS, "Default");
        db.insertOrThrow(Tablas.USUARIO, null, valores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tablas.USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.RIFA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.NUMERO);

        onCreate(db);
    }
}
