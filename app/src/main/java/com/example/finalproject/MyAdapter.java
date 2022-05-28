package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Student> list;


    public MyAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Student st = list.get(position);
        holder.id.setText(st.getStID());
        holder.name.setText(st.getStName());
        holder.fname.setText(st.getStFather());
        holder.surname.setText(st.getStSurname());
        holder.nationalID.setText(st.getStNationalID());
        holder.dob.setText(st.getStDOB());
        holder.gender.setText(st.getStGender());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

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
