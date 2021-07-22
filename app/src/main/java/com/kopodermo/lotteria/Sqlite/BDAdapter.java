package com.kopodermo.lotteria.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.kopodermo.lotteria.Model.Numero;
import com.kopodermo.lotteria.Model.Rifa;
import com.kopodermo.lotteria.Sqlite.Constants.Numeros;
import com.kopodermo.lotteria.Sqlite.Constants.Rifas;

import static com.kopodermo.lotteria.Sqlite.BDHelper.*;
import static com.kopodermo.lotteria.Sqlite.Constants.*;


public class BDAdapter {
    //-----------------
    private SQLiteDatabase db;
    private BDHelper helper;



    public BDAdapter(Context c) {
        helper=new BDHelper(c);
    }

    //Abrir Base de Datos
    public void openDB()
    {
        try
        {
            db=helper.getWritableDatabase();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    //Cerrar Base de Datos
    public void closeDB()
    {
        try
        {
            helper.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    //Obtener todas las rifas
    public Cursor obtenerRifas()
    {
        String sql="SELECT * FROM " + Tablas.RIFA + ";";
        return db.rawQuery(sql,null);
    }

    //Agregar rifas
    public String insertarRifa(Rifa rifa) {
        ContentValues valores = new ContentValues();
        // Generar Pk
        String idProducto = Rifas.generarIdRifa();
        valores.put(Rifas.ID, idProducto);
        valores.put(Rifas.TITULO, rifa.getTitulo());
        valores.put(Rifas.PREMIO, rifa.getPremio());
        valores.put(Rifas.FECHA, rifa.getFecha());
        valores.put(Rifas.VALOR, rifa.getValor());

        db.insertOrThrow(Tablas.RIFA, null, valores);

        return idProducto;
    }

    public boolean actualizarRifa(Rifa rifa) {
        ContentValues valores = new ContentValues();
        valores.put(Rifas.TITULO, rifa.getTitulo());
        valores.put(Rifas.PREMIO, rifa.getPremio());
        valores.put(Rifas.FECHA, rifa.getFecha());
        valores.put(Rifas.VALOR, rifa.getValor());

        String whereClause = String.format("%s=?", Rifas.ID);
        String[] whereArgs = {rifa.getID()};

        int resultado = db.update(Tablas.RIFA, valores, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarRifa(String idRifa) {
        String whereClause = String.format("%s=?", Rifas.ID);
        String[] whereArgs = {idRifa};

        int resultado = db.delete(Tablas.RIFA, whereClause, whereArgs);

        return resultado > 0;
    }

    //***Numero****//

    // Obtener los numeros de una rifa especifica
    public Cursor obtenerNumeroWhere(String IdRifa) {
        String sql1="SELECT * FROM " + Tablas.NUMERO + " WHERE " + Numeros.ID_RIFA + " LIKE '%" + IdRifa + "%'";
        return db.rawQuery(sql1,null);
    }

    //Obtener todas los numeros
    public Cursor obtenerNumero()
    {
        String sql="SELECT * FROM " + Tablas.NUMERO + ";";
        return db.rawQuery(sql,null);
    }

    //Agregar rifas
    public String insertarNumero(Numero numero) {
        ContentValues valores = new ContentValues();
        // Generar Pk
        String idProducto = Numeros.generarIdNumero();
        valores.put(Numeros.ID, idProducto);
        valores.put(Numeros.ID_RIFA, numero.getID_Rifa());
        valores.put(Numeros.NUMERO, numero.getNumero());
        valores.put(Numeros.CLIENTE, numero.getDueño());
        valores.put(Numeros.PAGO, numero.getPago());

        db.insertOrThrow(Tablas.NUMERO, null, valores);

        return idProducto;
    }

    public boolean actualizarNumero(Numero numero) {
        ContentValues valores = new ContentValues();
        valores.put(Numeros.NUMERO, numero.getNumero());
        valores.put(Numeros.CLIENTE, numero.getDueño());
        valores.put(Numeros.PAGO, numero.getPago());

        String whereClause = String.format("%s=?", Rifas.ID);
        String[] whereArgs = {numero.getID()};

        int resultado = db.update(Tablas.NUMERO, valores, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarNumero(String idNumero) {
        String whereClause = String.format("%s=?", Rifas.ID);
        String[] whereArgs = {idNumero};

        int resultado = db.delete(Tablas.NUMERO, whereClause, whereArgs);

        return resultado > 0;
    }

    // Obtener los numeros de una rifa especifica
    public Cursor obtenerUsuario() {
        String sql1="SELECT * FROM " + Tablas.USUARIO + " WHERE " + Usuario.ID + " LIKE '%" + "USER-1234" + "%'";
        return db.rawQuery(sql1,null);
    }

    public boolean actualizarUsuario(String Name, String Mail, String Pass) {
        ContentValues valores = new ContentValues();
        valores.put(Usuario.NOMBRE, Name);
        valores.put(Usuario.CORREO, Mail);
        valores.put(Usuario.PASS, Pass);

        String whereClause = String.format("%s=?", Usuario.ID);
        String[] whereArgs = {"USER-1234"};

        int resultado = db.update(Tablas.USUARIO, valores, whereClause, whereArgs);

        return resultado > 0;
    }
}