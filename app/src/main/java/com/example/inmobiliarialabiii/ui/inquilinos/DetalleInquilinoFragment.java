package com.example.inmobiliarialabiii.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.databinding.FragmentDetalleInquilinoBinding;
import com.example.inmobiliarialabiii.databinding.FragmentInquilinosBinding;
import com.example.inmobiliarialabiii.model.Inmueble;
import com.example.inmobiliarialabiii.model.Inquilino;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel mViewModel;
    private FragmentDetalleInquilinoBinding binding;
    private Inmueble inmueble;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);

        mViewModel.getMInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvNombreInquilino.setText(inquilino.getNombre());
                binding.tvApellidoInquilino.setText(inquilino.getApellido());
                binding.tvDniInquilino.setText(inquilino.getDni());
                binding.tvTelefonoInquilino.setText(inquilino.getTelefono());
                binding.tvEmailInquilino.setText(inquilino.getEmail());

            }
        });

        mViewModel.obtenerInquilino(getArguments());


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        // TODO: Use the ViewModel
    }

}