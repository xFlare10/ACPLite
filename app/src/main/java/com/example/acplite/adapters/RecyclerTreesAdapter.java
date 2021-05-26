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
import com.example.acplite.helpers.ItemTapListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerTreesAdapter extends RecyclerView.Adapter<RecyclerTreesAdapter.ViewHolder> {

    private ArrayList<Arbol> arbolesList;
    private final ItemTapListener mTapListener;

    public RecyclerTreesAdapter(ArrayList<Arbol> arbolesList, ItemTapListener mTapListener) {
        this.arbolesList = arbolesList;
        this.mTapListener = mTapListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trees, parent, false),
                mTapListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.txtNombre.setText("Nombre del árbol: " + arbolesList.get(position).getTreeName());
        holder.txtNombreCientifico.setText("Nombre Científico: " + arbolesList.get(position).getTreeScientificName());
        holder.txtDescripcion.setText("Descripción: " + arbolesList.get(position).getTreeDescription());
        holder.imgTrees.setImageBitmap(arbolesList.get(position).getTreeImg());
    }

    @Override
    public int getItemCount() {
        return arbolesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTrees;
        TextView txtNombre, txtNombreCientifico, txtDescripcion;

        public ViewHolder(@NonNull @NotNull View itemView, ItemTapListener mTapListener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTapListener.onItemTap(v, getAdapterPosition());
                }
            });

            imgTrees = itemView.findViewById(R.id.ImgTree);
            txtNombre = itemView.findViewById(R.id.txtTreeName);
            txtNombreCientifico = itemView.findViewById(R.id.txtTreeScientificName);
            txtDescripcion = itemView.findViewById(R.id.txtTreeDescription);
        }
    }
}
