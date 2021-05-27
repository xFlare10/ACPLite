package com.example.acplite.ui.fragments

import com.example.acplite.entidades.Productos.productName
import com.example.acplite.entidades.Productos.productID
import com.example.acplite.entidades.Productos.productPrice
import com.example.acplite.entidades.Productos.productImg
import com.example.acplite.sqlite.ConexionSQLiteHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.acplite.adapters.RecyclerProductsAdapter
import com.example.acplite.entidades.Productos
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.acplite.R
import com.example.acplite.utilidades.UtilidadesArbol
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.acplite.helpers.ItemTapListener
import android.widget.Toast
import android.content.Intent
import com.example.acplite.ui.ActivityUDProduct
import android.graphics.Bitmap
import com.example.acplite.ui.ActivityProductRegister
import android.database.sqlite.SQLiteDatabase
import com.example.acplite.utilidades.UtilidadesProducto
import android.graphics.BitmapFactory
import android.view.View
import androidx.fragment.app.Fragment
import com.example.acplite.databinding.FragmentTreesBinding
import java.io.ByteArrayOutputStream
import java.util.ArrayList

class FragmentProductos : Fragment() {
    private val binding: FragmentTreesBinding? = null
    private var conn: ConexionSQLiteHelper? = null
    private var rvProducts: RecyclerView? = null
    private var fab: FloatingActionButton? = null
    private var adapter: RecyclerProductsAdapter? = null
    var listaProducts: ArrayList<Productos>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_productos, container, false)
        conn = ConexionSQLiteHelper(context, UtilidadesArbol.DB_NAME, null, 1)
        listaProducts = ArrayList()
        rvProducts = vista.findViewById(R.id.rvProductos)
        getData(conn!!)
        rvProducts.setLayoutManager(LinearLayoutManager(context))
        rvProducts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = RecyclerProductsAdapter(listaProducts!!, object : ItemTapListener {
            override fun onItemTap(view: View?, position: Int) {
                Toast.makeText(
                    context,
                    "Tocaste el item " + listaProducts!![position].productName,
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(activity, ActivityUDProduct::class.java)
                intent.putExtra("idProducto", listaProducts!![position].productID)
                intent.putExtra("nombreProducto", listaProducts!![position].productName)
                intent.putExtra("precioProducto", listaProducts!![position].productPrice)

                //PARA PASAR IMAGENES GRANDES CON PUT EXTRA HAY QUE HACE LAS 4 SIGUIENTES LINEAS
                val b = listaProducts!![position].productImg // your bitmap
                val bs = ByteArrayOutputStream()
                b.compress(Bitmap.CompressFormat.PNG, 50, bs)
                intent.putExtra("imagenProducto", bs.toByteArray())
                startActivity(intent)
            }
        })
        rvProducts.setAdapter(adapter)
        fab = vista.findViewById(R.id.fabToRegisterProduct)
        fab.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    activity,
                    ActivityProductRegister::class.java
                )
            )
        })
        return vista
    }

    //THIS IS THE METHOD THAT LOADS ALL THE DATA IN THE RECYCLER VIEW
    fun getData(conn: ConexionSQLiteHelper) {
        val db = conn.writableDatabase
        var imgBytes: ByteArray
        val cursor = db.rawQuery("SELECT * FROM " + UtilidadesProducto.PRODUCTS_TABLE_NAME, null)
        while (cursor.moveToNext()) {
            val productID = cursor.getInt(0)
            val productName = cursor.getString(1)
            val productPrice = cursor.getFloat(2)
            imgBytes = cursor.getBlob(3)
            val obj = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
            listaProducts!!.add(Productos(productID, productName, productPrice, obj))
        }
    }
}