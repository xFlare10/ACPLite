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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.entidades.Arbol;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.ui.fragments.TreesFragment;
import com.example.acplite.utilidades.UtilidadesArbol;

import java.io.IOException;

public class ActivityTreeRegister extends AppCompatActivity {
    private EditText treeName, treeScName, treeDesc;
    private ImageView imageViewObj;

    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imgToStore;

    private ConexionSQLiteHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_register);

        treeName = findViewById(R.id.etTreeName);
        treeScName = findViewById(R.id.etTreeScientificName);
        treeDesc = findViewById(R.id.etTreeDescription);
        imageViewObj = findViewById(R.id.treeImg);

        dbh = new ConexionSQLiteHelper(getApplicationContext(), UtilidadesArbol.DB_NAME, null, 1);

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

    public void storeImage(View v){
        String Name = treeName.getText().toString();
        String NameSc = treeScName.getText().toString();
        String Desc = treeDesc.getText().toString();

        if( !Name.isEmpty() && !NameSc.isEmpty() && !Desc.isEmpty() && imageViewObj.getDrawable() != null && imgToStore != null){

            Cursor cursor = dbh.validateTree(Name);
            if(cursor.getCount() > 0){
                Toast.makeText(getApplicationContext(), "El Árbol ya existe", Toast.LENGTH_SHORT).show();
            } else {
                dbh.storeTree(new Arbol(Name, NameSc, Desc, imgToStore));

                Toast.makeText(getApplicationContext(), "Imagen guardada con éxito", Toast.LENGTH_SHORT).show();

                //LIMPIAMOS CAMPOS
                treeName.setText("");
                treeScName.setText("");
                treeDesc.setText("");
                imageViewObj.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_camera_alt_24));
            }

        } else {
            Toast.makeText(getApplicationContext(), "Llene los campos antes de guardar", Toast.LENGTH_SHORT).show();
        }
    }
}