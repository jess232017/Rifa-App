package com.kopodermo.lotteria;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kopodermo.lotteria.Sqlite.BDAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private String Mail, Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BDAdapter db = new BDAdapter(this);
        db.openDB();
        try
        {
            Cursor c = db.obtenerUsuario();
            if(c.moveToNext()){
                Mail = c.getString(3);
                Pass = c.getString(4);
            }
            db.closeDB();

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public void Number(View view) {
        TextInputEditText txtUser = findViewById(R.id.Phone);
        TextInputEditText txtPass = findViewById(R.id.password);
        String strUsuario = Objects.requireNonNull(txtUser.getText()).toString().trim();
        String strPassword = Objects.requireNonNull(txtPass.getText()).toString().trim();
        try
        {
            if (strUsuario.isEmpty() || strPassword.isEmpty()) {
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            }
            else if(strUsuario.equals(Mail) && strPassword.equals(Pass)){
                Intent intent = new Intent(this, Rifa_Activity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }
}
