package com.example.acplite.ui

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.acplite.R
import com.example.acplite.sqlite.ConexionSQLiteHelper
import com.example.acplite.utilidades.UtilidadesPublicacion

class ActivityUDPublicacion : AppCompatActivity() {
    private var txtPostName: EditText? = null
    private var conn: ConexionSQLiteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_udpublicacion)
        conn = ConexionSQLiteHelper(applicationContext, UtilidadesPublicacion.DB_NAME, null, 1)
        txtPostName = findViewById(R.id.etPostName1)

//        txtPostName.setText(intent.getStringExtra("postName"))
    }

    fun clickear(v: View) {
        when (v.id) {
            R.id.btnUpdateEvento -> actualizarPost()
            R.id.btnDeleteEvento -> eliminarPost()
        }
    }

    private fun actualizarPost() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        val db = conn!!.writableDatabase
        val name = arrayOf(txtPostName!!.text.toString())
        val campoNombre = txtPostName!!.text.toString()
        if (!campoNombre.isEmpty()) {
            val values = ContentValues()
            values.put(UtilidadesPublicacion.CAMPO_NOMBRE, txtPostName!!.text.toString())

            /*db.Update RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, EL HASH O TABLA DE VALORES POR LOS QUE SE VA A ACTUALIZAR,
             * LA CONDICION, EL ARREGLO STRING POR EL CUAL SE VA A CAMBIAR EL COMODIN)
             * */
            val cantidad = db.update(
                UtilidadesPublicacion.POSTS_TABLE_NAME, values,
                UtilidadesPublicacion.CAMPO_NOMBRE + " = ?", name
            )
            db.close()
            if (cantidad == 1) {
                Toast.makeText(
                    applicationContext,
                    "La publicación se actualizo con exito",
                    Toast.LENGTH_SHORT
                ).show()
                txtPostName!!.setText("")
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error: Publicacion no actualizada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(applicationContext, "Debes llenar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarPost() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        val db = conn!!.writableDatabase
        val name = arrayOf(txtPostName!!.text.toString())
        val campoNombre = txtPostName!!.text.toString()
        if (!campoNombre.isEmpty()) {
            val values = ContentValues()
            values.put(UtilidadesPublicacion.CAMPO_NOMBRE, txtPostName!!.text.toString())

            /*db.delete RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, LA CONDICION WHERE, EL PARAMETRO QUE SE SUSTITUYE POR EL COMODIN
             * */
            val cantidad = db.delete(
                UtilidadesPublicacion.POSTS_TABLE_NAME,
                UtilidadesPublicacion.CAMPO_NOMBRE + "= ?",
                name
            )
            db.close()
            if (cantidad == 1) {
                Toast.makeText(
                    applicationContext,
                    "La publicación se elimino con exito",
                    Toast.LENGTH_SHORT
                ).show()
                txtPostName!!.setText("")
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error: Publicación no eliminada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(applicationContext, "Debes llenar los campos", Toast.LENGTH_SHORT).show()
        }
    }
}