package com.kopodermo.lotteria.Sqlite;

import java.util.UUID;

public class Constants {

    interface ColumnasUsuario {
        String ID = "id";
        String NOMBRE = "nombre";
        String CORREO = "correo";
        String PASS = "pass";
    }

    interface ColumnasRifa {
        String ID = "id";
        String TITULO = "titulo";
        String PREMIO = "premio";
        String FECHA = "fecha";
        String VALOR = "Valor";
    }

    interface ColumnasNumero {
        String ID = "id";
        String ID_RIFA = "id_rifa";
        String NUMERO = "numero";
        String CLIENTE = "cliente";
        String PAGO = "pago";
    }

    public static class Rifas implements ColumnasRifa{
        public static String generarIdRifa() {
            return "RIF-" + UUID.randomUUID().toString();
        }
    }

    public static class Numeros implements ColumnasNumero{
        public static String generarIdNumero() {
            return "NUM-" + UUID.randomUUID().toString();
        }
    }

    public static class Usuario implements ColumnasUsuario{
        public static String generarIdNumero() {
            return "USER-1234";
        }
    }

    private Constants() {
    }
}