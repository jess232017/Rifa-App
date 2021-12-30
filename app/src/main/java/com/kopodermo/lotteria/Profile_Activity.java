package com.kopodermo.lotteria;

import android.database.Cursor;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kopodermo.lotteria.Sqlite.BDAdapter;

import java.util.Objects;

public class Profile_Activity extends AppCompatActivity {
    private TextInputEditText txtName, txtUser, txtPass;
    private String Name, Mail, Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BDAdapter db = new BDAdapter(this);
        db.openDB();
        try
        {
            Cursor c = db.obtenerUsuario();
            if(c.moveToNext()){
                Name = c.getString(2);
                Mail = c.getString(3);
                Pass = c.getString(4);
            }
            db.closeDB();

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

        txtName = findViewById(R.id.Name);
        txtUser = findViewById(R.id.User);
        txtPass = findViewById(R.id.password);
        txtName.setText(Name);
        txtUser.setText(Mail);
        txtPass.setText(Pass);
    }

    public void Guardar(View view) {
        String strUser = Objects.requireNonNull(txtName.getText()).toString().trim();
        String strMail = Objects.requireNonNull(txtUser.getText()).toString().trim();
        String strPassword = Objects.requireNonNull(txtPass.getText()).toString().trim();
        try
        {
            if (strUser.isEmpty() || strMail.isEmpty() || strPassword.isEmpty()) {
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            }
            else{
                BDAdapter db = new BDAdapter(this);
                db.openDB();
                db.actualizarUsuario(strMail,strUser,strPassword);
                db.closeDB();
                Toast.makeText(this, "Los datos han sido actualizados", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
