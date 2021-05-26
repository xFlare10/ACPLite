package com.example.acplite.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.adapters.RecyclerTreesAdapter;
import com.example.acplite.entidades.Arbol;
import com.example.acplite.helpers.ItemTapListener;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.utilidades.UtilidadesArbol;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ActivityUDTree extends AppCompatActivity {

    private EditText txtName, txtSName, txtDesc;
    private ImageView treeImg;
    private Button btnUpd, btnDelete;
    private TextView txtvItemTreeName;

    private ConexionSQLiteHelper conn;
    private RecyclerTreesAdapter adapter;

    ArrayList<Arbol> listaTrees;
    private ByteArrayOutputStream outputStream;
    private byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udtree);

        conn = new ConexionSQLiteHelper(getApplicationContext(), UtilidadesArbol.DB_NAME, null, 1);

        txtName = findViewById(R.id.etTreeName1);
        txtSName = findViewById(R.id.etTreeScientificName1);
        txtDesc = findViewById(R.id.etTreeDescription1);
        treeImg = findViewById(R.id.treeImg1);
        btnUpd = findViewById(R.id.btnUpdateTree);
        btnDelete = findViewById(R.id.btnDeleteTree);

        //TEXTVIEW FROM THE RECYCLER ITEM
        txtvItemTreeName = findViewById(R.id.txtTreeName);

        txtName.setText(getIntent().getStringExtra("nombreArbol"));
        txtSName.setText(getIntent().getStringExtra("nombreCientificoArbol"));
        txtDesc.setText(getIntent().getStringExtra("descripcionArbol"));
//        treeImg.setImageBitmap(getIntent().getParcelableExtra("imagenArbol"));

        //ESTE IF ES PARA RECIBIR EL PUT EXTRA DE IMAGENES GRANDES
        if(getIntent().hasExtra("imagenArbol")) {
            Bitmap b = BitmapFactory.decodeByteArray( getIntent().getByteArrayExtra("imagenArbol"),
                    0,getIntent().getByteArrayExtra("imagenArbol").length);
            treeImg.setImageBitmap(b);
        }
    }

    public void clickear(View v){
        switch (v.getId()){
            case R.id.btnUpdateTree:
                actualizar();
                break;
            case R.id.btnDeleteTree:
                eliminar();
                break;
        }
    }

    private void actualizar() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] name = {txtName.getText().toString()};

        String campoName = txtName.getText().toString();
        String campoNombreC = txtSName.getText().toString();
        String campoDesc = txtDesc.getText().toString();
        treeImg.setDrawingCacheEnabled(true);
        Bitmap campoImg = treeImg.getDrawingCache();

        outputStream = new ByteArrayOutputStream();
        campoImg.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        imageBytes = outputStream.toByteArray();

        if( !campoName.isEmpty() && !campoNombreC.isEmpty() && !campoDesc.isEmpty() && campoImg != null){

            ContentValues values = new ContentValues();
            values.put(UtilidadesArbol.CAMPO_NOMBRE, txtName.getText().toString());
            values.put(UtilidadesArbol.CAMPO_NOMBRE_CIENTIFICO, txtSName.getText().toString());
            values.put(UtilidadesArbol.CAMPO_DESCRIPCION, txtDesc.getText().toString());
            values.put(UtilidadesArbol.CAMPO_IMAGEN, imageBytes);

            /*db.Update RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, EL HASH O TABLA DE VALORES POR LOS QUE SE VA A ACTUALIZAR,
             * LA CONDICION, EL ARREGLO STRING POR EL CUAL SE VA A CAMBIAR EL COMODIN)
             * */
            int cantidad = db.update(UtilidadesArbol.TREES_TABLE_NAME, values, UtilidadesArbol.CAMPO_NOMBRE + "= ?", name);
            db.close();

            if( cantidad == 1 ){
                Toast.makeText(getApplicationContext(), "El Árbol se actualizo con exito", Toast.LENGTH_SHORT).show();

                txtName.setText("");
                txtSName.setText("");
                txtDesc.setText("");
                treeImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_camera_alt_24));
            } else {
                Toast.makeText(getApplicationContext(), "Error: árbol no actualizado", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminar() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] name = {txtName.getText().toString()};

        String campoName = txtName.getText().toString();
        String campoNombreC = txtSName.getText().toString();
        String campoDesc = txtDesc.getText().toString();
        treeImg.setDrawingCacheEnabled(true);
        Bitmap campoImg = treeImg.getDrawingCache();

        outputStream = new ByteArrayOutputStream();
        campoImg.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        imageBytes = outputStream.toByteArray();

        if( !campoName.isEmpty() && !campoNombreC.isEmpty() && !campoDesc.isEmpty() && campoImg != null ){

            ContentValues values = new ContentValues();
            values.put(UtilidadesArbol.CAMPO_NOMBRE, txtName.getText().toString());
            values.put(UtilidadesArbol.CAMPO_NOMBRE_CIENTIFICO, txtSName.getText().toString());
            values.put(UtilidadesArbol.CAMPO_DESCRIPCION, txtDesc.getText().toString());
            values.put(UtilidadesArbol.CAMPO_IMAGEN, imageBytes);

            /*db.delete RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, LA CONDICION WHERE, EL PARAMETRO QUE SE SUSTITUYE POR EL COMODIN
             * */
            int cantidad = db.delete(UtilidadesArbol.TREES_TABLE_NAME, UtilidadesArbol.CAMPO_NOMBRE + "= ?", name);
            db.close();

            if( cantidad == 1 ){
                Toast.makeText(getApplicationContext(), "El Árbol se elimino con exito", Toast.LENGTH_SHORT).show();

                txtName.setText("");
                txtSName.setText("");
                txtDesc.setText("");
                treeImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_camera_alt_24));
            } else {
                Toast.makeText(getApplicationContext(), "Error: árbol no eliminado", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }

    }

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
}