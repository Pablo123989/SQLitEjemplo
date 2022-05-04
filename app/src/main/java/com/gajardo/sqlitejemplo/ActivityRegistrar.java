package com.gajardo.sqlitejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityRegistrar extends AppCompatActivity {

    private boolean existe(String id) {
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(ActivityRegistrar.this, utilerias.NOMBRE_BD, null, 1);
        SQLiteDatabase bd = conexion.getReadableDatabase();
        String[] parametros = {id};
        String[] campos = {utilerias.CAMPO_NOMBRE};
        Cursor cursor = bd.query(utilerias.TABLA_PERSONA,
                campos,
                utilerias.CAMPO_ID + "=?",
                parametros,
                null,
                null,
                null);
        return cursor.moveToFirst();
    }//existe
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        final EditText etIdentificador = findViewById(R.id.etIdentificador),
                etNombre = findViewById(R.id.etNombre),
                etTelefono = findViewById(R.id.etTelefono);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String identificador = etIdentificador.getText().toString(),
                        nombre = etNombre.getText().toString(),
                        telefono = etTelefono.getText().toString();
                if (existe(identificador)) {
                    Toast.makeText(ActivityRegistrar.this, "Identificador existente", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (identificador.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) return;
                ContentValues contentValues = new ContentValues();
                contentValues.put(utilerias.CAMPO_ID, identificador);
                contentValues.put(utilerias.CAMPO_NOMBRE, nombre);
                contentValues.put(utilerias.CAMPO_TELEFONO, telefono);

                ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(ActivityRegistrar.this,
                        utilerias.NOMBRE_BD, null, 1);
                SQLiteDatabase bd = conexion.getWritableDatabase();
                long respuesta = bd.insert(utilerias.TABLA_PERSONA, null, contentValues);
                Toast.makeText(ActivityRegistrar.this, "Registro #" + respuesta, Toast.LENGTH_SHORT).show();
                bd.close();
            }
        });
    }//onCreate()
}//ActivityRegistrar