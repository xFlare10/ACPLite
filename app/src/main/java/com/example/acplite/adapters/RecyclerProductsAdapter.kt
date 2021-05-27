package com.example.acplite.adapters

import com.example.acplite.entidades.Productos
import com.example.acplite.helpers.ItemTapListener
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.acplite.R
import android.widget.TextView
import java.util.ArrayList

class RecyclerProductsAdapter(
    private val productosList: ArrayList<Productos>,
    private val mTapListener: ItemTapListener
) : RecyclerView.Adapter<RecyclerProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false),
            mTapListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtID.text = "ID: " + productosList[position].productID.toString()
        holder.txtNombre.text = "Nombre: " + productosList[position].productName
        holder.txtPrecio.text = "Precio: " + productosList[position].productPrice
        holder.imgProducts.setImageBitmap(productosList[position].productImg)
    }

    override fun getItemCount(): Int {
        return productosList.size
    }

    inner class ViewHolder(itemView: View, mTapListener: ItemTapListener) :
        RecyclerView.ViewHolder(itemView) {
        var imgProducts: ImageView
        var txtID: TextView
        var txtNombre: TextView
        var txtPrecio: TextView

        init {
            itemView.setOnClickListener { v -> mTapListener.onItemTap(v, adapterPosition) }
            imgProducts = itemView.findViewById(R.id.ImgProduct)
            txtID = itemView.findViewById(R.id.txtProductID)
            txtNombre = itemView.findViewById(R.id.txtProductName)
            txtPrecio = itemView.findViewById(R.id.txtProductPrice)
        }
    }
}