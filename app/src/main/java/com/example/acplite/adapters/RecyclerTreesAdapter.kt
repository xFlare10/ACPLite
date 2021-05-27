package com.example.acplite.adapters

import com.example.acplite.entidades.Arbol
import com.example.acplite.helpers.ItemTapListener
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.acplite.R
import android.widget.TextView
import java.util.ArrayList

class RecyclerTreesAdapter(
    private val arbolesList: ArrayList<Arbol>,
    private val mTapListener: ItemTapListener
) : RecyclerView.Adapter<RecyclerTreesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_trees, parent, false),
            mTapListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNombre.text = "Nombre del árbol: " + arbolesList[position].treeName
        holder.txtNombreCientifico.text =
            "Nombre Científico: " + arbolesList[position].treeScientificName
        holder.txtDescripcion.text = "Descripción: " + arbolesList[position].treeDescription
        holder.imgTrees.setImageBitmap(arbolesList[position].treeImg)
    }

    override fun getItemCount(): Int {
        return arbolesList.size
    }

    inner class ViewHolder(itemView: View, mTapListener: ItemTapListener) :
        RecyclerView.ViewHolder(itemView) {
        var imgTrees: ImageView
        var txtNombre: TextView
        var txtNombreCientifico: TextView
        var txtDescripcion: TextView

        init {
            itemView.setOnClickListener { v -> mTapListener.onItemTap(v, adapterPosition) }
            imgTrees = itemView.findViewById(R.id.ImgTree)
            txtNombre = itemView.findViewById(R.id.txtTreeName)
            txtNombreCientifico = itemView.findViewById(R.id.txtTreeScientificName)
            txtDescripcion = itemView.findViewById(R.id.txtTreeDescription)
        }
    }
}