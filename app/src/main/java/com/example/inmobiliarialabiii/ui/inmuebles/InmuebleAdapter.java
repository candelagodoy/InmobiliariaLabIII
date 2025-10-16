package com.example.inmobiliarialabiii.ui.inmuebles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.model.Inmueble;
import com.bumptech.glide.Glide;


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
        String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net";
        Inmueble iActual = lista.get(position);
        holder.tvDireccion.setText(iActual.getDireccion());
        holder.tvPrecio.setText(String.valueOf(iActual.getValor()));
        Glide.with(context)
                .load(URLBASE + iActual.getImagen())
                .placeholder(null)//puedo dejar una imagen por defecto
                .error("null")//puedo colocar lo que quiero que aparezca cuando no se encontro la imagen
                .into(holder.iInmueble);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class InmuebleViewHolder extends RecyclerView.ViewHolder {

        TextView tvPrecio, tvDireccion;
        ImageView iInmueble;


        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            iInmueble = itemView.findViewById(R.id.iInmueble);
        }

    }
}
