package com.kopodermo.lotteria.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kopodermo.lotteria.Model.Rifa;
import com.kopodermo.lotteria.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Rifa_Adapter extends RecyclerView.Adapter<Rifa_Adapter.ContactoViewHolder>{

    //Declaracion de Variables
    private OnRifaListener mOnRidaListener;
    private ArrayList<Rifa> rifas;
    private int resource;
    private Activity activity;
    //---------------------------

    public Rifa_Adapter(ArrayList<Rifa> rifas, int resource, Activity activity, OnRifaListener mOnRidaListener){
        this.rifas = rifas;
        this.resource = resource;
        this.activity=activity;
        this.mOnRidaListener = mOnRidaListener;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ContactoViewHolder(view,mOnRidaListener);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Rifa rifa = rifas.get(position);
        holder.tvTitle.setText(rifa.getTitulo());
        holder.tvPremio.setText(rifa.getPremio());
        holder.tvValor.setText("C$ " + rifa.getValor());
        holder.tvFecha.setText(rifa.getFecha());
    }

    @Override
    public int getItemCount() {
        return rifas.size();
    }

    class ContactoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private OnRifaListener mOnRifaListener;
        private ImageView img_row;
        private TextView tvTitle;
        private TextView tvPremio;
        private TextView tvValor;
        private TextView tvFecha;
        private ImageView img_row_delete;
        private ImageView img_row_add;

        ContactoViewHolder(View itemView, OnRifaListener onRifaListener) {
            super(itemView);
            img_row = itemView.findViewById(R.id.img_row);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPremio = itemView.findViewById(R.id.tvPremio);
            tvValor = itemView.findViewById(R.id.tvValor);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            img_row_add = itemView.findViewById(R.id.img_row_add);
            img_row_delete = itemView.findViewById(R.id.img_row_delete);
            this.mOnRifaListener = onRifaListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnRifaListener.onRifaClick(getAdapterPosition(), rifas.get(getAdapterPosition()));
        }
    }

    public interface OnRifaListener {
        void onRifaClick(int pos, Rifa rifa);
    }
}
