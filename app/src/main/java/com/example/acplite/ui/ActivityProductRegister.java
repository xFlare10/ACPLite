package com.example.acplite.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.entidades.Arbol;
import com.example.acplite.entidades.Productos;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.utilidades.UtilidadesArbol;
import com.example.acplite.utilidades.UtilidadesProducto;

import java.io.IOException;

public class ActivityProductRegister extends AppCompatActivity {
    private EditText productID, productName, productPrice;
    private ImageView imageViewObj;

    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imgToStore;

    private ConexionSQLiteHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_register);
        dbh = new ConexionSQLiteHelper(getApplicationContext(), UtilidadesProducto.DB_NAME, null, 1);

        productID = findViewById(R.id.etProductID);
        productName = findViewById(R.id.etProductName);
        productPrice = findViewById(R.id.etProductPrice);
        imageViewObj = findViewById(R.id.productImg);

        imageViewObj.setOnClickListener(new View.OnClickListener() {
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

            imageViewObj.setImageBitmap(imgToStore);
        }
    }

    public void storeProductImage(View v){
        String id = productID.getText().toString();
        String Name = productName.getText().toString();
        String Price = productPrice.getText().toString();

        if( !Name.isEmpty() && !id.isEmpty() && !Price.isEmpty() && imageViewObj.getDrawable() != null && imgToStore != null){

            Cursor cursor = dbh.validateProduct(id);
            if(cursor.getCount() > 0){
                Toast.makeText(getApplicationContext(), "El Producto ya existe", Toast.LENGTH_SHORT).show();
            } else {
                int campoID = Integer.parseInt(id);
                float campoPrice = Float.parseFloat(Price);
                dbh.storeProducto(new Productos(campoID, Name, campoPrice, imgToStore));

                Toast.makeText(getApplicationContext(), "Producto guardado con éxito", Toast.LENGTH_SHORT).show();

                //LIMPIAMOS CAMPOS
                productID.setText("");
                productName.setText("");
                productPrice.setText("");
                imageViewObj.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_camera_alt_24));
            }

        } else {
            Toast.makeText(getApplicationContext(), "Llene los campos antes de guardar", Toast.LENGTH_SHORT).show();
        }
    }
}