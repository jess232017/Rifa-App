package com.kopodermo.lotteria.Model;

public class Numero extends Number {
    private String ID;
    private String ID_Rifa;
    private int numero;
    private String dueño;
    private String pago;


    public Numero(String id_rifa, int numero, String dueño, String pago) {
        this.ID_Rifa = id_rifa;
        this.numero = numero;
        this.dueño = dueño;
        this.pago = pago;
    }

    public Numero(int numero, String dueño, String pago) {
        this.numero = numero;
        this.dueño = dueño;
        this.pago = pago;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_Rifa() {
        return ID_Rifa;
    }

    public void setID_Rifa(String ID_Rifa) {
        this.ID_Rifa = ID_Rifa;
    }
}
