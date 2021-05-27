package com.example.acplite.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acplite.R;
import com.example.acplite.entidades.Evento;
import com.example.acplite.entidades.Publicacion;
import com.example.acplite.helpers.ItemTapListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerPostsAdapter extends RecyclerView.Adapter<RecyclerPostsAdapter.ViewHolder> {
    private ArrayList<Publicacion> publicacionsList;
    private final ItemTapListener mTapListener;

    public RecyclerPostsAdapter(ArrayList<Publicacion> publicacionsList, ItemTapListener mTapListener) {
        this.publicacionsList = publicacionsList;
        this.mTapListener = mTapListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecyclerPostsAdapter.ViewHolder viewHolder = new RecyclerPostsAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts, parent, false),
                mTapListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
           holder.txtNombre.setText(publicacionsList.get(position).getPostName());
    }

    @Override
    public int getItemCount() {
        return publicacionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNombre;

        public ViewHolder(@NonNull @NotNull View itemView, ItemTapListener itemTapListener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemTapListener.onItemTap(v, getAdapterPosition());
                }
            });

            txtNombre = itemView.findViewById(R.id.txtPostName);
        }
    }
}
