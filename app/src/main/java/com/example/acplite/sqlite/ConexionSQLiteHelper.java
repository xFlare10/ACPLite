package com.example.acplite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.acplite.entidades.Arbol;
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

    //TREES METHODS
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
}
