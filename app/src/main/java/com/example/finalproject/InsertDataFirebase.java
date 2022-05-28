package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class InsertDataFirebase extends AppCompatActivity {

    String id, name, fname, surname, nationalID, gender, date, date2;
    Firebase myFDB = new Firebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        Button datePicker = (Button) findViewById(R.id.datePickBttn);
        Button bttnInsert = (Button) findViewById(R.id.bttnInsert);
        Button bttnUpdate = (Button) findViewById(R.id.bttnUpdate);
        Button bttnView = (Button) findViewById(R.id.bttnView);
        Button bttnDelete = (Button) findViewById(R.id.bttnDelete);
        Button bttnFind = (Button) findViewById(R.id.bttnFind);


        EditText editID = (EditText) findViewById(R.id.editID);
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editFName = (EditText) findViewById(R.id.editFName);
        EditText editSurname = (EditText) findViewById(R.id.editSurname);
        EditText editNationalID = (EditText) findViewById(R.id.editNationalID);


        ImageView imgMain = (ImageView) findViewById(R.id.imgInsertF);
        TextView descMain = (TextView) findViewById(R.id.descInsertF);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(InsertDataFirebase.this);

        String city = sp.getString("city", String.valueOf(0));
        String link = sp.getString("link", String.valueOf(0));
        String weather = sp.getString("weather", String.valueOf(0));

        Glide.with(InsertDataFirebase.this).load(link).into(imgMain);

        descMain.setText(city + "\n" + weather);


        Calendar c = Calendar.getInstance();
        DateFormat fmtDate = DateFormat.getDateInstance();

        RadioButton male = (RadioButton) findViewById(R.id.Male);
        RadioButton female = (RadioButton) findViewById(R.id.Female);


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

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(InsertDataFirebase.this, d,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        bttnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertDataFirebase.this, DisplayFirebase.class));
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

                Query checkID = FirebaseDatabase.getInstance().getReference("Students")
                        .orderByChild("stID").equalTo(id);

                checkID.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toasty.error(getBaseContext(), "ID exists", Toasty.LENGTH_LONG, true).show();


                        } else {
                            if (male.isChecked()) {
                                gender = "Male";
                            } else {
                                if (female.isChecked()) {
                                    gender = "Female";
                                } else {
                                    Toasty.info(getBaseContext(), "please pick a gender", Toasty.LENGTH_LONG, true).show();
                                    return;
                                }
                            }

                            if (id.isEmpty() || name.isEmpty() || fname.isEmpty() || surname.isEmpty() || nationalID.isEmpty()) {
                                Toasty.info(getBaseContext(), "Empty fields", Toasty.LENGTH_LONG, true).show();
                            } else {
                                date = fmtDate.format(c.getTime());
                                myFDB.writeNewStudent(id, name, fname, surname, nationalID, date, gender);
                                Toasty.success(getBaseContext(), "Inserted in Firebase", Toasty.LENGTH_LONG, true).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        bttnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = editID.getText().toString();
                if (id.isEmpty()) {
                    Toasty.info(getBaseContext(), "Empty ID field", Toasty.LENGTH_LONG, true).show();
                } else {
                    myFDB.deleteStudent(id);
                    editID.setText("");
                    Toasty.success(getBaseContext(), "Deleted", Toasty.LENGTH_LONG, true).show();

                }
            }
        });


        bttnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = editID.getText().toString();
                Query checkID = FirebaseDatabase.getInstance().getReference("Students")
                        .orderByChild("stID").equalTo(id);

                checkID.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            editName.setText(snapshot.child(id).child("stName").getValue().toString());
                            editFName.setText(snapshot.child(id).child("stFather").getValue().toString());
                            editSurname.setText(snapshot.child(id).child("stSurname").getValue().toString());
                            editNationalID.setText(snapshot.child(id).child("stNationalID").getValue().toString());
                            datePicker.setText(snapshot.child(id).child("stDOB").getValue().toString());
                            Toasty.success(getBaseContext(), "Found ID", Toasty.LENGTH_LONG, true).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toasty.info(getBaseContext(), "ID does not exist", Toasty.LENGTH_LONG, true).show();
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
                if (male.isChecked()) {
                    gender = "Male";
                } else {
                    if (female.isChecked()) {
                        gender = "Female";
                    } else {
                        Toasty.info(getBaseContext(), "please pick a gender", Toasty.LENGTH_LONG, true).show();
                        return;
                    }
                }

                if (id.isEmpty() || name.isEmpty() || fname.isEmpty() || surname.isEmpty() || nationalID.isEmpty()) {
                    Toasty.info(getBaseContext(), "Empty fields", Toasty.LENGTH_LONG, true).show();
                } else {
                    date = datePicker.getText().toString();
                    myFDB.writeNewStudent(id, name, fname, surname, nationalID, date, gender);
                    Toasty.success(getBaseContext(), "Updated", Toasty.LENGTH_LONG, true).show();
                }
            }

        });


    }
}