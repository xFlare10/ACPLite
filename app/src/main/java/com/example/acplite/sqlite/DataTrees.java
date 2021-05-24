package com.example.acplite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.acplite.entidades.Arbol;
import com.example.acplite.utilidades.UtilidadesArbol;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

public class DataTrees {
    private Context context;
    private ConexionSQLiteHelper conn;
    private ByteArrayOutputStream outputStream;
    private byte[] imageBytes;

//    public void storeImage(@NotNull Arbol obj){
//        conn = new ConexionSQLiteHelper(context, UtilidadesArbol.DB_NAME, null, 1);
//        SQLiteDatabase db = conn.getWritableDatabase();
//        Bitmap imageToStore = obj.getTreeImg();
//
//        outputStream = new ByteArrayOutputStream();
//        imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        imageBytes = outputStream.toByteArray();
//        ContentValues values = new ContentValues();
//
//        values.put(UtilidadesArbol.CAMPO_ID, obj.getTreeID());
//        values.put(UtilidadesArbol.CAMPO_NOMBRE, obj.getTreeName());
//        values.put(UtilidadesArbol.CAMPO_NOMBRE_CIENTIFICO, obj.getTreeScientificName());
//        values.put(UtilidadesArbol.CAMPO_DESCRIPCION, obj.getTreeDescription());
//        values.put(UtilidadesArbol.CAMPO_IMAGEN, imageBytes);
//
//        long cantidad = db.insert(UtilidadesArbol.TREES_TABLE_NAME, null, values);
//
//        if( cantidad != 0 ){
//            Toast.makeText(context, "Image added successfully", Toast.LENGTH_SHORT).show();
//            db.close();
//        } else {
//            Toast.makeText(context, "ERROR: Image not added", Toast.LENGTH_SHORT).show();
//        }
//    }
}
