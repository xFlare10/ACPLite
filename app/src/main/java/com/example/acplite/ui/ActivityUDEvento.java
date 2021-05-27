package com.example.acplite.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.ui.fragments.DatePickerFragment;
import com.example.acplite.utilidades.UtilidadesEvento;
import com.example.acplite.utilidades.UtilidadesProducto;

import java.io.ByteArrayOutputStream;

public class ActivityUDEvento extends AppCompatActivity {

    private EditText txtEventName, txtEventDate;
    private Button btnUpd, btnDelete;

    private ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udevento);

        conn = new ConexionSQLiteHelper(getApplicationContext(), UtilidadesEvento.DB_NAME, null, 1);

        txtEventName = findViewById(R.id.etEventName1);
        txtEventDate = findViewById(R.id.etEventDate1);

        txtEventName.setText(getIntent().getStringExtra("eventName"));
        txtEventDate.setText(getIntent().getStringExtra("eventDate"));

        txtEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                txtEventDate.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void clickear(View v){
        switch (v.getId()){
            case R.id.btnUpdateEvento:
                actualizarEvento();
                break;
            case R.id.btnDeleteEvento:
                eliminarEvento();
                break;
        }
    }

    private void actualizarEvento() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] name = {txtEventName.getText().toString()};

        String campoNombre = txtEventName.getText().toString();
        String campoDate = txtEventDate.getText().toString();

        if( !campoNombre.isEmpty() && !campoDate.isEmpty() ){

            ContentValues values = new ContentValues();
            values.put(UtilidadesEvento.CAMPO_NOMBRE, txtEventName.getText().toString());
            values.put(UtilidadesEvento.CAMPO_FECHA, txtEventDate.getText().toString());

            /*db.Update RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, EL HASH O TABLA DE VALORES POR LOS QUE SE VA A ACTUALIZAR,
             * LA CONDICION, EL ARREGLO STRING POR EL CUAL SE VA A CAMBIAR EL COMODIN)
             * */
            int cantidad = db.update(UtilidadesEvento.EVENTS_TABLE_NAME, values,
                    UtilidadesEvento.CAMPO_NOMBRE + " = ?", name);
            db.close();

            if( cantidad == 1 ){
                Toast.makeText(getApplicationContext(), "El Evento se actualizo con exito", Toast.LENGTH_SHORT).show();
                limpiar();
            } else {
                Toast.makeText(getApplicationContext(), "Error: Evento no actualizado", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarEvento() {
        //ABRIMOS LA BASE DE DATOS EN MOD ESCRITURA
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] name = {txtEventName.getText().toString()};

        String campoNombre = txtEventName.getText().toString();
        String campoDate = txtEventDate.getText().toString();

        if( !campoNombre.isEmpty() && !campoDate.isEmpty() ){

            ContentValues values = new ContentValues();
            values.put(UtilidadesEvento.CAMPO_NOMBRE, txtEventName.getText().toString());
            values.put(UtilidadesEvento.CAMPO_FECHA, txtEventDate.getText().toString());

            /*db.delete RECIBE COMO PARAMETROS:
             * (EL NOMBRE DE LA TABLA, LA CONDICION WHERE, EL PARAMETRO QUE SE SUSTITUYE POR EL COMODIN
             * */
            int cantidad = db.delete(UtilidadesEvento.EVENTS_TABLE_NAME, UtilidadesEvento.CAMPO_NOMBRE + "= ?", name);
            db.close();

            if( cantidad == 1 ){
                Toast.makeText(getApplicationContext(), "El Evento se elimino con exito", Toast.LENGTH_SHORT).show();
                limpiar();
            } else {
                Toast.makeText(getApplicationContext(), "Error: Evento no eliminado", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiar(){
        txtEventName.setText("");
        txtEventDate.setText("");
    }
}