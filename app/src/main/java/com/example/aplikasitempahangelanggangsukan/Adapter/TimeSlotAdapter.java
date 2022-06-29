package com.example.aplikasitempahangelanggangsukan.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitempahangelanggangsukan.BookingActivity;
import com.example.aplikasitempahangelanggangsukan.R;
import com.example.aplikasitempahangelanggangsukan.TimeSlots;
import com.example.aplikasitempahangelanggangsukan.courtlist;
import com.example.aplikasitempahangelanggangsukan.homepage;

import java.util.ArrayList;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.MyViewHolder> {


    Context context;
    ArrayList<TimeSlots> timeSlotsArrayList;
    int row_index = -1;


    public TimeSlotAdapter(Context context, ArrayList<TimeSlots> _timeSlotsArrayList) {
        this.context = context;
        this.timeSlotsArrayList = _timeSlotsArrayList;
    }

    @NonNull
    @Override
    public TimeSlotAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.card_time_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();

            }
        });

        if(row_index==position){
            holder.card_time_slot.setBackgroundColor(context.getResources().getColor(R.color.orange));
            holder.txt_time_slot.setTextColor(Color.parseColor("#FFFFFF"));
            holder.txt_time_slot_desc.setTextColor(Color.parseColor("#FFFFFF"));

            holder.txt_time_slot.setText(timeSlotsArrayList.get(position).getTimeSpan());

            if(timeSlotsArrayList.get(position).getAvailable().equals("true")) {
                holder.txt_time_slot_desc.setText("Available");
                BookingActivity.dataModel.setAvailable("false");
                BookingActivity.dataModel.setTimeSpan(timeSlotsArrayList.get(position).getTimeSpan());
            }else {
                holder.txt_time_slot_desc.setText("Unavailable");
                BookingActivity.dataModel.setAvailable("true");
                BookingActivity.dataModel.setTimeSpan(timeSlotsArrayList.get(position).getTimeSpan());
            }


        }
        else {

            holder.txt_time_slot.setText(timeSlotsArrayList.get(position).getTimeSpan());

            if(timeSlotsArrayList.get(position).getAvailable().equals("true")) {
                holder.txt_time_slot_desc.setText("Available");

                holder.card_time_slot.setEnabled(true);
                holder.card_time_slot.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.txt_time_slot.setTextColor(Color.parseColor("#000000"));
                holder.txt_time_slot_desc.setTextColor(Color.parseColor("#000000"));
            }else {
                holder.txt_time_slot_desc.setText("UnAvailable");

                holder.card_time_slot.setEnabled(false);
                holder.card_time_slot.setBackgroundColor(context.getResources().getColor(R.color.gray));
                holder.txt_time_slot.setTextColor(Color.parseColor("#000000"));
                holder.txt_time_slot_desc.setTextColor(Color.parseColor("#000000"));
            }

        }


    }

    @Override
    public int getItemCount() {
        return timeSlotsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_time_slot,txt_time_slot_desc;
        ImageView emoji_iv;
        CardView card_time_slot;

        MyViewHolder(View view) {
            super(view);

            //TextViews
            txt_time_slot = view.findViewById(R.id.txt_time_slot);
            txt_time_slot_desc = view.findViewById(R.id.txt_time_slot_desc);

            //CardView
            card_time_slot = view.findViewById(R.id.card_time_slot);

        }



    }
}
