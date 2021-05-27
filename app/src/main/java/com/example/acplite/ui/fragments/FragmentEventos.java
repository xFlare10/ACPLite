package com.example.acplite.ui.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.adapters.RecyclerEventsAdapter;
import com.example.acplite.adapters.RecyclerProductsAdapter;
import com.example.acplite.databinding.FragmentEventosBinding;
import com.example.acplite.databinding.FragmentTreesBinding;
import com.example.acplite.entidades.Evento;
import com.example.acplite.entidades.Productos;
import com.example.acplite.helpers.ItemTapListener;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.ui.ActivityEventRegister;
import com.example.acplite.ui.ActivityProductRegister;
import com.example.acplite.ui.ActivityUDEvento;
import com.example.acplite.ui.ActivityUDProduct;
import com.example.acplite.utilidades.UtilidadesArbol;
import com.example.acplite.utilidades.UtilidadesEvento;
import com.example.acplite.utilidades.UtilidadesProducto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FragmentEventos extends Fragment {
    private FragmentEventosBinding binding;

    private ConexionSQLiteHelper conn;
    private RecyclerView rvEventos;
    private FloatingActionButton fab;
    private RecyclerEventsAdapter adapter;

    ArrayList<Evento> listaEvents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_eventos, container, false);

        conn = new ConexionSQLiteHelper(getContext(), UtilidadesArbol.DB_NAME, null, 1);

        listaEvents = new ArrayList<>();
        rvEventos = vista.findViewById(R.id.rvEventos);

        getData(conn);

        rvEventos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvEventos.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new RecyclerEventsAdapter(listaEvents, new ItemTapListener() {
            @Override
            public void onItemTap(View view, int position) {
                Toast.makeText(getContext(), "Tocaste el item " + listaEvents.get(position).getEventName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ActivityUDEvento.class);
                intent.putExtra("eventName",listaEvents.get(position).getEventName());
                intent.putExtra("eventDate",listaEvents.get(position).getEventDate());

                startActivity(intent);
            }
        });

        rvEventos.setAdapter(adapter);

        fab = vista.findViewById(R.id.fabToRegisterEvent);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityEventRegister.class));
            }
        });

        return vista;
    }

    //THIS IS THE METHOD THAT LOADS ALL THE DATA IN THE RECYCLER VIEW
    public void getData(ConexionSQLiteHelper conn){
        SQLiteDatabase db = conn.getWritableDatabase();
        byte[] imgBytes;

        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesEvento.EVENTS_TABLE_NAME, null);

        while (cursor.moveToNext()){
            String eventName = cursor.getString(0);
            String eventDate = cursor.getString(1);

            listaEvents.add(new Evento(eventName, eventDate));
        }
    }
}