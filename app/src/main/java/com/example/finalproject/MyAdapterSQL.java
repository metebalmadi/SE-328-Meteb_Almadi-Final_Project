package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterSQL extends RecyclerView.Adapter<MyAdapterSQL.MyViewHolder> {


    Context context;
    ArrayList id, name, fname, surname, nationalID, dob, gender;

    public MyAdapterSQL(Context context, ArrayList id, ArrayList name, ArrayList fname, ArrayList surname, ArrayList nationalID, ArrayList dob, ArrayList gender) {
        this.context = context;
        this.id = id;
        this.name = name;
        this.fname = fname;
        this.surname = surname;
        this.nationalID = nationalID;
        this.dob = dob;
        this.gender = gender;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.id.setText(String.valueOf(id.get(position)));
        holder.name.setText(String.valueOf(name.get(position)));
        holder.fname.setText(String.valueOf(fname.get(position)));
        holder.surname.setText(String.valueOf(surname.get(position)));
        holder.nationalID.setText(String.valueOf(nationalID.get(position)));
        holder.dob.setText(String.valueOf(dob.get(position)));
        holder.gender.setText(String.valueOf(gender.get(position)));


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView id, name, fname, surname, nationalID, dob, gender;

        public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                id = itemView.findViewById(R.id.listID);
                name = itemView.findViewById(R.id.listName);
                fname = itemView.findViewById(R.id.listFName);
                surname = itemView.findViewById(R.id.listSurname);
                nationalID = itemView.findViewById(R.id.listNationalID);
                dob = itemView.findViewById(R.id.listDOB);
                gender = itemView.findViewById(R.id.listGender);
        }
    }
}
