package com.example.acplite.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acplite.R;
import com.example.acplite.entidades.Evento;
import com.example.acplite.entidades.Productos;
import com.example.acplite.sqlite.ConexionSQLiteHelper;
import com.example.acplite.ui.fragments.DatePickerFragment;
import com.example.acplite.utilidades.UtilidadesProducto;

public class ActivityEventRegister extends AppCompatActivity {

    private EditText txtEventName, txtEventDate;
    private ConexionSQLiteHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        dbh = new ConexionSQLiteHelper(getApplicationContext(), UtilidadesProducto.DB_NAME, null, 1);

        txtEventName = findViewById(R.id.etEventName);
        txtEventDate = findViewById(R.id.etEventDate);

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

    public void storeEvent(View v){
        String name = txtEventName.getText().toString();
        String date = txtEventDate.getText().toString();

        if( !name.isEmpty() && !date.isEmpty() ){

            Cursor cursor = dbh.validateEvent(name);
            if(cursor.getCount() > 0){
                Toast.makeText(getApplicationContext(), "El Evento ya existe", Toast.LENGTH_SHORT).show();
            } else {
                dbh.storeEvent(new Evento(name, date));

                Toast.makeText(getApplicationContext(), "Evento guardado con éxito", Toast.LENGTH_SHORT).show();

                //LIMPIAMOS CAMPOS
                txtEventName.setText("");
                txtEventDate.setText("");
            }
        } else {
            Toast.makeText(getApplicationContext(), "Llene los campos antes de guardar", Toast.LENGTH_SHORT).show();
        }
    }
}