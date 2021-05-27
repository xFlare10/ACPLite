package com.example.acplite.utilidades

object UtilidadesEvento {
    const val DB_NAME = "ACPLiteDB"
    const val EVENTS_TABLE_NAME = "Eventos"
    const val CAMPO_NOMBRE = "nombre"
    const val CAMPO_FECHA = "fecha"
    const val CREATE_TABLE_EVENT = "CREATE TABLE " + EVENTS_TABLE_NAME + " ( " +
            CAMPO_NOMBRE + " TEXT, " +
            CAMPO_FECHA + " TEXT)"
    const val UPDATE_TABLE_EVENT = "DROP TABLE IF EXISTS " + EVENTS_TABLE_NAME
}