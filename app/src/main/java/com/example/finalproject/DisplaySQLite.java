package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class DisplaySQLite extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> id, name, fname, surname, nationalID, dob, gender;
    DatabaseHelper myDB;
    MyAdapterSQL adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sqlite);
        myDB = new DatabaseHelper(this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        fname = new ArrayList<>();
        surname = new ArrayList<>();
        nationalID = new ArrayList<>();
        dob = new ArrayList<>();
        gender = new ArrayList<>();

        recyclerView = findViewById(R.id.recycleViewSQL);
        adapter = new MyAdapterSQL(this, id, name, fname, surname, nationalID, dob, gender);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayData();

    }

    public void displayData()
    {
        Cursor cursor = myDB.getListContents();
        if (cursor.getCount() == 0)
        {
            Toasty.info(getBaseContext(), "There is no data", Toasty.LENGTH_LONG,true).show();
        }else
        {
            while(cursor.moveToNext())
            {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                fname.add(cursor.getString(2));
                surname.add(cursor.getString(3));
                nationalID.add(cursor.getString(4));
                dob.add(cursor.getString(5));
                gender.add(cursor.getString(6));

            }
        }
    }

}