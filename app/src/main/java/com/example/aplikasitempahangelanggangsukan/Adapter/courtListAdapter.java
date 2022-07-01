package com.example.aplikasitempahangelanggangsukan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitempahangelanggangsukan.R;
import com.example.aplikasitempahangelanggangsukan.Courts;
import com.example.aplikasitempahangelanggangsukan.homepage;


import java.util.ArrayList;

public class courtListAdapter extends RecyclerView.Adapter<courtListAdapter.MyViewHolder> {

    Context context;
    homepage homepageObj;
    ArrayList<Courts> courtsArrayList;

    public courtListAdapter(Context context, homepage _homepage,ArrayList<Courts> courtsArrayList) {
        this.context = context;
        homepageObj = _homepage;
        this.courtsArrayList = courtsArrayList;
    }

    @NonNull
    @Override
    public courtListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.itemrv,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull courtListAdapter.MyViewHolder holder, int position) {

        //Courts cL = courtsArrayList.get(position);

        holder.tv_rv_courtname.setText(courtsArrayList.get(position).getCourtname());
        holder.tv_rv_court_add.setText(courtsArrayList.get(position).getCourtaddress());
        holder.container_court.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homepageObj.OnListItemClicked(courtsArrayList.get(position));
                Toast.makeText(view.getContext(), "Court Name: " + courtsArrayList.get(position).getCourtname(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return courtsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_rv_courtname,tv_rv_court_add;
        CardView container_court;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rv_courtname = itemView.findViewById(R.id.tv_rv_courtname);
            tv_rv_court_add = itemView.findViewById(R.id.tv_rv_court_add);
            container_court = itemView.findViewById(R.id.container_court);

        }
    }
}
