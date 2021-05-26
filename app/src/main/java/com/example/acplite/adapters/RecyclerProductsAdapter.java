package com.example.acplite.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acplite.R;
import com.example.acplite.entidades.Arbol;
import com.example.acplite.entidades.Productos;
import com.example.acplite.helpers.ItemTapListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerProductsAdapter extends RecyclerView.Adapter<RecyclerProductsAdapter.ViewHolder>{

    private ArrayList<Productos> productosList;
    private final ItemTapListener mTapListener;

    public RecyclerProductsAdapter(ArrayList<Productos> productosList, ItemTapListener mTapListener) {
        this.productosList = productosList;
        this.mTapListener = mTapListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecyclerProductsAdapter.ViewHolder viewHolder = new RecyclerProductsAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false),
                mTapListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.txtID.setText("ID: " + String.valueOf(productosList.get(position).getProductID()));
        holder.txtNombre.setText("Nombre: " + productosList.get(position).getProductName());
        holder.txtPrecio.setText("Precio: " + productosList.get(position).getProductPrice());
        holder.imgProducts.setImageBitmap(productosList.get(position).getProductImg());
    }

    @Override
    public int getItemCount() {
        return productosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProducts;
        TextView txtID, txtNombre, txtPrecio;

        public ViewHolder(@NonNull @NotNull View itemView, ItemTapListener mTapListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTapListener.onItemTap(v, getAdapterPosition());
                }
            });
            imgProducts = itemView.findViewById(R.id.ImgProduct);
            txtID = itemView.findViewById(R.id.txtProductID);
            txtNombre = itemView.findViewById(R.id.txtProductName);
            txtPrecio = itemView.findViewById(R.id.txtProductPrice);
        }
    }
}
