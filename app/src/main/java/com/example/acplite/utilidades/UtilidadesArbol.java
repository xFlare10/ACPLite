package com.example.acplite.utilidades;

public class UtilidadesArbol {
    public static final String DB_NAME = "ACPLiteDB";
    public static final String TREES_TABLE_NAME = "Arboles";
    public static final String CAMPO_ID = "arbolID";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_NOMBRE_CIENTIFICO = "nombreCientifico";
    public static final String CAMPO_IMAGEN = "imagen";

    public static final String CREATE_TABLE_TREE = "CREATE TABLE " + TREES_TABLE_NAME + " ( " +
            CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CAMPO_NOMBRE + " TEXT, " +
            CAMPO_NOMBRE_CIENTIFICO + " TEXT, " +
            CAMPO_IMAGEN + " BLOB)";


    public static final String UPDATE_TABLE_TREE = "DROP TABLE IF EXISTS " + TREES_TABLE_NAME;
}
