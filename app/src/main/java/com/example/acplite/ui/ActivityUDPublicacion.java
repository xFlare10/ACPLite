package com.example.acplite.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.utilidades.UtilidadesEvento;
import com.example.acplite.utilidades.UtilidadesPublicacion;

public class ActivityUDPublicacion extends AppCompatActivity {
    private EditText txtPostName;

    private ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udpublicacion);

        conn = new ConexionSQLiteHelper(getApplicationContext(), UtilidadesPublicacion.DB_NAME, null, 1);

        txtPostName = findViewById(R.id.etPostName1);

        txtPostName.setText(getIntent().getStringExtra("postName"));
    }

    public void clickear(View v){
        switch (v.getId()){
            case R.id.btnUpdateEvento:
                actualizarPost();
                break;
            case R.id.btnDeleteEvento:
                eliminarPost();
                break;
        }
    }

    private void actualizarPost() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] name = {txtPostName.getText().toString()};

        String campoNombre = txtPostName.getText().toString();

        if( !campoNombre.isEmpty() ){

            ContentValues values = new ContentValues();
            values.put(UtilidadesPublicacion.CAMPO_NOMBRE, txtPostName.getText().toString());

            /*db.Update RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, EL HASH O TABLA DE VALORES POR LOS QUE SE VA A ACTUALIZAR,
             * LA CONDICION, EL ARREGLO STRING POR EL CUAL SE VA A CAMBIAR EL COMODIN)
             * */
            int cantidad = db.update(UtilidadesPublicacion.POSTS_TABLE_NAME, values,
                    UtilidadesPublicacion.CAMPO_NOMBRE + " = ?", name);
            db.close();

            if( cantidad == 1 ){
                Toast.makeText(getApplicationContext(), "La publicación se actualizo con exito", Toast.LENGTH_SHORT).show();
                txtPostName.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Error: Publicacion no actualizada", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarPost() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] name = {txtPostName.getText().toString()};

        String campoNombre = txtPostName.getText().toString();

        if( !campoNombre.isEmpty() ){

            ContentValues values = new ContentValues();
            values.put(UtilidadesPublicacion.CAMPO_NOMBRE, txtPostName.getText().toString());

            /*db.delete RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, LA CONDICION WHERE, EL PARAMETRO QUE SE SUSTITUYE POR EL COMODIN
             * */
            int cantidad = db.delete(UtilidadesPublicacion.POSTS_TABLE_NAME, UtilidadesPublicacion.CAMPO_NOMBRE + "= ?", name);
            db.close();

            if( cantidad == 1 ){
                Toast.makeText(getApplicationContext(), "La publicación se elimino con exito", Toast.LENGTH_SHORT).show();
                txtPostName.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Error: Publicación no eliminada", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }
    }
}