package com.example.inmobiliarialabiii.ui.inquilinos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class InmuebleAdapter2 extends RecyclerView.Adapter<InmuebleAdapter2.InmuebleInquilinosViewHolder> {

    private List<Inmueble> lista;
    private Context context;

    public InmuebleAdapter2(List<Inmueble> lista, Context context){
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public InmuebleInquilinosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteminquilinos, parent, false);
        return new InmuebleInquilinosViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleInquilinosViewHolder holder, int position) {
        Inmueble iActual = lista.get(position);
        holder.tvDireccion.setText(iActual.getDireccion());
        holder.tvPrecio.setText(String.valueOf(iActual.getValor()));
        Glide.with(context)
                .load(ApiClient.URLBASE + iActual.getImagen())
                .placeholder(R.drawable.inmuebles)
                .error("null")
                .into(holder.iInmueble);
        holder.btVerInquilino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idInmueble", iActual.getIdInmueble());
                Navigation.findNavController((Activity)v.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.detalleInquilinoFragment, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class InmuebleInquilinosViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrecio, tvDireccion;
        ImageView iInmueble;
        Button btVerInquilino;
        CardView cardView;
        public InmuebleInquilinosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrecio = itemView.findViewById(R.id.tvPrecioInquilinos);
            tvDireccion = itemView.findViewById(R.id.tvDireccionInquilinos);
            iInmueble = itemView.findViewById(R.id.iInmuebleInquilinos);
            cardView = itemView.findViewById(R.id.idCardInquilinos);
            btVerInquilino = itemView.findViewById(R.id.btVerInquilino);
        }

    }
}