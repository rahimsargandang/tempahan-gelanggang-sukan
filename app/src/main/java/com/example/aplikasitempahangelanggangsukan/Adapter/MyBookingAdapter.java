package com.example.aplikasitempahangelanggangsukan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitempahangelanggangsukan.R;
import com.example.aplikasitempahangelanggangsukan.TimeSlots;

import java.util.ArrayList;


public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> {

    Context context;
    ArrayList<TimeSlots> timeSlotsArrayList;



    public MyBookingAdapter(Context context, ArrayList<TimeSlots> _timeSlotsArrayList) {
        this.context = context;
        this.timeSlotsArrayList = _timeSlotsArrayList;
    }

    @NonNull
    @Override
    public MyBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyBookingAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mybooking_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingAdapter.MyViewHolder holder, int position) {
        holder.tv_rv_courtname.setText(timeSlotsArrayList.get(position).getCourtname());
        holder.tv_rv_court_add.setText(timeSlotsArrayList.get(position).getCoutaddress());
        holder.txt_time_slot.setText(timeSlotsArrayList.get(position).getTimeSpan());
        holder.txt_date_slot.setText(timeSlotsArrayList.get(position).getSportDate()+"");
    }

    @Override
    public int getItemCount() {
        return timeSlotsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_time_slot,txt_date_slot,tv_rv_courtname,tv_rv_court_add;

        MyViewHolder(View view) {
            super(view);

            //TextViews
            tv_rv_courtname = view.findViewById(R.id.tv_rv_courtname);
            tv_rv_court_add = view.findViewById(R.id.tv_rv_court_add);
            txt_time_slot = view.findViewById(R.id.txt_time_slot);
            txt_date_slot = view.findViewById(R.id.txt_date_slot);


        }



    }
}
