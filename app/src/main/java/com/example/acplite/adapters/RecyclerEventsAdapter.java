package com.example.acplite.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acplite.R;
import com.example.acplite.entidades.Evento;
import com.example.acplite.entidades.Productos;
import com.example.acplite.helpers.ItemTapListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerEventsAdapter extends RecyclerView.Adapter<RecyclerEventsAdapter.ViewHolder> {

    private ArrayList<Evento> eventList;
    private final ItemTapListener mTapListener;

    public RecyclerEventsAdapter(ArrayList<Evento> eventList, ItemTapListener mTapListener) {
        this.eventList = eventList;
        this.mTapListener = mTapListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecyclerEventsAdapter.ViewHolder viewHolder = new RecyclerEventsAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events, parent, false),
                mTapListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.txtEventName.setText(eventList.get(position).getEventName());
        holder.txtEventDate.setText("Fecha: " + eventList.get(position).getEventDate());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEventName, txtEventDate;

        public ViewHolder(@NonNull @NotNull View itemView, ItemTapListener itemTapListener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTapListener.onItemTap(v, getAdapterPosition());
                }
            });

            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtEventDate = itemView.findViewById(R.id.txtEventDate);

        }
    }
}
