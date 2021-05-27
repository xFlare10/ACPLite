package com.example.acplite.adapters

import com.example.acplite.entidades.Evento
import com.example.acplite.helpers.ItemTapListener
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.acplite.R
import android.widget.TextView
import java.util.ArrayList

class RecyclerEventsAdapter(
    private val eventList: ArrayList<Evento>,
    private val mTapListener: ItemTapListener
) : RecyclerView.Adapter<RecyclerEventsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_events, parent, false),
            mTapListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtEventName.text = eventList[position].eventName
        holder.txtEventDate.text = "Fecha: " + eventList[position].eventDate
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class ViewHolder(itemView: View, itemTapListener: ItemTapListener?) :
        RecyclerView.ViewHolder(itemView) {
        var txtEventName: TextView
        var txtEventDate: TextView

        init {
            itemView.setOnClickListener { v -> mTapListener.onItemTap(v, adapterPosition) }
            txtEventName = itemView.findViewById(R.id.txtEventName)
            txtEventDate = itemView.findViewById(R.id.txtEventDate)
        }
    }
}