package com.example.inmobiliarialabiii.ui.inmuebles;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.model.Inmueble;
import com.bumptech.glide.Glide;
import com.example.inmobiliarialabiii.request.ApiClient;


import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {

    private List<Inmueble> lista;
    private Context context;



    public InmuebleAdapter(List<Inmueble> lista, Context context) {
        this.lista = lista;
        this.context = context;

    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new InmuebleViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {
        Inmueble iActual = lista.get(position);
        holder.tvDireccion.setText(iActual.getDireccion());
        holder.tvPrecio.setText(String.valueOf(iActual.getValor()));
        Glide.with(context)
                .load(ApiClient.URLBASE + iActual.getImagen())
                .placeholder(R.drawable.inmuebles)//puedo dejar una imagen por defecto
                .error("null")//puedo colocar lo que quiero que aparezca cuando no se encontro la imagen
                .into(holder.iInmueble);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", iActual);
                Navigation.findNavController((Activity)v.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.detalleInmuebleFragment, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class InmuebleViewHolder extends RecyclerView.ViewHolder {

        TextView tvPrecio, tvDireccion;
        ImageView iInmueble;

        CardView cardView;

        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            iInmueble = itemView.findViewById(R.id.iInmueble);
            cardView = itemView.findViewById(R.id.idCardItem);
        }

    }
}
