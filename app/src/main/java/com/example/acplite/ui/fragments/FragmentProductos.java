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
import com.example.acplite.adapters.RecyclerProductsAdapter;
import com.example.acplite.adapters.RecyclerTreesAdapter;
import com.example.acplite.databinding.FragmentTreesBinding;
import com.example.acplite.entidades.Arbol;
import com.example.acplite.entidades.Productos;
import com.example.acplite.helpers.ItemTapListener;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.ui.ActivityProductRegister;
import com.example.acplite.ui.ActivityTreeRegister;
import com.example.acplite.ui.ActivityUDProduct;
import com.example.acplite.ui.ActivityUDTree;
import com.example.acplite.utilidades.UtilidadesArbol;
import com.example.acplite.utilidades.UtilidadesProducto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FragmentProductos extends Fragment {
    private FragmentTreesBinding binding;

    private ConexionSQLiteHelper conn;
    private RecyclerView rvProducts;
    private FloatingActionButton fab;
    private RecyclerProductsAdapter adapter;

    ArrayList<Productos> listaProducts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_productos, container, false);

        conn = new ConexionSQLiteHelper(getContext(), UtilidadesArbol.DB_NAME, null, 1);

        listaProducts = new ArrayList<>();
        rvProducts = vista.findViewById(R.id.rvProductos);

        getData(conn);

        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new RecyclerProductsAdapter(listaProducts, new ItemTapListener() {
            @Override
            public void onItemTap(View view, int position) {
                Toast.makeText(getContext(), "Tocaste el item " + listaProducts.get(position).getProductName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ActivityUDProduct.class);
                intent.putExtra("idProducto",listaProducts.get(position).getProductID());
                intent.putExtra("nombreProducto",listaProducts.get(position).getProductName());
                intent.putExtra("precioProducto",listaProducts.get(position).getProductPrice());

                //PARA PASAR IMAGENES GRANDES CON PUT EXTRA HAY QUE HACE LAS 4 SIGUIENTES LINEAS
                Bitmap b = listaProducts.get(position).getProductImg(); // your bitmap
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 50, bs);
                intent.putExtra("imagenProducto",bs.toByteArray());

                startActivity(intent);
            }
        });

        rvProducts.setAdapter(adapter);

        fab = vista.findViewById(R.id.fabToRegisterProduct);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityProductRegister.class));
            }
        });

        return vista;
    }

    //THIS IS THE METHOD THAT LOADS ALL THE DATA IN THE RECYCLER VIEW
    public void getData(ConexionSQLiteHelper conn){
        SQLiteDatabase db = conn.getWritableDatabase();
        byte[] imgBytes;

        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesProducto.PRODUCTS_TABLE_NAME, null);

        while (cursor.moveToNext()){
            int productID = cursor.getInt(0);
            String productName = cursor.getString(1);
            float productPrice = cursor.getFloat(2);
            imgBytes = cursor.getBlob(3);

            Bitmap obj = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

            listaProducts.add(new Productos(productID, productName, productPrice, obj));
        }
    }
}