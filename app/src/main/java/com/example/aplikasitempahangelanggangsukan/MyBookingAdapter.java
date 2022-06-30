package com.example.aplikasitempahangelanggangsukan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitempahangelanggangsukan.Adapter.TimeSlotAdapter;

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
        holder.txt_time_slot.setText(timeSlotsArrayList.get(position).getTimeSpan());
        holder.txt_date_slot.setText(timeSlotsArrayList.get(position).getSportDate()+"");
    }

    @Override
    public int getItemCount() {
        return timeSlotsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_time_slot,txt_date_slot;

        MyViewHolder(View view) {
            super(view);

            //TextViews
            txt_time_slot = view.findViewById(R.id.txt_time_slot);
            txt_date_slot = view.findViewById(R.id.txt_date_slot);


        }



    }
}
