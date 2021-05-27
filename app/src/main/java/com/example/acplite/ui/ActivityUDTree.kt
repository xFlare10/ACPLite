package com.example.acplite.ui

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.acplite.R
import com.example.acplite.adapters.RecyclerTreesAdapter
import com.example.acplite.entidades.Arbol
import com.example.acplite.sqlite.ConexionSQLiteHelper
import com.example.acplite.utilidades.UtilidadesArbol
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class ActivityUDTree : AppCompatActivity() {
    private var txtName: EditText? = null
    private var txtSName: EditText? = null
    private var txtDesc: EditText? = null
    private var treeImg: ImageView? = null
    private var btnUpd: Button? = null
    private var btnDelete: Button? = null
    private var txtvItemTreeName: TextView? = null
    private var conn: ConexionSQLiteHelper? = null
    private val adapter: RecyclerTreesAdapter? = null
    var listaTrees: ArrayList<Arbol>? = null
    private var outputStream: ByteArrayOutputStream? = null
    private var imageBytes: ByteArray
    private var imageFilePath: Uri? = null
    private var imgToStore: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_udtree)
        conn = ConexionSQLiteHelper(applicationContext, UtilidadesArbol.DB_NAME, null, 1)
        txtName = findViewById(R.id.etTreeName1)
        txtSName = findViewById(R.id.etTreeScientificName1)
        txtDesc = findViewById(R.id.etTreeDescription1)
        treeImg = findViewById(R.id.treeImg1)
        btnUpd = findViewById(R.id.btnUpdateTree)
        btnDelete = findViewById(R.id.btnDeleteTree)

        //TEXTVIEW FROM THE RECYCLER ITEM
        txtvItemTreeName = findViewById(R.id.txtTreeName)

        //ERRORES
//        txtName.setText(intent.getStringExtra("nombreArbol"))
//        txtSName.setText(intent.getStringExtra("nombreCientificoArbol"))
//        txtDesc.setText(intent.getStringExtra("descripcionArbol"))
//        //        treeImg.setImageBitmap(getIntent().getParcelableExtra("imagenArbol"));
//
//        //ESTE IF ES PARA RECIBIR EL PUT EXTRA DE IMAGENES GRANDES
//        if (intent.hasExtra("imagenArbol")) {
//            val b = BitmapFactory.decodeByteArray(
//                intent.getByteArrayExtra("imagenArbol"),
//                0, intent.getByteArrayExtra("imagenArbol")!!.size
//            )
//            treeImg.setImageBitmap(b)
//        }
//        treeImg.setOnClickListener(View.OnClickListener { chooseImage() })
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageFilePath = data.data
            try {
                imgToStore = MediaStore.Images.Media.getBitmap(contentResolver, imageFilePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            treeImg!!.setImageBitmap(imgToStore)
        }
    }

    fun clickear(v: View) {
        when (v.id) {
            R.id.btnUpdateTree -> actualizar()
            R.id.btnDeleteTree -> eliminar()
        }
    }

    private fun actualizar() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        val db = conn!!.writableDatabase
        val name = arrayOf(txtName!!.text.toString())
        val campoName = txtName!!.text.toString()
        val campoNombreC = txtSName!!.text.toString()
        val campoDesc = txtDesc!!.text.toString()
        treeImg!!.isDrawingCacheEnabled = true
        val campoImg = treeImg!!.drawingCache
        outputStream = ByteArrayOutputStream()
        campoImg!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        imageBytes = outputStream!!.toByteArray()
        if (!campoName.isEmpty() && !campoNombreC.isEmpty() && !campoDesc.isEmpty() && campoImg != null) {
            val values = ContentValues()
            values.put(UtilidadesArbol.CAMPO_NOMBRE, txtName!!.text.toString())
            values.put(UtilidadesArbol.CAMPO_NOMBRE_CIENTIFICO, txtSName!!.text.toString())
            values.put(UtilidadesArbol.CAMPO_DESCRIPCION, txtDesc!!.text.toString())
            values.put(UtilidadesArbol.CAMPO_IMAGEN, imageBytes)

            /*db.Update RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, EL HASH O TABLA DE VALORES POR LOS QUE SE VA A ACTUALIZAR,
             * LA CONDICION, EL ARREGLO STRING POR EL CUAL SE VA A CAMBIAR EL COMODIN)
             * */
            val cantidad = db.update(
                UtilidadesArbol.TREES_TABLE_NAME,
                values,
                UtilidadesArbol.CAMPO_NOMBRE + "= ?",
                name
            )
            db.close()
            if (cantidad == 1) {
                Toast.makeText(
                    applicationContext,
                    "El Árbol se actualizo con exito",
                    Toast.LENGTH_SHORT
                ).show()
                txtName!!.setText("")
                txtSName!!.setText("")
                txtDesc!!.setText("")
                treeImg!!.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_camera_alt_24))
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error: árbol no actualizado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(applicationContext, "Debes llenar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminar() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        val db = conn!!.writableDatabase
        val name = arrayOf(txtName!!.text.toString())
        val campoName = txtName!!.text.toString()
        val campoNombreC = txtSName!!.text.toString()
        val campoDesc = txtDesc!!.text.toString()
        treeImg!!.isDrawingCacheEnabled = true
        val campoImg = treeImg!!.drawingCache
        outputStream = ByteArrayOutputStream()
        campoImg!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        imageBytes = outputStream!!.toByteArray()
        if (!campoName.isEmpty() && !campoNombreC.isEmpty() && !campoDesc.isEmpty() && campoImg != null) {
            val values = ContentValues()
            values.put(UtilidadesArbol.CAMPO_NOMBRE, txtName!!.text.toString())
            values.put(UtilidadesArbol.CAMPO_NOMBRE_CIENTIFICO, txtSName!!.text.toString())
            values.put(UtilidadesArbol.CAMPO_DESCRIPCION, txtDesc!!.text.toString())
            values.put(UtilidadesArbol.CAMPO_IMAGEN, imageBytes)

            /*db.delete RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, LA CONDICION WHERE, EL PARAMETRO QUE SE SUSTITUYE POR EL COMODIN
             * */
            val cantidad = db.delete(
                UtilidadesArbol.TREES_TABLE_NAME,
                UtilidadesArbol.CAMPO_NOMBRE + "= ?",
                name
            )
            db.close()
            if (cantidad == 1) {
                Toast.makeText(
                    applicationContext,
                    "El Árbol se elimino con exito",
                    Toast.LENGTH_SHORT
                ).show()
                txtName!!.setText("")
                txtSName!!.setText("")
                txtDesc!!.setText("")
                treeImg!!.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_camera_alt_24))
            } else {
                Toast.makeText(applicationContext, "Error: árbol no eliminado", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(applicationContext, "Debes llenar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun getData(conn: ConexionSQLiteHelper) {
        val db = conn.writableDatabase
        var imgBytes: ByteArray
        val cursor = db.rawQuery("SELECT * FROM " + UtilidadesArbol.TREES_TABLE_NAME, null)
        while (cursor.moveToNext()) {
            val treeName = cursor.getString(0)
            val treeScientificName = cursor.getString(1)
            val treeDescription = cursor.getString(2)
            imgBytes = cursor.getBlob(3)
            val obj = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
            listaTrees!!.add(Arbol(treeName, treeScientificName, treeDescription, obj))
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 100
    }
}