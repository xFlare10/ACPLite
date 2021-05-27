package com.example.acplite.ui

import androidx.appcompat.app.AppCompatActivity
import com.example.acplite.sqlite.ConexionSQLiteHelper
import com.example.acplite.adapters.RecyclerProductsAdapter
import com.example.acplite.entidades.Productos
import android.graphics.Bitmap
import android.os.Bundle
import com.example.acplite.R
import com.example.acplite.utilidades.UtilidadesProducto
import android.graphics.BitmapFactory
import android.content.Intent
import com.example.acplite.ui.ActivityUDProduct
import android.app.Activity
import android.provider.MediaStore
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.net.Uri
import android.view.View
import android.widget.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.ArrayList

class ActivityUDProduct : AppCompatActivity() {
    private var txtID: EditText? = null
    private var txtName: EditText? = null
    private var txtPrice: EditText? = null
    private var productImg: ImageView? = null
    private var btnUpd: Button? = null
    private var btnDelete: Button? = null
    private var txtvItemProductName: TextView? = null
    private var conn: ConexionSQLiteHelper? = null
    private val adapter: RecyclerProductsAdapter? = null
    var listaProductos: ArrayList<Productos>? = null
    private var outputStream: ByteArrayOutputStream? = null
    private var imageBytes: ByteArray
    private var imageFilePath: Uri? = null
    private var imgToStore: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_udproduct)
        conn = ConexionSQLiteHelper(applicationContext, UtilidadesProducto.DB_NAME, null, 1)
        txtID = findViewById(R.id.etProductID1)
        txtName = findViewById(R.id.etProductName1)
        txtPrice = findViewById(R.id.etProductPrice1)
        productImg = findViewById(R.id.productImg1)
        btnUpd = findViewById(R.id.btnUpdateProduct)
        btnDelete = findViewById(R.id.btnDeleteProduct)

        //TEXTVIEW FROM THE RECYCLER ITEM
        txtvItemProductName = findViewById(R.id.txtTreeName)
//        txtID.setText(intent.getIntExtra("idProducto", 0).toString())
//        txtName.setText(intent.getStringExtra("nombreProducto"))
//        txtPrice.setText(intent.getFloatExtra("precioProducto", 5f).toString())
//        //        treeImg.setImageBitmap(getIntent().getParcelableExtra("imagenArbol"));
//
//        //ESTE IF ES PARA RECIBIR EL PUT EXTRA DE IMAGENES GRANDES
//        if (intent.hasExtra("imagenProducto")) {
//            val b = BitmapFactory.decodeByteArray(
//                intent.getByteArrayExtra("imagenProducto"),
//                0, intent.getByteArrayExtra("imagenProducto")!!.size
//            )
//            productImg.setImageBitmap(b)
//        }
//        productImg.setOnClickListener(View.OnClickListener { chooseImage() })
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
            productImg!!.setImageBitmap(imgToStore)
        }
    }

    fun clickear(v: View) {
        when (v.id) {
            R.id.btnUpdateProduct -> actualizarProducto()
            R.id.btnDeleteProduct -> eliminarProducto()
        }
    }

    private fun actualizarProducto() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        val db = conn!!.writableDatabase
        val id = arrayOf(txtID!!.text.toString())
        val campoID = txtID!!.text.toString()
        val campoNombre = txtName!!.text.toString()
        val campoPrecio = txtPrice!!.text.toString()
        productImg!!.isDrawingCacheEnabled = true
        val campoImg = productImg!!.drawingCache
        outputStream = ByteArrayOutputStream()
        campoImg!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        imageBytes = outputStream!!.toByteArray()
        if (!campoID.isEmpty() && !campoNombre.isEmpty() && !campoPrecio.isEmpty() && campoImg != null) {
            val values = ContentValues()
            values.put(UtilidadesProducto.CAMPO_ID, txtID!!.text.toString())
            values.put(UtilidadesProducto.CAMPO_NOMBRE, txtName!!.text.toString())
            values.put(UtilidadesProducto.CAMPO_PRECIO, txtPrice!!.text.toString())
            values.put(UtilidadesProducto.CAMPO_IMAGEN, imageBytes)

            /*db.Update RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, EL HASH O TABLA DE VALORES POR LOS QUE SE VA A ACTUALIZAR,
             * LA CONDICION, EL ARREGLO STRING POR EL CUAL SE VA A CAMBIAR EL COMODIN)
             * */
            val cantidad = db.update(
                UtilidadesProducto.PRODUCTS_TABLE_NAME,
                values,
                UtilidadesProducto.CAMPO_ID + "= ?",
                id
            )
            db.close()
            if (cantidad == 1) {
                Toast.makeText(
                    applicationContext,
                    "El producto se actualizo con exito",
                    Toast.LENGTH_SHORT
                ).show()
                limpiar()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error: producto no actualizado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(applicationContext, "Debes llenar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarProducto() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        val db = conn!!.writableDatabase
        val id = arrayOf(txtID!!.text.toString())
        val campoID = txtID!!.text.toString()
        val campoNombre = txtName!!.text.toString()
        val campoPrecio = txtPrice!!.text.toString()
        productImg!!.isDrawingCacheEnabled = true
        val campoImg = productImg!!.drawingCache
        outputStream = ByteArrayOutputStream()
        campoImg!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        imageBytes = outputStream!!.toByteArray()
        if (!campoID.isEmpty() && !campoNombre.isEmpty() && !campoPrecio.isEmpty() && campoImg != null) {
            val values = ContentValues()
            values.put(UtilidadesProducto.CAMPO_ID, txtID!!.text.toString())
            values.put(UtilidadesProducto.CAMPO_NOMBRE, txtName!!.text.toString())
            values.put(UtilidadesProducto.CAMPO_PRECIO, txtPrice!!.text.toString())
            values.put(UtilidadesProducto.CAMPO_IMAGEN, imageBytes)

            /*db.delete RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, LA CONDICION WHERE, EL PARAMETRO QUE SE SUSTITUYE POR EL COMODIN
             * */
            val cantidad = db.delete(
                UtilidadesProducto.PRODUCTS_TABLE_NAME,
                UtilidadesProducto.CAMPO_ID + "= ?",
                id
            )
            db.close()
            if (cantidad == 1) {
                Toast.makeText(
                    applicationContext,
                    "El Producto se elimino con exito",
                    Toast.LENGTH_SHORT
                ).show()
                limpiar()
            } else {
                Toast.makeText(applicationContext, "Error: árbol no eliminado", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(applicationContext, "Debes llenar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiar() {
        txtID!!.setText("")
        txtName!!.setText("")
        txtPrice!!.setText("")
        productImg!!.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_camera_alt_24))
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 100
    }
}