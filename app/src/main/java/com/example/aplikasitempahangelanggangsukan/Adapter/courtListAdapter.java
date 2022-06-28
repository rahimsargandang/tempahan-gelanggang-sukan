package com.example.aplikasitempahangelanggangsukan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitempahangelanggangsukan.R;
import com.example.aplikasitempahangelanggangsukan.courtlist;


import java.util.ArrayList;

public class courtListAdapter extends RecyclerView.Adapter<courtListAdapter.MyViewHolder> {

    Context context;
    ArrayList<courtlist> courtlistArrayList;

    public courtListAdapter(Context context, ArrayList<courtlist> courtlistArrayList) {
        this.context = context;
        this.courtlistArrayList = courtlistArrayList;
    }

    @NonNull
    @Override
    public courtListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.itemrv,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull courtListAdapter.MyViewHolder holder, int position) {

        courtlist cL = courtlistArrayList.get(position);

        holder.courtName.setText(cL.courtname);

    }

    @Override
    public int getItemCount() {
        return courtlistArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView courtName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courtName = itemView.findViewById(R.id.tv_rv_courtnameList);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Court Name: " + courtlistArrayList.get(getAdapterPosition()).getCourtname(),Toast.LENGTH_SHORT).show();
        }
    }
}
