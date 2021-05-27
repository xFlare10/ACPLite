package com.example.acplite.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.entidades.Evento;
import com.example.acplite.entidades.Publicacion;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.utilidades.UtilidadesProducto;
import com.example.acplite.utilidades.UtilidadesPublicacion;

public class ActivityPostRegister extends AppCompatActivity {
    private EditText txtPostName;
    private ConexionSQLiteHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_register);

        dbh = new ConexionSQLiteHelper(getApplicationContext(), UtilidadesPublicacion.DB_NAME, null, 1);

        txtPostName = findViewById(R.id.etPostName);
    }

    public void storePost(View v){
        String name = txtPostName.getText().toString();

        if( !name.isEmpty() ){

            Cursor cursor = dbh.validatePost(name);
            if(cursor.getCount() > 0){
                Toast.makeText(getApplicationContext(), "La publicación ya existe", Toast.LENGTH_SHORT).show();
            } else {
                dbh.storePost(new Publicacion(name));

                Toast.makeText(getApplicationContext(), "Publicación guardada con éxito", Toast.LENGTH_SHORT).show();

                //LIMPIAMOS CAMPOS
                txtPostName.setText("");
            }
        } else {
            Toast.makeText(getApplicationContext(), "Llene los campos antes de guardar", Toast.LENGTH_SHORT).show();
        }
    }
}