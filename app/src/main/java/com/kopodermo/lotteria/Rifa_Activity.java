package com.kopodermo.lotteria;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kopodermo.lotteria.Adapter.Rifa_Adapter;
import com.kopodermo.lotteria.Model.Numero;
import com.kopodermo.lotteria.Model.Rifa;
import com.kopodermo.lotteria.Sqlite.BDAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Rifa_Activity extends AppCompatActivity implements Rifa_Adapter.OnRifaListener{
    private final Calendar cldr = Calendar.getInstance();
    private int day = cldr.get(Calendar.DAY_OF_MONTH);
    private int month = cldr.get(Calendar.MONTH);
    private int year = cldr.get(Calendar.YEAR);
    public String txtFecha = "";
    private DatePickerDialog picker;

    ArrayList<Rifa> rifas = new ArrayList<>();
    RecyclerView RifaRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rifa);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Rifas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        RifaRecycler = findViewById(R.id.RifaRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RifaRecycler.setLayoutManager(linearLayoutManager);
        Refrescar();

        FloatingActionButton fab = findViewById(R.id.fabPlus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarDialog(null);
            }
        });
    }

    private void Refrescar(){
        Rifa_Adapter rifa_adapter = new Rifa_Adapter(CargarRifas(), R.layout.cardview_rifa, this,this);
        RifaRecycler.setAdapter(rifa_adapter);
    }

    private ArrayList<Rifa> CargarRifas() {
        rifas.clear();
        BDAdapter db = new BDAdapter(this);
        db.openDB();
        try
        {
            Rifa p;
            Cursor c = db.obtenerRifas();
            while (c.moveToNext()) {
                p= new Rifa(c.getString(1),c.getString(2),c.getString(3),
                        c.getString(4),c.getString(5));
                rifas.add(p);
            }
            db.closeDB();
            return rifas;

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            return rifas;
        }
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void MostrarDialog(final Rifa rifa){
        final Dialog dialog = new Dialog(this);
        final BDAdapter BD = new BDAdapter(this);
        BD.openDB();

        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.custom_rifa);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;

        final TextView Num = dialog.findViewById(R.id.Num);
        final TextView Titulo = dialog.findViewById(R.id.Titulo);
        final TextView Premio = dialog.findViewById(R.id.Premio);
        final TextView Fecha  = dialog.findViewById(R.id.Fecha);
        final TextView Valor = dialog.findViewById(R.id.Valor);

        if(rifa!=null){
            Num.setText("Editar Rifa");
            Titulo.setText(rifa.getTitulo());
            Premio.setText(rifa.getPremio());
            Fecha.setText(rifa.getFecha());
            Valor.setText(rifa.getValor());
        }

        Fecha.setInputType(InputType.TYPE_NULL);
        Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Rifa_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                                txtFecha = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                Fecha.setText(txtFecha);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });



        dialog.findViewById(R.id.Aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtTitulo = Objects.requireNonNull(Titulo.getText()).toString().trim();
                String txtPremio = Objects.requireNonNull(Premio.getText()).toString().trim();
                String txtValor = Objects.requireNonNull(Valor.getText()).toString().trim();

                if(txtTitulo.isEmpty() || txtPremio.isEmpty() || txtFecha.isEmpty() || txtValor.isEmpty()){
                    Toast.makeText(Rifa_Activity.this,"Ingrese datos validos",Toast.LENGTH_LONG).show();
                    return;
                }
                if(rifa==null){

                    String IDRifa = BD.insertarRifa(new Rifa(txtTitulo,txtPremio,txtFecha,txtValor));

                    for(int i = 0; i < 100; i++  ){
                        BD.insertarNumero(new Numero(IDRifa,i,"",""));
                    }
                }else{
                    rifa.setTitulo(txtTitulo);
                    rifa.setPremio(txtPremio);
                    rifa.setFecha(txtFecha);
                    rifa.setValor(txtValor);
                    BD.actualizarRifa(rifa);
                }

                BD.closeDB();
                Refrescar();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.Cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BD.closeDB();
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

     private void MostrarBottomDiaog(final int pos, final Rifa rifa){
        final BottomSheetDialog dialog = new BottomSheetDialog(Rifa_Activity.this);
        dialog.setContentView(R.layout.bottom_sheet);
        dialog.setCanceledOnTouchOutside(false);

        LinearLayout ver =  dialog.findViewById(R.id.ver);
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rifa_Activity.this, Numeros_Activity.class);
                intent.putExtra("ID", rifa.getID());
                intent.putExtra("Title", rifa.getTitulo());
                intent.putExtra("Fecha", rifa.getFecha());
                intent.putExtra("Valor", rifa.getValor());
                intent.putExtra("Premio", rifa.getPremio());
                startActivity(intent);

                dialog.dismiss();
            }
        });

         LinearLayout editar =  dialog.findViewById(R.id.editar);
         editar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MostrarDialog(rifa);
                 dialog.dismiss();
             }
         });

         LinearLayout borrar =  dialog.findViewById(R.id.borrar);
         borrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final BDAdapter BD = new BDAdapter(Rifa_Activity.this);
                 BD.openDB();
                 BD.eliminarRifa(rifa.getID());
                 BD.closeDB();
                 Refrescar();

                 dialog.dismiss();
             }
         });

         LinearLayout estadistica =  dialog.findViewById(R.id.estadistica);
         estadistica.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Rifa_Activity.this, Estadistica_Activity.class);
                 intent.putExtra("ID", rifa.getID());
                 intent.putExtra("Title", rifa.getTitulo());
                 intent.putExtra("Fecha", rifa.getFecha());
                 intent.putExtra("Valor", rifa.getValor());
                 intent.putExtra("Premio", rifa.getPremio());
                 startActivity(intent);

                 dialog.dismiss();
             }
         });


        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.setuo, menu);

        MenuItem AddMenu = menu.findItem(R.id.AddMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.AddMenu) {
            Intent intent = new Intent(this, Profile_Activity.class);
            startActivity(intent);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);  //exit application
    }

    @Override
    public void onRifaClick(int pos, Rifa rifa) {
        MostrarBottomDiaog(pos, rifa);
    }
}
