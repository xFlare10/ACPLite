package com.example.acplite.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.widget.Toast
import com.example.acplite.entidades.Arbol
import com.example.acplite.entidades.Evento
import com.example.acplite.entidades.Productos
import com.example.acplite.entidades.Publicacion
import com.example.acplite.utilidades.UtilidadesArbol
import com.example.acplite.utilidades.UtilidadesEvento
import com.example.acplite.utilidades.UtilidadesProducto
import com.example.acplite.utilidades.UtilidadesPublicacion
import java.io.ByteArrayOutputStream

class ConexionSQLiteHelper(
    private val context: Context?,
    name: String?,
    factory: CursorFactory?,
    version: Int
) : SQLiteOpenHelper(
    context, name, factory, version
) {
    private var outputStream: ByteArrayOutputStream? = null
    private var imageBytes: ByteArray
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(UtilidadesArbol.CREATE_TABLE_TREE)
        db.execSQL(UtilidadesProducto.CREATE_TABLE_PRODUCT)
        db.execSQL(UtilidadesEvento.CREATE_TABLE_EVENT)
        db.execSQL(UtilidadesPublicacion.CREATE_TABLE_POST)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(UtilidadesArbol.UPDATE_TABLE_TREE)
        db.execSQL(UtilidadesProducto.UPDATE_TABLE_PRODUCT)
        db.execSQL(UtilidadesEvento.UPDATE_TABLE_EVENT)
        db.execSQL(UtilidadesPublicacion.UPDATE_TABLE_POST)
        onCreate(db)
    }

    // ================= METODOS CRUD DEL ARBOL ================= //
    // METODO PARA GUARDAR UN ARBOL
    fun storeTree(obj: Arbol) {
        val db = this.writableDatabase
        val imageToStore = obj.treeImg
        outputStream = ByteArrayOutputStream()
        imageToStore!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        imageBytes = outputStream!!.toByteArray()
        val values = ContentValues()
        values.put(UtilidadesArbol.CAMPO_NOMBRE, obj.treeName)
        values.put(UtilidadesArbol.CAMPO_NOMBRE_CIENTIFICO, obj.treeScientificName)
        values.put(UtilidadesArbol.CAMPO_DESCRIPCION, obj.treeDescription)
        values.put(UtilidadesArbol.CAMPO_IMAGEN, imageBytes)
        val cantidad = db.insert(UtilidadesArbol.TREES_TABLE_NAME, null, values)
        if (cantidad != 0L) {
            Toast.makeText(context, "Image added successfully", Toast.LENGTH_SHORT).show()
            db.close()
        } else {
            Toast.makeText(context, "ERROR: Image not added", Toast.LENGTH_SHORT).show()
        }
    }

    // METODO PARA VALIDAR SI EXISTE UN ARBOL
    @Throws(SQLException::class)
    fun validateTree(treeName: String): Cursor? {
        var cursor: Cursor? = null
        cursor = this.readableDatabase.query(
            UtilidadesArbol.TREES_TABLE_NAME, arrayOf(
                UtilidadesArbol.CAMPO_NOMBRE, UtilidadesArbol.CAMPO_NOMBRE_CIENTIFICO,
                UtilidadesArbol.CAMPO_DESCRIPCION, UtilidadesArbol.CAMPO_IMAGEN
            ),
            UtilidadesArbol.CAMPO_NOMBRE + " LIKE '" + treeName + "' ",
            null, null, null, null
        )
        return cursor
    }

    // ================= METODOS CRUD DEL PRODUCTO ================= //
    // METODO PARA GUARDAR UN PRODUCTO
    fun storeProducto(obj: Productos) {
        val db = this.writableDatabase
        val imageToStore = obj.productImg
        outputStream = ByteArrayOutputStream()
        imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        imageBytes = outputStream!!.toByteArray()
        val values = ContentValues()
        values.put(UtilidadesProducto.CAMPO_ID, obj.productID)
        values.put(UtilidadesProducto.CAMPO_NOMBRE, obj.productName)
        values.put(UtilidadesProducto.CAMPO_PRECIO, obj.productPrice)
        values.put(UtilidadesProducto.CAMPO_IMAGEN, imageBytes)
        val cantidad = db.insert(UtilidadesProducto.PRODUCTS_TABLE_NAME, null, values)
        if (cantidad != 0L) {
            Toast.makeText(context, "Image added successfully", Toast.LENGTH_SHORT).show()
            db.close()
        } else {
            Toast.makeText(context, "ERROR: Image not added", Toast.LENGTH_SHORT).show()
        }
    }

    // METODO PARA VALIDAR SI EXISTE UN PRODUCTO
    @Throws(SQLException::class)
    fun validateProduct(id: String): Cursor? {
        var cursor: Cursor? = null
        cursor = this.readableDatabase.query(
            UtilidadesProducto.PRODUCTS_TABLE_NAME, arrayOf(
                UtilidadesProducto.CAMPO_ID, UtilidadesProducto.CAMPO_NOMBRE,
                UtilidadesProducto.CAMPO_PRECIO, UtilidadesProducto.CAMPO_IMAGEN
            ),
            UtilidadesProducto.CAMPO_ID + " LIKE '" + id + "' ",
            null, null, null, null
        )
        return cursor
    }

    // ================= METODOS CRUD DEL EVENTO ================= //
    // METODO PARA VALIDAR UN EVENTO
    fun validateEvent(name: String): Cursor? {
        var cursor: Cursor? = null
        cursor = this.readableDatabase.query(
            UtilidadesEvento.EVENTS_TABLE_NAME,
            arrayOf(UtilidadesEvento.CAMPO_NOMBRE, UtilidadesEvento.CAMPO_FECHA),
            UtilidadesEvento.CAMPO_NOMBRE + " LIKE '" + name + "' ",
            null,
            null,
            null,
            null
        )
        return cursor
    }

    //METODO PARA GUARDAR UN EVENTO
    fun storeEvent(obj: Evento) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(UtilidadesEvento.CAMPO_NOMBRE, obj.eventName)
        values.put(UtilidadesEvento.CAMPO_FECHA, obj.eventDate)
        val cantidad = db.insert(UtilidadesEvento.EVENTS_TABLE_NAME, null, values)
        if (cantidad != 0L) {
            Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show()
            db.close()
        } else {
            Toast.makeText(context, "ERROR: No se guardo el registro", Toast.LENGTH_SHORT).show()
        }
    }

    // ================= METODOS CRUD DE LA PUBLICACION ================= //
    fun validatePost(name: String): Cursor? {
        var cursor: Cursor? = null
        cursor = this.readableDatabase.query(
            UtilidadesPublicacion.POSTS_TABLE_NAME, arrayOf(UtilidadesPublicacion.CAMPO_NOMBRE),
            UtilidadesPublicacion.CAMPO_NOMBRE + " LIKE '" + name + "' ",
            null, null, null, null
        )
        return cursor
    }

    fun storePost(obj: Publicacion) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(UtilidadesEvento.CAMPO_NOMBRE, obj.postName)
        val cantidad = db.insert(UtilidadesPublicacion.POSTS_TABLE_NAME, null, values)
        if (cantidad != 0L) {
            Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show()
            db.close()
        } else {
            Toast.makeText(context, "ERROR: No se guardo el registro", Toast.LENGTH_SHORT).show()
        }
    }
}