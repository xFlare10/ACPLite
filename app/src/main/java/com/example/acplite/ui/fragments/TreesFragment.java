package com.example.acplite.ui.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.adapters.RecyclerTreesAdapter;
import com.example.acplite.databinding.FragmentTreesBinding;
import com.example.acplite.entidades.Arbol;
import com.example.acplite.helpers.ItemTapListener;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.ui.ActivityTreeRegister;
import com.example.acplite.ui.ActivityUDTree;
import com.example.acplite.utilidades.UtilidadesArbol;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class TreesFragment extends Fragment {
    private FragmentTreesBinding binding;

    private ConexionSQLiteHelper conn;
    private RecyclerView rvTrees;
    private FloatingActionButton fab;
    private RecyclerTreesAdapter adapter;

    ArrayList<Arbol> listaTrees;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_trees, container, false);

        conn = new ConexionSQLiteHelper(getContext(), UtilidadesArbol.DB_NAME, null, 1);

        listaTrees = new ArrayList<>();
        rvTrees = vista.findViewById(R.id.rvTrees);

        getData(conn);

        rvTrees.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTrees.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new RecyclerTreesAdapter(listaTrees, new ItemTapListener() {
            @Override
            public void onItemTap(View view, int position) {
                Toast.makeText(getContext(), "Tocaste el item " + listaTrees.get(position).getTreeName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ActivityUDTree.class);
                intent.putExtra("nombreArbol",listaTrees.get(position).getTreeName());
                intent.putExtra("nombreCientificoArbol",listaTrees.get(position).getTreeScientificName());
                intent.putExtra("descripcionArbol",listaTrees.get(position).getTreeDescription());

                //PARA PASAR IMAGENES GRANDES CON PUT EXTRA HAY QUE HACE LAS 4 SIGUIENTES LINEAS
                Bitmap b = listaTrees.get(position).getTreeImg(); // your bitmap
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 50, bs);
                intent.putExtra("imagenArbol",bs.toByteArray());

                startActivity(intent);
            }
        });

        rvTrees.setAdapter(adapter);

        fab = vista.findViewById(R.id.fabToRegisterTree);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityTreeRegister.class));
            }
        });

        return vista;
    }

    //THIS IS THE METHOD THAT LOADS ALL THE DATA IN THE RECYCLER VIEW
    public void getData(ConexionSQLiteHelper conn){
        SQLiteDatabase db = conn.getWritableDatabase();
        byte[] imgBytes;

        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesArbol.TREES_TABLE_NAME, null);

        while (cursor.moveToNext()){
            String treeName = cursor.getString(0);
            String treeScientificName = cursor.getString(1);
            String treeDescription = cursor.getString(2);
            imgBytes = cursor.getBlob(3);

            Bitmap obj = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

            listaTrees.add(new Arbol(treeName, treeScientificName, treeDescription, obj));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(conn);
    }
}