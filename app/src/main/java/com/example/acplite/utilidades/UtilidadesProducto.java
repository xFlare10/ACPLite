package com.example.acplite.utilidades;

public class UtilidadesProducto {
    public static final String DB_NAME = "ACPLiteDB";
    public static final String PRODUCTS_TABLE_NAME = "Productos";
    public static final String CAMPO_ID = "productoID";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_PRECIO = "precio";
    public static final String CAMPO_IMAGEN = "imagen";

    public static final String CREATE_TABLE_PRODUCT = "CREATE TABLE " + PRODUCTS_TABLE_NAME + " ( " +
            CAMPO_ID + " INTEGER PRIMARY KEY, " +
            CAMPO_NOMBRE + " TEXT, " +
            CAMPO_PRECIO + " TEXT, " +
            CAMPO_IMAGEN + " BLOB)";

    public static final String UPDATE_TABLE_PRODUCT = "DROP TABLE IF EXISTS " + PRODUCTS_TABLE_NAME;
}
