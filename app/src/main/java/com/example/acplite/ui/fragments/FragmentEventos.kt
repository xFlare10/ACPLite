package com.example.acplite.ui.fragments

import android.content.Intent
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
import com.example.acplite.adapters.RecyclerEventsAdapter
import com.example.acplite.databinding.FragmentEventosBinding
import com.example.acplite.entidades.Evento
import com.example.acplite.helpers.ItemTapListener
import com.example.acplite.sqlite.ConexionSQLiteHelper
import com.example.acplite.ui.ActivityEventRegister
import com.example.acplite.ui.ActivityUDEvento
import com.example.acplite.utilidades.UtilidadesArbol
import com.example.acplite.utilidades.UtilidadesEvento
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class FragmentEventos : Fragment() {
    private val binding: FragmentEventosBinding? = null
    private var conn: ConexionSQLiteHelper? = null
    private var rvEventos: RecyclerView? = null
    private var fab: FloatingActionButton? = null
    private var adapter: RecyclerEventsAdapter? = null
    var listaEvents: ArrayList<Evento>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_eventos, container, false)
        conn = ConexionSQLiteHelper(context, UtilidadesArbol.DB_NAME, null, 1)
        listaEvents = ArrayList()
        rvEventos = vista.findViewById(R.id.rvEventos)
        getData(conn!!)
        rvEventos.setLayoutManager(LinearLayoutManager(context))
        rvEventos.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = RecyclerEventsAdapter(listaEvents!!, object : ItemTapListener {
            override fun onItemTap(view: View?, position: Int) {
                Toast.makeText(
                    context,
                    "Tocaste el item " + listaEvents!![position].eventName,
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(activity, ActivityUDEvento::class.java)
                intent.putExtra("eventName", listaEvents!![position].eventName)
                intent.putExtra("eventDate", listaEvents!![position].eventDate)
                startActivity(intent)
            }
        })
        rvEventos.setAdapter(adapter)
        fab = vista.findViewById(R.id.fabToRegisterEvent)
        fab.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    activity,
                    ActivityEventRegister::class.java
                )
            )
        })
        return vista
    }

    //THIS IS THE METHOD THAT LOADS ALL THE DATA IN THE RECYCLER VIEW
    fun getData(conn: ConexionSQLiteHelper) {
        val db = conn.writableDatabase
        var imgBytes: ByteArray
        val cursor = db.rawQuery("SELECT * FROM " + UtilidadesEvento.EVENTS_TABLE_NAME, null)
        while (cursor.moveToNext()) {
            val eventName = cursor.getString(0)
            val eventDate = cursor.getString(1)
            listaEvents!!.add(Evento(eventName, eventDate))
        }
    }
}