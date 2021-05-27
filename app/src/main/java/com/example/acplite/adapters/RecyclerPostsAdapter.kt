package com.example.acplite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acplite.R
import com.example.acplite.entidades.Publicacion
import com.example.acplite.helpers.ItemTapListener
import java.util.*

class RecyclerPostsAdapter(
    private val publicacionsList: ArrayList<Publicacion>,
    private val mTapListener: ItemTapListener
) : RecyclerView.Adapter<RecyclerPostsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_posts, parent, false),
            mTapListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNombre.text = publicacionsList[position].postName
    }

    override fun getItemCount(): Int {
        return publicacionsList.size
    }

    inner class ViewHolder(itemView: View, itemTapListener: ItemTapListener) :
        RecyclerView.ViewHolder(itemView) {
        private val txtNombre: TextView

        init {
            itemView.setOnClickListener { v -> itemTapListener.onItemTap(v, adapterPosition) }
            txtNombre = itemView.findViewById(R.id.txtPostName)
        }
    }
}