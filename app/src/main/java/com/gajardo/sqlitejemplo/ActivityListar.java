package com.gajardo.sqlitejemplo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class ActivityListar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPersonas);

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(ActivityListar.this, utilerias.NOMBRE_BD, null, 1);
        SQLiteDatabase bd = conexion.getReadableDatabase();
        String[] campos = {utilerias.CAMPO_NOMBRE, utilerias.CAMPO_TELEFONO, utilerias.CAMPO_ID};
        Cursor cursor = bd.query(utilerias.TABLA_PERSONA,
                campos,
                null,
                null,
                null,
                null,
                null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            bd.close();
            return;
        }

        ArrayList<Persona> personas = new ArrayList<>();


        do {
            String nombre = cursor.getString(cursor.getColumnIndex(utilerias.CAMPO_NOMBRE));
            String telefono = cursor.getString(cursor.getColumnIndex(utilerias.CAMPO_TELEFONO));
            int id = cursor.getInt(cursor.getColumnIndex(utilerias.CAMPO_ID));
            Persona persona = new Persona(nombre, telefono, id);
            personas.add(persona);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de mascotas :)
        cursor.close();


        AdaptadorPersonas adaptadorPersonas = new AdaptadorPersonas(personas);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorPersonas);
    }//onCreate()
}//ActivityListar