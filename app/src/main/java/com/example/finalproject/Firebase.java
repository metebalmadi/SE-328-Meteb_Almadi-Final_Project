package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    static final String DATABASE_TO_USE = "Students";

    public Firebase() {
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference(DATABASE_TO_USE);
    }

    public void writeNewStudent(String stID, String stName, String stFather, String stSurname,
                                String stNationalID, String stDOB, String stGender)
    {
        Student newStudent = new Student(stID, stName, stFather, stSurname, stNationalID, stDOB, stGender);
        myRef.child(stID).setValue(newStudent);
    }

    public void updateStudentName(String stID, String update, String value)
    {
        myRef.child(stID).child(update).setValue(value);

    }

    public void deleteStudent(String stID)
    {
        myRef.child(stID).removeValue();
    }

}
