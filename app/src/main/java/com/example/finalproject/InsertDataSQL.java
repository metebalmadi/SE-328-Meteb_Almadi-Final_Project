package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class InsertDataSQL extends AppCompatActivity {

    String id, name, fname, surname, nationalID, gender, date, genderChoice;
    Firebase myFDB = new Firebase();
    DatabaseHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data_sql);

        myDB = new DatabaseHelper(this);

        Button datePicker = (Button) findViewById(R.id.datePickBttn2);
        Button bttnInsert = (Button) findViewById(R.id.bttnInsert2);
        Button bttnUpdate = (Button) findViewById(R.id.bttnUpdate2);
        Button bttnView = (Button) findViewById(R.id.bttnView2);
        Button bttnDelete = (Button) findViewById(R.id.bttnDelete2);
        Button bttnFind = (Button) findViewById(R.id.bttnFind2);
        Button bttnGET = (Button) findViewById(R.id.bttnGetFirebase);


        EditText editID = (EditText) findViewById(R.id.editID2);
        EditText editName = (EditText) findViewById(R.id.editName2);
        EditText editFName = (EditText) findViewById(R.id.editFName2);
        EditText editSurname = (EditText) findViewById(R.id.editSurname2);
        EditText editNationalID = (EditText) findViewById(R.id.editNationalID2);


        ImageView imgMain = (ImageView) findViewById(R.id.imgInsertSQL);
        TextView descMain = (TextView) findViewById(R.id.descInsertSQL);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(InsertDataSQL.this);

        String city = sp.getString("city", String.valueOf(0));
        String link = sp.getString("link", String.valueOf(0));
        String weather = sp.getString("weather", String.valueOf(0));

        Glide.with(InsertDataSQL.this).load(link).into(imgMain);

        descMain.setText(city + "\n" + weather);




        Calendar c = Calendar.getInstance();
        DateFormat fmtDate = DateFormat.getDateInstance();

        RadioButton male = (RadioButton) findViewById(R.id.Male2);
        RadioButton female = (RadioButton) findViewById(R.id.Female2);


        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                datePicker.setText(fmtDate.format(c.getTime()));
            }
        };

        datePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new DatePickerDialog(InsertDataSQL.this, d,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        bttnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = editID.getText().toString();
                name = editName.getText().toString();
                fname = editFName.getText().toString();
                surname = editSurname.getText().toString();
                nationalID = editNationalID.getText().toString();




                if(male.isChecked())
                {
                    gender = "Male";
                }else
                {
                    if (female.isChecked())
                    {
                        gender = "Female";
                    }else
                    {
                        Toasty.info(getBaseContext(), "please pick a gender", Toasty.LENGTH_LONG,true).show();
                        return;
                    }
                }

                if (id.isEmpty() || name.isEmpty() || fname.isEmpty() || surname.isEmpty() || nationalID.isEmpty())
                {
                    Toasty.info(getBaseContext(), "Empty fields", Toasty.LENGTH_LONG,true).show();
                }else
                {
                    date = fmtDate.format(c.getTime());
                    if (myDB.addData(id, name, fname, surname, nationalID, date, gender))
                    {
                        Toasty.success(getBaseContext(), "Inserted in SQLite", Toasty.LENGTH_LONG,true).show();
                    }
                    else{
                        Toasty.error(getBaseContext(), "Inserted failed", Toasty.LENGTH_LONG,true).show();
                    }



                }
            }
        });

        bttnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = editID.getText().toString();

                if (id.isEmpty())
                {
                    Toasty.info(getBaseContext(), "ID field is empty", Toasty.LENGTH_LONG,true).show();
                }else
                {
                    Cursor cursor = myDB.structuredQuery(id);
                    String cID = cursor.getString(0);
                    String cName = cursor.getString(1);
                    String cFName = cursor.getString(2);
                    String cSurname = cursor.getString(3);
                    String cNationalID = cursor.getString(4);
                    String cDOB = cursor.getString(5);
                    String cGender = cursor.getString(6);


                    editID.setText(cID);
                    editName.setText(cName);
                    editFName.setText(cFName);
                    editSurname.setText(cSurname);
                    editNationalID.setText(cNationalID);
                    datePicker.setText(cDOB);
                    genderChoice = cGender;

                }
            }
        });

        bttnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = editID.getText().toString();
                if (id.isEmpty())
                {
                    Toasty.info(getBaseContext(), "ID field is empty", Toasty.LENGTH_LONG,true).show();
                }else
                {
                    if (myDB.deleteData(id))
                    {
                        Toasty.success(getBaseContext(), "Deleted from SQLite", Toasty.LENGTH_LONG,true).show();
                    }
                    else{
                        Toasty.error(getBaseContext(), "Deletion failed", Toasty.LENGTH_LONG,true).show();
                    }

                }

            }
        });

        bttnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertDataSQL.this, DisplaySQLite.class));
            }
        });

        bttnGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = editID.getText().toString();
                Query checkID = FirebaseDatabase.getInstance().getReference("Students")
                        .orderByChild("stID").equalTo(id);

                checkID.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            editName.setText(snapshot.child(id).child("stName").getValue().toString());
                            editFName.setText(snapshot.child(id).child("stFather").getValue().toString());
                            editSurname.setText(snapshot.child(id).child("stSurname").getValue().toString());
                            editNationalID.setText(snapshot.child(id).child("stNationalID").getValue().toString());
                            datePicker.setText(snapshot.child(id).child("stDOB").getValue().toString());
                            genderChoice = snapshot.child(id).child("stGender").getValue().toString();
                            Toasty.success(getBaseContext(), "Found ID", Toasty.LENGTH_LONG,true).show();

                            id = editID.getText().toString();
                            name = editName.getText().toString();
                            fname = editFName.getText().toString();
                            surname = editSurname.getText().toString();
                            nationalID = editNationalID.getText().toString();
                            date = snapshot.child(id).child("stDOB").getValue().toString();
                            gender = snapshot.child(id).child("stGender").getValue().toString();

                            if (myDB.addData(id, name, fname, surname, nationalID, date, gender))
                            {
                                Toasty.success(getBaseContext(), "Inserted in SQLite", Toasty.LENGTH_LONG,true).show();
                            }
                            else{
                                Toasty.error(getBaseContext(), "Inserted failed", Toasty.LENGTH_LONG,true).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toasty.info(getBaseContext(), "ID does not exist", Toasty.LENGTH_LONG,true).show();
                    }
                });
            }
        });


        bttnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = editID.getText().toString();
                name = editName.getText().toString();
                fname = editFName.getText().toString();
                surname = editSurname.getText().toString();
                nationalID = editNationalID.getText().toString();
                date = datePicker.getText().toString();

                if(male.isChecked())
                {
                    gender = "Male";
                }else
                {
                    if (female.isChecked())
                    {
                        gender = "Female";
                    }else
                    {
                        Toasty.info(getBaseContext(), "please pick a gender", Toasty.LENGTH_LONG,true).show();
                        return;
                    }
                }

                if (id.isEmpty() || name.isEmpty() || fname.isEmpty() || surname.isEmpty() || nationalID.isEmpty() || date.isEmpty() || gender.isEmpty())
                {
                    Toasty.info(getBaseContext(), "Please find an ID first", Toasty.LENGTH_LONG,true).show();
                }else
                {
                    if (!myDB.updateData(id, name, fname, surname, nationalID, date, gender))
                    {
                        Toasty.error(getBaseContext(), "Update failed", Toasty.LENGTH_LONG,true).show();
                    }else
                    {
                        Toasty.success(getBaseContext(), "Updated record", Toasty.LENGTH_LONG,true).show();
                    }


                }

            }
        });

    }
}