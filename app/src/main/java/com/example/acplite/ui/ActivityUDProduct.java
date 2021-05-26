package com.example.acplite.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.adapters.RecyclerProductsAdapter;
import com.example.acplite.adapters.RecyclerTreesAdapter;
import com.example.acplite.entidades.Arbol;
import com.example.acplite.entidades.Productos;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.utilidades.UtilidadesArbol;
import com.example.acplite.utilidades.UtilidadesProducto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityUDProduct extends AppCompatActivity {

    private EditText txtID, txtName, txtPrice;
    private ImageView productImg;
    private Button btnUpd, btnDelete;
    private TextView txtvItemProductName;

    private ConexionSQLiteHelper conn;
    private RecyclerProductsAdapter adapter;

    ArrayList<Productos> listaProductos;
    private ByteArrayOutputStream outputStream;
    private byte[] imageBytes;

    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imgToStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udproduct);

        conn = new ConexionSQLiteHelper(getApplicationContext(), UtilidadesProducto.DB_NAME, null, 1);

        txtID = findViewById(R.id.etProductID1);
        txtName = findViewById(R.id.etProductName1);
        txtPrice = findViewById(R.id.etProductPrice1);
        productImg = findViewById(R.id.productImg1);
        btnUpd = findViewById(R.id.btnUpdateProduct);
        btnDelete = findViewById(R.id.btnDeleteProduct);

        //TEXTVIEW FROM THE RECYCLER ITEM
        txtvItemProductName = findViewById(R.id.txtTreeName);

        txtID.setText(String.valueOf(getIntent().getIntExtra("idProducto", 0)));
        txtName.setText(getIntent().getStringExtra("nombreProducto"));
        txtPrice.setText(String.valueOf(getIntent().getFloatExtra("precioProducto", 5f)));
//        treeImg.setImageBitmap(getIntent().getParcelableExtra("imagenArbol"));

        //ESTE IF ES PARA RECIBIR EL PUT EXTRA DE IMAGENES GRANDES
        if(getIntent().hasExtra("imagenProducto")) {
            Bitmap b = BitmapFactory.decodeByteArray( getIntent().getByteArrayExtra("imagenProducto"),
                    0,getIntent().getByteArrayExtra("imagenProducto").length);
            productImg.setImageBitmap(b);
        }

        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&
                data.getData() != null){

            imageFilePath = data.getData();
            try {
                imgToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            productImg.setImageBitmap(imgToStore);
        }
    }

    public void clickear(View v){
        switch (v.getId()){
            case R.id.btnUpdateProduct:
                actualizarProducto();
                break;
            case R.id.btnDeleteProduct:
                eliminarProducto();
                break;
        }
    }

    private void actualizarProducto() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] id = {txtID.getText().toString()};

        String campoID = txtID.getText().toString();
        String campoNombre = txtName.getText().toString();
        String campoPrecio = txtPrice.getText().toString();
        productImg.setDrawingCacheEnabled(true);
        Bitmap campoImg = productImg.getDrawingCache();

        outputStream = new ByteArrayOutputStream();
        campoImg.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        imageBytes = outputStream.toByteArray();

        if( !campoID.isEmpty() && !campoNombre.isEmpty() && !campoPrecio.isEmpty() && campoImg != null){

            ContentValues values = new ContentValues();
            values.put(UtilidadesProducto.CAMPO_ID, txtID.getText().toString());
            values.put(UtilidadesProducto.CAMPO_NOMBRE, txtName.getText().toString());
            values.put(UtilidadesProducto.CAMPO_PRECIO, txtPrice.getText().toString());
            values.put(UtilidadesProducto.CAMPO_IMAGEN, imageBytes);

            /*db.Update RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, EL HASH O TABLA DE VALORES POR LOS QUE SE VA A ACTUALIZAR,
             * LA CONDICION, EL ARREGLO STRING POR EL CUAL SE VA A CAMBIAR EL COMODIN)
             * */
            int cantidad = db.update(UtilidadesProducto.PRODUCTS_TABLE_NAME, values, UtilidadesProducto.CAMPO_ID + "= ?", id);
            db.close();

            if( cantidad == 1 ){
                Toast.makeText(getApplicationContext(), "El producto se actualizo con exito", Toast.LENGTH_SHORT).show();
                limpiar();
            } else {
                Toast.makeText(getApplicationContext(), "Error: producto no actualizado", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarProducto() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] id = {txtID.getText().toString()};

        String campoID = txtID.getText().toString();
        String campoNombre = txtName.getText().toString();
        String campoPrecio = txtPrice.getText().toString();
        productImg.setDrawingCacheEnabled(true);
        Bitmap campoImg = productImg.getDrawingCache();

        outputStream = new ByteArrayOutputStream();
        campoImg.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        imageBytes = outputStream.toByteArray();

        if( !campoID.isEmpty() && !campoNombre.isEmpty() && !campoPrecio.isEmpty() && campoImg != null ){

            ContentValues values = new ContentValues();
            values.put(UtilidadesProducto.CAMPO_ID, txtID.getText().toString());
            values.put(UtilidadesProducto.CAMPO_NOMBRE, txtName.getText().toString());
            values.put(UtilidadesProducto.CAMPO_PRECIO, txtPrice.getText().toString());
            values.put(UtilidadesProducto.CAMPO_IMAGEN, imageBytes);

            /*db.delete RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, LA CONDICION WHERE, EL PARAMETRO QUE SE SUSTITUYE POR EL COMODIN
             * */
            int cantidad = db.delete(UtilidadesProducto.PRODUCTS_TABLE_NAME, UtilidadesProducto.CAMPO_ID + "= ?", id);
            db.close();

            if( cantidad == 1 ){
                Toast.makeText(getApplicationContext(), "El Producto se elimino con exito", Toast.LENGTH_SHORT).show();
                limpiar();
            } else {
                Toast.makeText(getApplicationContext(), "Error: árbol no eliminado", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }

    }

    private void limpiar(){
        txtID.setText("");
        txtName.setText("");
        txtPrice.setText("");
        productImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_camera_alt_24));
    }
}