package com.example.acplite.utilidades

object UtilidadesArbol {
    const val DB_NAME = "ACPLiteDB"
    const val TREES_TABLE_NAME = "Arboles"
    const val CAMPO_NOMBRE = "nombre"
    const val CAMPO_NOMBRE_CIENTIFICO = "nombreCientifico"
    const val CAMPO_DESCRIPCION = "descripcion"
    const val CAMPO_IMAGEN = "imagen"
    const val CREATE_TABLE_TREE = "CREATE TABLE " + TREES_TABLE_NAME + " ( " +
            CAMPO_NOMBRE + " TEXT, " +
            CAMPO_NOMBRE_CIENTIFICO + " TEXT, " +
            CAMPO_DESCRIPCION + " TEXT, " +
            CAMPO_IMAGEN + " BLOB)"
    const val UPDATE_TABLE_TREE = "DROP TABLE IF EXISTS " + TREES_TABLE_NAME
}