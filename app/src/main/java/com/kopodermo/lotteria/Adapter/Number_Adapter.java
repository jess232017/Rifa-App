package com.kopodermo.lotteria.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.kopodermo.lotteria.Model.Numero;
import com.kopodermo.lotteria.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Number_Adapter extends RecyclerView.Adapter<Number_Adapter.ContactoViewHolder> implements SectionTitleProvider, Filterable {

    //Declaracion de Variables
    private Activity activity;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private OnNumberListener mOnNumberListener;
    private ArrayList<Numero> filterList;
    ArrayList<Numero> numeros;
    private CustomFilter filter;
    private int resource;
    //---------------------------

    public Number_Adapter(ArrayList<Numero> numeros, Activity activity, int resource,OnNumberListener mOnNumberListener){
        this.numeros = numeros;
        this.filterList = numeros;
        this.resource    = resource;
        this.mOnNumberListener = mOnNumberListener;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ContactoViewHolder(view,mOnNumberListener);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Numero numero = numeros.get(position);

        // poner el formato de dos digitos al numero
        String format = "%1$02d";
        format = String.format(format, numero.getNumero());

        //Genera una imagen con los dos digitos y un color ramdon

        if(numero.getPago().equals("Pendiente")){
            TextDrawable drawable = TextDrawable.builder().buildRound(format, Color.RED);
            holder.txtNum.setImageDrawable(drawable);
        }else if(numero.getPago().equals("Pagado")){
            TextDrawable drawable = TextDrawable.builder().buildRound(format, Color.GREEN);
            holder.txtNum.setImageDrawable(drawable);
        }else{
            TextDrawable drawable = TextDrawable.builder().buildRound(format, Color.GRAY);
            holder.txtNum.setImageDrawable(drawable);
        }

        if(numero.getDueño().isEmpty()){
            holder.txtCliente.setVisibility(View.GONE);
            holder.txtpago.setVisibility(View.GONE);
        }else{
            holder.txtCliente.setVisibility(View.VISIBLE);
            holder.txtpago.setVisibility(View.VISIBLE);
            holder.txtCliente.setText(numero.getDueño());
            holder.txtpago.setText(numero.getPago());
        }
    }

    @Override
    public int getItemCount() {
        return numeros.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
            filter=new CustomFilter(filterList,this);

        return filter;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String getSectionTitle(int position) {
        //this String will be shown in a bubble for specified position
        String format = "%1$02d";
        return String.format(format, numeros.get(position).getNumero()).substring(0,1);
    }

    class ContactoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private OnNumberListener mOnNumberListener;
        private ImageView txtNum;
        private TextView txtCliente;
        private TextView txtpago;


        ContactoViewHolder(View itemView, OnNumberListener onNumberListener) {
            super(itemView);
            txtNum = itemView.findViewById(R.id.txtNum);
            txtCliente = itemView.findViewById(R.id.txtCliente);
            txtpago = itemView.findViewById(R.id.txtpago);
            this.mOnNumberListener = onNumberListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            int pos = numeros.get(getAdapterPosition()).getNumero();
            mOnNumberListener.onNumberClick(pos);
        }
    }

    public interface OnNumberListener {
        void onNumberClick(int pos);
    }
}
