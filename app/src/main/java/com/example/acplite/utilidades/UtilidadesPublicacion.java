package com.example.acplite.utilidades;

public class UtilidadesPublicacion {
    public static final String DB_NAME = "ACPLiteDB";
    public static final String POSTS_TABLE_NAME = "Publicaciones";
    public static final String CAMPO_ID = "publicacionesID";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_DESCRIPCION = "descripcion";

    public static final String CREATE_TABLE_POST = "CREATE TABLE " + POSTS_TABLE_NAME + " ( " +
            CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CAMPO_NOMBRE + " TEXT, " +
            CAMPO_DESCRIPCION + " TEXT)";

    public static final String UPDATE_TABLE_POST = "DROP TABLE IF EXISTS " + POSTS_TABLE_NAME;
}
