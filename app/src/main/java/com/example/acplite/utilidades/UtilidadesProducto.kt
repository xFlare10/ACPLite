package com.example.acplite.utilidades

object UtilidadesProducto {
    const val DB_NAME = "ACPLiteDB"
    const val PRODUCTS_TABLE_NAME = "Productos"
    const val CAMPO_ID = "productoID"
    const val CAMPO_NOMBRE = "nombre"
    const val CAMPO_PRECIO = "precio"
    const val CAMPO_IMAGEN = "imagen"
    const val CREATE_TABLE_PRODUCT = "CREATE TABLE " + PRODUCTS_TABLE_NAME + " ( " +
            CAMPO_ID + " INTEGER PRIMARY KEY, " +
            CAMPO_NOMBRE + " TEXT, " +
            CAMPO_PRECIO + " TEXT, " +
            CAMPO_IMAGEN + " BLOB)"
    const val UPDATE_TABLE_PRODUCT = "DROP TABLE IF EXISTS " + PRODUCTS_TABLE_NAME
}