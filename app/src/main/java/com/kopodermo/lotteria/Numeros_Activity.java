package com.kopodermo.lotteria;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.kopodermo.lotteria.Adapter.Number_Adapter;
import com.kopodermo.lotteria.Model.Numero;
import com.kopodermo.lotteria.Sqlite.BDAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class Numeros_Activity extends AppCompatActivity implements Number_Adapter.OnNumberListener{
    ArrayList<Numero> numeros = new ArrayList<>();
    Number_Adapter NumberAdapter;
    RecyclerView NumberRecycler;
    FastScroller fastScroller;
    private String ID_Rifa;
    String pago = "";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeros);

        ID_Rifa = getIntent().getStringExtra("ID");
        String Title = getIntent().getStringExtra("Title");
        //String Fecha = getIntent().getStringExtra("Fecha");
        String Premio = getIntent().getStringExtra("Premio");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView titulo = findViewById(R.id.titulo);
        TextView subtext = findViewById(R.id.subtext);
        titulo.setText("Rifa de: "+ Title);
        subtext.setText("Premio: "+ Premio);

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSupportNavigateUp();
            }
        });

        GridLayoutManager lmGrid = new GridLayoutManager(this,2);
        NumberRecycler = findViewById(R.id.NumberRecycler);
        fastScroller = findViewById(R.id.fastscroll);
        NumberRecycler.setLayoutManager(lmGrid);
        NumberRecycler.setHasFixedSize(true);
        Refrescar();
    }

    private void Refrescar(){
        NumberAdapter = new Number_Adapter(CargarNumeros(), this, R.layout.tarjeta, this);
        NumberRecycler.setAdapter(NumberAdapter);
        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(NumberRecycler);
    }

    //para proposito de prueba
    private ArrayList<Numero> CargarNumeros() {
        numeros.clear();
        BDAdapter db = new BDAdapter(this);
        db.openDB();
        try
        {
            Numero p;
            Cursor c = db.obtenerNumeroWhere(ID_Rifa);
            while (c.moveToNext()) {
                p = new Numero(c.getString(2),c.getInt(3),
                        c.getString(4),c.getString(5));
                p.setID(c.getString(1));
                numeros.add(p);
            }
            db.closeDB();
            return numeros;

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            return numeros;
        }
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void CustomDialog(final int pos){
        final Numero numero = numeros.get(pos);
        final Dialog dialog = new Dialog(this);
        final BDAdapter BD = new BDAdapter(this);
        BD.openDB();

        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.custom_num);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;

        String format = "%1$02d"; // dos digitos
        ((TextView) dialog.findViewById(R.id.Num)).setText(String.format(format, pos));
        final TextView Persona  = dialog.findViewById(R.id.Persona);
        final Spinner spnType         = dialog.findViewById(R.id.spnType);
        if(numero.getPago().equals("Pendiente")){
            spnType.setSelection(1);
        }else if(numero.getPago().equals("Pagado")){
            spnType.setSelection(2);
        }else{
            spnType.setSelection(0);
        }
        Persona.setText(numero.getDueño());


        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                pago = spnType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        dialog.findViewById(R.id.Aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtPersona = Objects.requireNonNull(Persona.getText()).toString().trim();
                if(txtPersona.isEmpty() || pago.equals("Vacio")){
                    Toast.makeText(Numeros_Activity.this, "Rellene los campos", Toast.LENGTH_LONG).show();
                    return;
                }
                numero.setDueño(txtPersona);
                numero.setPago(pago);
                BD.actualizarNumero(numero);
                Refrescar();
                BD.closeDB();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.Cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onNumberClick(int pos) {
        CustomDialog(pos);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Toast.makeText(this, "que pex", Toast.LENGTH_LONG).show();
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                NumberAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        return true;
    }
}
