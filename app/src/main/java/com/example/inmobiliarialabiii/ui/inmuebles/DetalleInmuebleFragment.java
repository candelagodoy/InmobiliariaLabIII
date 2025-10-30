package com.example.inmobiliarialabiii.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmobiliarialabiii.model.Inmueble;
import com.example.inmobiliarialabiii.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mViewModel;
    private FragmentDetalleInmuebleBinding binding;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);;
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        mViewModel.getMInmueble().observe(getViewLifecycleOwner(), inmueble -> {
                binding.tvIdInmueble.setText(inmueble.getIdInmueble()+ "");
                binding.tvDireccionI.setText(inmueble.getDireccion());
                binding.tvUsoI.setText(inmueble.getUso());
                binding.tvAmbientesI.setText(inmueble.getAmbientes()+"");
                binding.tvLatitudI.setText(inmueble.getLatitud()+"");
                binding.tvLongitudI.setText(inmueble.getLongitud()+"");
                binding.tvValorI.setText(inmueble.getValor()+"");
                Glide.with(this)
                        .load(ApiClient.URLBASE + inmueble.getImagen())
                        .placeholder(R.drawable.inmuebles)//puedo dejar una imagen por defecto
                        .error("null")//puedo colocar lo que quiero que aparezca cuando no se encontro la imagen
                        .into(binding.imgInmuebleD);
                binding.checkDisponible.setChecked(inmueble.isDisponible());

        });
        mViewModel.obtenerInmueble(getArguments());

        binding.checkDisponible.setOnClickListener(v -> {
                mViewModel.actualizarEstado(binding.checkDisponible.isChecked());

        });

        return binding.getRoot();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}