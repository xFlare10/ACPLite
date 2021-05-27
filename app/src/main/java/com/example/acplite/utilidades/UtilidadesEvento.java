package com.example.acplite.utilidades;

public class UtilidadesEvento {
    public static final String DB_NAME = "ACPLiteDB";
    public static final String EVENTS_TABLE_NAME = "Eventos";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_FECHA = "fecha";

    public static final String CREATE_TABLE_EVENT = "CREATE TABLE " + EVENTS_TABLE_NAME + " ( " +
            CAMPO_NOMBRE + " TEXT, " +
            CAMPO_FECHA + " TEXT)";

    public static final String UPDATE_TABLE_EVENT = "DROP TABLE IF EXISTS " + EVENTS_TABLE_NAME;
}
