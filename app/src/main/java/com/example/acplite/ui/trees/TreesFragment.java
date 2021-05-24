package com.example.acplite.ui.trees;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.acplite.R;
import com.example.acplite.adapters.RecyclerTreesAdapter;
import com.example.acplite.databinding.FragmentGalleryBinding;
import com.example.acplite.databinding.FragmentTreesBinding;
import com.example.acplite.entidades.Arbol;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.sqlite.DataTrees;
import com.example.acplite.ui.ActivityTreeRegister;
import com.example.acplite.ui.gallery.GalleryViewModel;
import com.example.acplite.utilidades.UtilidadesArbol;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        getData();

        rvTrees.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTrees.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerTreesAdapter(listaTrees);
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

    public void getData(){
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
}