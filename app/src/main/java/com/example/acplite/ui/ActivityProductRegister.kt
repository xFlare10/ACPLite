package com.example.acplite.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.acplite.R
import com.example.acplite.entidades.Productos
import com.example.acplite.sqlite.ConexionSQLiteHelper
import com.example.acplite.utilidades.UtilidadesProducto
import java.io.IOException

class ActivityProductRegister : AppCompatActivity() {
    private var productID: EditText? = null
    private var productName: EditText? = null
    private var productPrice: EditText? = null
    private var imageViewObj: ImageView? = null
    private var imageFilePath: Uri? = null
    private var imgToStore: Bitmap? = null
    private var dbh: ConexionSQLiteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_register)
        dbh = ConexionSQLiteHelper(applicationContext, UtilidadesProducto.DB_NAME, null, 1)
        productID = findViewById(R.id.etProductID)
        productName = findViewById(R.id.etProductName)
        productPrice = findViewById(R.id.etProductPrice)
        imageViewObj = findViewById(R.id.productImg)
        imageViewObj.setOnClickListener(View.OnClickListener { chooseImage() })
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
            imageViewObj!!.setImageBitmap(imgToStore)
        }
    }

    fun storeProductImage(v: View?) {
        val id = productID!!.text.toString()
        val Name = productName!!.text.toString()
        val Price = productPrice!!.text.toString()
        if (!Name.isEmpty() && !id.isEmpty() && !Price.isEmpty() && imageViewObj!!.drawable != null && imgToStore != null) {
            val cursor = dbh!!.validateProduct(id)
            if (cursor!!.count > 0) {
                Toast.makeText(applicationContext, "El Producto ya existe", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val campoID = id.toInt()
                val campoPrice = Price.toFloat()
                dbh!!.storeProducto(Productos(campoID, Name, campoPrice, imgToStore!!))
                Toast.makeText(
                    applicationContext,
                    "Producto guardado con éxito",
                    Toast.LENGTH_SHORT
                ).show()

                //LIMPIAMOS CAMPOS
                productID!!.setText("")
                productName!!.setText("")
                productPrice!!.setText("")
                imageViewObj!!.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_camera_alt_24))
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Llene los campos antes de guardar",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 100
    }
}