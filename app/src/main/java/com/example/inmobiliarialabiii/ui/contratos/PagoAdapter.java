package com.example.inmobiliarialabiii.ui.contratos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.model.Inmueble;
import com.example.inmobiliarialabiii.model.Pago;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder> {

    private List<Pago> lista;
    private Context context;

    public PagoAdapter(List<Pago> lista, Context context){
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempagos, parent, false);
        return new PagoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago pagoActual = lista.get(position);
        holder.tvCodigoP.setText(String.valueOf(pagoActual.getIdPago()));
        holder.tvImporte.setText(String.valueOf(pagoActual.getMonto()));
        String fechaOriginal = pagoActual.getFechaPago();
        try {
            LocalDate fecha = LocalDate.parse(fechaOriginal);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = fecha.format(formatter);
            holder.tvFecha.setText(fechaFormateada);
        } catch (Exception e) {
            holder.tvFecha.setText(fechaOriginal); // si hay error, muestra la original
        }

        holder.tvCodigoC.setText(String.valueOf(pagoActual.getIdContrato()));

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class PagoViewHolder extends RecyclerView.ViewHolder {

        TextView tvCodigoP, tvImporte, tvFecha, tvCodigoC;


        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodigoP = itemView.findViewById(R.id.tvCodPagoSet);
            tvImporte = itemView.findViewById(R.id.tvImporteSet);
            tvFecha = itemView.findViewById(R.id.tvFechaPagoSet);
            tvCodigoC = itemView.findViewById(R.id.tvCodContSet);
        }
    }
}
