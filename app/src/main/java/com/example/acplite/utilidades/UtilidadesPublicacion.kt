package com.example.acplite.utilidades

object UtilidadesPublicacion {
    const val DB_NAME = "ACPLiteDB"
    const val POSTS_TABLE_NAME = "Publicaciones"
    const val CAMPO_NOMBRE = "nombre"
    const val CREATE_TABLE_POST = "CREATE TABLE " + POSTS_TABLE_NAME + " ( " +
            CAMPO_NOMBRE + " TEXT)"
    const val UPDATE_TABLE_POST = "DROP TABLE IF EXISTS " + POSTS_TABLE_NAME
}