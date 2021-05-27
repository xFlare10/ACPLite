package com.example.acplite.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acplite.R;
import com.example.acplite.adapters.RecyclerEventsAdapter;
import com.example.acplite.adapters.RecyclerPostsAdapter;
import com.example.acplite.databinding.FragmentEventosBinding;
import com.example.acplite.databinding.FragmentPublicacionesBinding;
import com.example.acplite.entidades.Evento;
import com.example.acplite.entidades.Publicacion;
import com.example.acplite.helpers.ItemTapListener;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.ui.ActivityEventRegister;
import com.example.acplite.ui.ActivityPostRegister;
import com.example.acplite.ui.ActivityUDEvento;
import com.example.acplite.ui.ActivityUDPublicacion;
import com.example.acplite.utilidades.UtilidadesArbol;
import com.example.acplite.utilidades.UtilidadesEvento;
import com.example.acplite.utilidades.UtilidadesPublicacion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentPublicaciones extends Fragment {
    private FragmentPublicacionesBinding binding;

    private ConexionSQLiteHelper conn;
    private RecyclerView rvPublicaciones;
    private FloatingActionButton fab;
    private RecyclerPostsAdapter adapter;

    ArrayList<Publicacion> listaPublicaciones;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_publicaciones, container, false);

        conn = new ConexionSQLiteHelper(getContext(), UtilidadesArbol.DB_NAME, null, 1);

        listaPublicaciones = new ArrayList<>();
        rvPublicaciones = vista.findViewById(R.id.rvPublicaciones);

        getData(conn);
        Toast.makeText(getContext(), "HOLA", Toast.LENGTH_SHORT).show();

        rvPublicaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPublicaciones.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new RecyclerPostsAdapter(listaPublicaciones, new ItemTapListener() {
            @Override
            public void onItemTap(View view, int position) {
                Toast.makeText(getContext(), "Tocaste el item " + listaPublicaciones.get(position).getPostName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ActivityUDPublicacion.class);
                intent.putExtra("postName",listaPublicaciones.get(position).getPostName());

                startActivity(intent);
            }
        });

        rvPublicaciones.setAdapter(adapter);

        fab = vista.findViewById(R.id.fabToRegisterPost);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityPostRegister.class));
            }
        });

        return vista;
    }

    //THIS IS THE METHOD THAT LOADS ALL THE DATA IN THE RECYCLER VIEW
    public void getData(ConexionSQLiteHelper conn){
        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesPublicacion.POSTS_TABLE_NAME, null);

        while (cursor.moveToNext()){
            String postName = cursor.getString(0);

            listaPublicaciones.add(new Publicacion(postName));
        }
    }
}
