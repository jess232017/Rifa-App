package com.kopodermo.lotteria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kopodermo.lotteria.Sqlite.BDAdapter;

import java.util.Objects;

@SuppressLint("Registered")
public class Estadistica_Activity extends AppCompatActivity {
    private String ID_Rifa,title,valor,fecha,premio;
    private int Recaudado = 0, Pendiente  = 0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ESTADISTICAS");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ID_Rifa = getIntent().getStringExtra("ID");
        title = getIntent().getStringExtra("Title");
        valor = getIntent().getStringExtra("Valor");
        fecha = getIntent().getStringExtra("Fecha");
        premio = getIntent().getStringExtra("Premio");

        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvValor = findViewById(R.id.tvValor);
        TextView tvPremio = findViewById(R.id.tvPremio);
        TextView tvFecha = findViewById(R.id.tvFecha);

        TextView tvTotal = findViewById(R.id.tvTotal);
        TextView tvRecaudado = findViewById(R.id.tvRecaudado);
        TextView tvPendiente = findViewById(R.id.tvPendiente);

        TextView tvNumero = findViewById(R.id.tvNumero);
        TextView tvNumero2 = findViewById(R.id.tvNumero2);
        TextView tvNumero3 = findViewById(R.id.tvNumero3);

        tvTitle.setText(title);
        tvValor.setText(valor);
        tvPremio.setText(premio);
        tvFecha.setText(fecha);

        BDAdapter db = new BDAdapter(this);
        db.openDB();
        Cursor c = db.obtenerNumeroWhere(ID_Rifa);

        while (c.moveToNext()) {
            if(c.getString(5).equals("Pagado"))
                Recaudado ++;
            else if(c.getString(5).equals("Pendiente"))
                Pendiente ++;
        }
        db.closeDB();

        float Tot_Recaudado = Float.valueOf(valor) * Recaudado;
        float Tot_Pendiente = Float.valueOf(valor) * Pendiente;

        tvTotal.setText("C$: " + (Tot_Recaudado + Tot_Pendiente));
        tvRecaudado.setText("C$: " + Tot_Recaudado);
        tvPendiente.setText("C$: " + Tot_Pendiente);

        tvNumero.setText((Recaudado + Pendiente) + "/100 Numeros");
        tvNumero2.setText(Recaudado + "/100 Numeros");
        tvNumero3.setText(Pendiente + "/100 Numeros");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void Number(View view) {
        Intent intent = new Intent(Estadistica_Activity.this, Numeros_Activity.class);
        intent.putExtra("ID", ID_Rifa);
        intent.putExtra("Title", title);
        intent.putExtra("Fecha", valor);
        intent.putExtra("Valor", fecha);
        intent.putExtra("Premio", premio);
        startActivity(intent);
    }
}
