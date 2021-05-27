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
import com.example.acplite.entidades.Arbol
import com.example.acplite.sqlite.ConexionSQLiteHelper
import com.example.acplite.utilidades.UtilidadesArbol
import java.io.IOException

class ActivityTreeRegister : AppCompatActivity() {
    private var treeName: EditText? = null
    private var treeScName: EditText? = null
    private var treeDesc: EditText? = null
    private var imageViewObj: ImageView? = null
    private var imageFilePath: Uri? = null
    private var imgToStore: Bitmap? = null
    private var dbh: ConexionSQLiteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree_register)
        treeName = findViewById(R.id.etTreeName)
        treeScName = findViewById(R.id.etTreeScientificName)
        treeDesc = findViewById(R.id.etTreeDescription)
        imageViewObj = findViewById(R.id.treeImg)
        dbh = ConexionSQLiteHelper(applicationContext, UtilidadesArbol.DB_NAME, null, 1)
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

    fun storeImage(v: View?) {
        val Name = treeName!!.text.toString()
        val NameSc = treeScName!!.text.toString()
        val Desc = treeDesc!!.text.toString()
        if (!Name.isEmpty() && !NameSc.isEmpty() && !Desc.isEmpty() && imageViewObj!!.drawable != null && imgToStore != null) {
            val cursor = dbh!!.validateTree(Name)
            if (cursor!!.count > 0) {
                Toast.makeText(applicationContext, "El Árbol ya existe", Toast.LENGTH_SHORT).show()
            } else {
                dbh!!.storeTree(Arbol(Name, NameSc, Desc, imgToStore))
                Toast.makeText(applicationContext, "Imagen guardada con éxito", Toast.LENGTH_SHORT)
                    .show()

                //LIMPIAMOS CAMPOS
                treeName!!.setText("")
                treeScName!!.setText("")
                treeDesc!!.setText("")
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