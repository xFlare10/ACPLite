package com.example.acplite.ui.fragments

import com.example.acplite.entidades.Publicacion.postName
import com.example.acplite.sqlite.ConexionSQLiteHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.acplite.adapters.RecyclerPostsAdapter
import com.example.acplite.entidades.Publicacion
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.acplite.R
import com.example.acplite.utilidades.UtilidadesArbol
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.acplite.helpers.ItemTapListener
import android.content.Intent
import com.example.acplite.ui.ActivityUDPublicacion
import com.example.acplite.ui.ActivityPostRegister
import android.database.sqlite.SQLiteDatabase
import android.view.View
import androidx.fragment.app.Fragment
import com.example.acplite.databinding.FragmentPublicacionesBinding
import com.example.acplite.utilidades.UtilidadesPublicacion
import java.util.ArrayList

class FragmentPublicaciones : Fragment() {
    private val binding: FragmentPublicacionesBinding? = null
    private var conn: ConexionSQLiteHelper? = null
    private var rvPublicaciones: RecyclerView? = null
    private var fab: FloatingActionButton? = null
    private var adapter: RecyclerPostsAdapter? = null
    var listaPublicaciones: ArrayList<Publicacion>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_publicaciones, container, false)
        conn = ConexionSQLiteHelper(context, UtilidadesArbol.DB_NAME, null, 1)
        listaPublicaciones = ArrayList()
        rvPublicaciones = vista.findViewById(R.id.rvPublicaciones)
        getData(conn!!)
        Toast.makeText(context, "HOLA", Toast.LENGTH_SHORT).show()
        rvPublicaciones.setLayoutManager(LinearLayoutManager(context))
        rvPublicaciones.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        adapter = RecyclerPostsAdapter(listaPublicaciones!!, object : ItemTapListener {
            override fun onItemTap(view: View?, position: Int) {
                Toast.makeText(
                    context,
                    "Tocaste el item " + listaPublicaciones!![position].postName,
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(activity, ActivityUDPublicacion::class.java)
                intent.putExtra("postName", listaPublicaciones!![position].postName)
                startActivity(intent)
            }
        })
        rvPublicaciones.setAdapter(adapter)
        fab = vista.findViewById(R.id.fabToRegisterPost)
        fab.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    activity,
                    ActivityPostRegister::class.java
                )
            )
        })
        return vista
    }

    //THIS IS THE METHOD THAT LOADS ALL THE DATA IN THE RECYCLER VIEW
    fun getData(conn: ConexionSQLiteHelper) {
        val db = conn.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + UtilidadesPublicacion.POSTS_TABLE_NAME, null)
        while (cursor.moveToNext()) {
            val postName = cursor.getString(0)
            listaPublicaciones!!.add(Publicacion(postName))
        }
    }
}