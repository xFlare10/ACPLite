package com.example.acplite.ui

import com.example.acplite.ui.fragments.DatePickerFragment.Companion.newInstance
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.example.acplite.sqlite.ConexionSQLiteHelper
import android.os.Bundle
import com.example.acplite.R
import com.example.acplite.utilidades.UtilidadesEvento
import com.example.acplite.ui.fragments.DatePickerFragment
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.view.View
import android.widget.Button
import android.widget.Toast

class ActivityUDEvento : AppCompatActivity() {
    private var txtEventName: EditText? = null
    private var txtEventDate: EditText? = null
    private val btnUpd: Button? = null
    private val btnDelete: Button? = null
    private var conn: ConexionSQLiteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_udevento)
        conn = ConexionSQLiteHelper(applicationContext, UtilidadesEvento.DB_NAME, null, 1)
        txtEventName = findViewById(R.id.etEventName1)
        txtEventDate = findViewById(R.id.etEventDate1)

//        txtEventName.setText(intent.getStringExtra("eventName").toString())
//        txtEventDate.setText(intent.getStringExtra("eventDate"))
//
//        txtEventDate.setOnClickListener(View.OnClickListener { showDatePickerDialog() })
    }

    private fun showDatePickerDialog() {
        val newFragment =
            newInstance { datePicker, year, month, day -> // +1 because January is zero
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                txtEventDate!!.setText(selectedDate)
            }
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun clickear(v: View) {
        when (v.id) {
            R.id.btnUpdateEvento -> actualizarEvento()
            R.id.btnDeleteEvento -> eliminarEvento()
        }
    }

    private fun actualizarEvento() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        val db = conn!!.writableDatabase
        val name = arrayOf(txtEventName!!.text.toString())
        val campoNombre = txtEventName!!.text.toString()
        val campoDate = txtEventDate!!.text.toString()
        if (!campoNombre.isEmpty() && !campoDate.isEmpty()) {
            val values = ContentValues()
            values.put(UtilidadesEvento.CAMPO_NOMBRE, txtEventName!!.text.toString())
            values.put(UtilidadesEvento.CAMPO_FECHA, txtEventDate!!.text.toString())

            /*db.Update RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, EL HASH O TABLA DE VALORES POR LOS QUE SE VA A ACTUALIZAR,
             * LA CONDICION, EL ARREGLO STRING POR EL CUAL SE VA A CAMBIAR EL COMODIN)
             * */
            val cantidad = db.update(
                UtilidadesEvento.EVENTS_TABLE_NAME, values,
                UtilidadesEvento.CAMPO_NOMBRE + " = ?", name
            )
            db.close()
            if (cantidad == 1) {
                Toast.makeText(
                    applicationContext,
                    "El Evento se actualizo con exito",
                    Toast.LENGTH_SHORT
                ).show()
                limpiar()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error: Evento no actualizado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(applicationContext, "Debes llenar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarEvento() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        val db = conn!!.writableDatabase
        val name = arrayOf(txtEventName!!.text.toString())
        val campoNombre = txtEventName!!.text.toString()
        val campoDate = txtEventDate!!.text.toString()
        if (!campoNombre.isEmpty() && !campoDate.isEmpty()) {
            val values = ContentValues()
            values.put(UtilidadesEvento.CAMPO_NOMBRE, txtEventName!!.text.toString())
            values.put(UtilidadesEvento.CAMPO_FECHA, txtEventDate!!.text.toString())

            /*db.delete RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, LA CONDICION WHERE, EL PARAMETRO QUE SE SUSTITUYE POR EL COMODIN
             * */
            val cantidad = db.delete(
                UtilidadesEvento.EVENTS_TABLE_NAME,
                UtilidadesEvento.CAMPO_NOMBRE + "= ?",
                name
            )
            db.close()
            if (cantidad == 1) {
                Toast.makeText(
                    applicationContext,
                    "El Evento se elimino con exito",
                    Toast.LENGTH_SHORT
                ).show()
                limpiar()
            } else {
                Toast.makeText(applicationContext, "Error: Evento no eliminado", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(applicationContext, "Debes llenar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiar() {
        txtEventName!!.setText("")
        txtEventDate!!.setText("")
    }
}