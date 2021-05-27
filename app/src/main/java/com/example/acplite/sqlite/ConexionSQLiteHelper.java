package com.example.acplite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.acplite.entidades.Arbol;
import com.example.acplite.entidades.Evento;
import com.example.acplite.entidades.Productos;
import com.example.acplite.utilidades.UtilidadesArbol;
import com.example.acplite.utilidades.UtilidadesEvento;
import com.example.acplite.utilidades.UtilidadesProducto;
import com.example.acplite.utilidades.UtilidadesPublicacion;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private ByteArrayOutputStream outputStream;
    private byte[] imageBytes;

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
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

    // ================= METODOS CRUD DEL ARBOL ================= //

    // METODO PARA GUARDAR UN ARBOL
    public void storeTree(Arbol obj){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageToStore = obj.getTreeImg();

        outputStream = new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        imageBytes = outputStream.toByteArray();
        ContentValues values = new ContentValues();

        values.put(UtilidadesArbol.CAMPO_NOMBRE, obj.getTreeName());
        values.put(UtilidadesArbol.CAMPO_NOMBRE_CIENTIFICO, obj.getTreeScientificName());
        values.put(UtilidadesArbol.CAMPO_DESCRIPCION, obj.getTreeDescription());
        values.put(UtilidadesArbol.CAMPO_IMAGEN, imageBytes);

        long cantidad = db.insert(UtilidadesArbol.TREES_TABLE_NAME, null, values);

        if( cantidad != 0 ){
            Toast.makeText(context, "Image added successfully", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(context, "ERROR: Image not added", Toast.LENGTH_SHORT).show();
        }
    }

    // METODO PARA VALIDAR SI EXISTE UN ARBOL
    public Cursor validateTree(String treeName) throws SQLException {
        Cursor cursor = null;
        cursor = this.getReadableDatabase().query(
                UtilidadesArbol.TREES_TABLE_NAME,
                new String[]{UtilidadesArbol.CAMPO_NOMBRE, UtilidadesArbol.CAMPO_NOMBRE_CIENTIFICO,
                        UtilidadesArbol.CAMPO_DESCRIPCION, UtilidadesArbol.CAMPO_IMAGEN},
                UtilidadesArbol.CAMPO_NOMBRE + " LIKE '" + treeName + "' ",
                null, null, null, null);

        return cursor;
    }

    // ================= METODOS CRUD DEL PRODUCTO ================= //

    // METODO PARA GUARDAR UN PRODUCTO
    public void storeProducto(Productos obj){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageToStore = obj.getProductImg();

        outputStream = new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        imageBytes = outputStream.toByteArray();
        ContentValues values = new ContentValues();

        values.put(UtilidadesProducto.CAMPO_ID, obj.getProductID());
        values.put(UtilidadesProducto.CAMPO_NOMBRE, obj.getProductName());
        values.put(UtilidadesProducto.CAMPO_PRECIO, obj.getProductPrice());
        values.put(UtilidadesProducto.CAMPO_IMAGEN, imageBytes);

        long cantidad = db.insert(UtilidadesProducto.PRODUCTS_TABLE_NAME, null, values);

        if( cantidad != 0 ){
            Toast.makeText(context, "Image added successfully", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(context, "ERROR: Image not added", Toast.LENGTH_SHORT).show();
        }
    }

    // METODO PARA VALIDAR SI EXISTE UN PRODUCTO
    public Cursor validateProduct(String id) throws SQLException {
        Cursor cursor = null;
        cursor = this.getReadableDatabase().query(
                UtilidadesProducto.PRODUCTS_TABLE_NAME,
                new String[]{UtilidadesProducto.CAMPO_ID, UtilidadesProducto.CAMPO_NOMBRE,
                        UtilidadesProducto.CAMPO_PRECIO, UtilidadesProducto.CAMPO_IMAGEN},
                UtilidadesProducto.CAMPO_ID + " LIKE '" + id + "' ",
                null, null, null, null);

        return cursor;
    }

    // ================= METODOS CRUD DEL EVENTO ================= //

    // METODO PARA VALIDAR UN EVENTO
    public Cursor validateEvent(String name) {
        Cursor cursor = null;
        cursor = this.getReadableDatabase().query(
                UtilidadesEvento.EVENTS_TABLE_NAME,
                new String[]{UtilidadesEvento.CAMPO_NOMBRE, UtilidadesEvento.CAMPO_FECHA},
                UtilidadesEvento.CAMPO_NOMBRE + " LIKE '" + name + "' ",
                null, null, null, null);

        return cursor;
    }

    //METODO PARA GUARDAR UN EVENTO
    public void storeEvent(Evento obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UtilidadesEvento.CAMPO_NOMBRE, obj.getEventName());
        values.put(UtilidadesEvento.CAMPO_FECHA, obj.getEventDate());

        long cantidad = db.insert(UtilidadesEvento.EVENTS_TABLE_NAME, null, values);

        if( cantidad != 0 ){
            Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(context, "ERROR: No se guardo el registro", Toast.LENGTH_SHORT).show();
        }
    }
}
