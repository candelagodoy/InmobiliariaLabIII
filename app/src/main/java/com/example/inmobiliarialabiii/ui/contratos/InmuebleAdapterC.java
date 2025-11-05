package com.example.inmobiliarialabiii.ui.contratos;

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

import com.bumptech.glide.Glide;
import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.model.Inmueble;
import com.example.inmobiliarialabiii.request.ApiClient;
import com.example.inmobiliarialabiii.ui.inquilinos.InmuebleAdapter2;

import java.util.List;

public class InmuebleAdapterC extends RecyclerView.Adapter<InmuebleAdapterC.InmuebleContratosViewHolder> {
    private List<Inmueble> lista;
    private Context context;

    public InmuebleAdapterC(List<Inmueble> lista, Context context){
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public InmuebleContratosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcontratos, parent, false);
        return new InmuebleContratosViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleContratosViewHolder holder, int position) {
        Inmueble iActual = lista.get(position);
        holder.tvDireccion.setText(iActual.getDireccion());
        holder.tvPrecio.setText(String.valueOf(iActual.getValor()));
        Glide.with(context)
                .load(ApiClient.URLBASE + iActual.getImagen())
                .placeholder(R.drawable.inmuebles)
                .error("null")
                .into(holder.iInmueble);
        holder.btVerContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idInmueble", iActual.getIdInmueble());
                Navigation.findNavController((Activity)v.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.detalleContratoFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class InmuebleContratosViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrecio, tvDireccion;
        ImageView iInmueble;
        Button btVerContrato;
        CardView cardView;
        public InmuebleContratosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrecio = itemView.findViewById(R.id.tvPrecioContratos);
            tvDireccion = itemView.findViewById(R.id.tvDireccionContratos);
            iInmueble = itemView.findViewById(R.id.iInmuebleContratos);
            cardView = itemView.findViewById(R.id.idCardContratos);
            btVerContrato = itemView.findViewById(R.id.btVerContrato);
        }

    }
}
