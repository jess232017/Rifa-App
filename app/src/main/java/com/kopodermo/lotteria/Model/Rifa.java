package com.kopodermo.lotteria.Model;

public class Rifa {
    private String ID;
    private String Titulo;
    private String Premio;
    private String Fecha;
    private String Valor;

    public Rifa( String titulo, String premio, String fecha, String valor) {
        ID = null;
        Titulo = titulo;
        Premio = premio;
        Fecha = fecha;
        Valor = valor;
    }

    public Rifa(String id, String titulo, String premio, String fecha, String valor) {
        ID = id;
        Titulo = titulo;
        Premio = premio;
        Fecha = fecha;
        Valor = valor;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getPremio() {
        return Premio;
    }

    public void setPremio(String premio) {
        Premio = premio;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }
}
