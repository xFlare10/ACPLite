package com.example.acplite.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.acplite.utilidades.UtilidadesArbol;
import com.example.acplite.utilidades.UtilidadesEvento;
import com.example.acplite.utilidades.UtilidadesProducto;
import com.example.acplite.utilidades.UtilidadesPublicacion;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UtilidadesArbol.CREATE_TABLE_TREE);
        db.execSQL(UtilidadesProducto.CREATE_TABLE_PRODUCT);
        db.execSQL(UtilidadesEvento.CREATE_TABLE_EVENT);
        db.execSQL(UtilidadesPublicacion.CREATE_TABLE_POST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UtilidadesArbol.UPDATE_TABLE_TREE);
        db.execSQL(UtilidadesProducto.UPDATE_TABLE_PRODUCT);
        db.execSQL(UtilidadesEvento.UPDATE_TABLE_EVENT);
        db.execSQL(UtilidadesPublicacion.UPDATE_TABLE_POST);
        onCreate(db);
    }
}
