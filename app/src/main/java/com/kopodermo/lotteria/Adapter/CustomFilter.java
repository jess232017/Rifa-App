package com.kopodermo.lotteria.Adapter;

import android.annotation.SuppressLint;
import android.widget.Filter;

import com.kopodermo.lotteria.Model.Numero;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    private Number_Adapter adapter;
    private ArrayList<Numero> filterList;

    CustomFilter(ArrayList<Numero> filterList, Number_Adapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;
    }

    //FILTERING OCURS
    @SuppressLint("DefaultLocale")
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Numero> filteredPlayers=new ArrayList<>();
            for (int i=0;i<filterList.size();i++)
            {
                String format = "%1$02d";
                format = String.format(format, filterList.get(i).getNumero());

                if(format.contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.numeros = (ArrayList<Numero>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
