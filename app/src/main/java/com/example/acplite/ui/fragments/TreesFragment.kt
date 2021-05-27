package com.example.acplite.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acplite.R
import com.example.acplite.adapters.RecyclerTreesAdapter
import com.example.acplite.databinding.FragmentTreesBinding
import com.example.acplite.entidades.Arbol
import com.example.acplite.helpers.ItemTapListener
import com.example.acplite.sqlite.ConexionSQLiteHelper
import com.example.acplite.ui.ActivityTreeRegister
import com.example.acplite.ui.ActivityUDTree
import com.example.acplite.utilidades.UtilidadesArbol
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.ByteArrayOutputStream
import java.util.*

class TreesFragment : Fragment() {
    private var binding: FragmentTreesBinding? = null
    private var conn: ConexionSQLiteHelper? = null
    private var rvTrees: RecyclerView? = null
    private var fab: FloatingActionButton? = null
    private var adapter: RecyclerTreesAdapter? = null
    var listaTrees: ArrayList<Arbol>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_trees, container, false)
        conn = ConexionSQLiteHelper(context, UtilidadesArbol.DB_NAME, null, 1)
        listaTrees = ArrayList()
        rvTrees = vista.findViewById(R.id.rvTrees)
        getData(conn)
        rvTrees.setLayoutManager(LinearLayoutManager(context))
        rvTrees.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = RecyclerTreesAdapter(listaTrees!!, object : ItemTapListener {
            override fun onItemTap(view: View?, position: Int) {
                Toast.makeText(
                    context,
                    "Tocaste el item " + listaTrees!![position].treeName,
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(activity, ActivityUDTree::class.java)
                intent.putExtra("nombreArbol", listaTrees!![position].treeName)
                intent.putExtra("nombreCientificoArbol", listaTrees!![position].treeScientificName)
                intent.putExtra("descripcionArbol", listaTrees!![position].treeDescription)

                //PARA PASAR IMAGENES GRANDES CON PUT EXTRA HAY QUE HACE LAS 4 SIGUIENTES LINEAS
                val b = listaTrees!![position].treeImg // your bitmap
                val bs = ByteArrayOutputStream()
                b!!.compress(Bitmap.CompressFormat.PNG, 50, bs)
                intent.putExtra("imagenArbol", bs.toByteArray())
                startActivity(intent)
            }
        })
        rvTrees.setAdapter(adapter)
        fab = vista.findViewById(R.id.fabToRegisterTree)
        fab.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    activity,
                    ActivityTreeRegister::class.java
                )
            )
        })
        return vista
    }

    //THIS IS THE METHOD THAT LOADS ALL THE DATA IN THE RECYCLER VIEW
    fun getData(conn: ConexionSQLiteHelper?) {
        val db = conn!!.writableDatabase
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onResume() {
        super.onResume()
        getData(conn)
    }
}