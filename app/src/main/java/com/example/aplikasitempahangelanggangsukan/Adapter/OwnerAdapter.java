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

import com.example.aplikasitempahangelanggangsukan.Courts;
import com.example.aplikasitempahangelanggangsukan.R;
import com.example.aplikasitempahangelanggangsukan.TimeSlots;
import com.example.aplikasitempahangelanggangsukan.homepage;

import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.MyViewHolder> {

    Context context;
    ArrayList<TimeSlots> timeSlotsArrayList;

    public OwnerAdapter(Context context, ArrayList<TimeSlots> _timeSlotsArrayList) {
        this.context = context;
        this.timeSlotsArrayList = _timeSlotsArrayList;
    }

    @NonNull
    @Override
    public OwnerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemrv,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerAdapter.MyViewHolder holder, int position) {
        holder.tv_rv_court_add.setText(timeSlotsArrayList.get(position).getSportDate()+"");
        holder.tv_rv_courtname.setText(timeSlotsArrayList.get(position).getTimeSpan());

    }

    @Override
    public int getItemCount() {
        return timeSlotsArrayList.size();
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
