package com.example.inmobiliarialabiii.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliarialabiii.databinding.FragmentLogoutBinding;
import com.example.inmobiliarialabiii.databinding.FragmentPerfilBinding;
import com.example.inmobiliarialabiii.model.Propietario;
import com.example.inmobiliarialabiii.ui.logout.LogoutViewModel;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater,container,false);

        mViewModel.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etNombreActualizar.setText(propietario.getNombre());
                binding.etApellidoActualizar.setText(propietario.getApellido());
                binding.etDniActualizar.setText(propietario.getDni());
                binding.etEmailActualizar.setText(propietario.getEmail());
                binding.etTelefonoActualizar.setText(propietario.getTelefono());
            }
        });

        mViewModel.getMBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btEditar.setText(s);
            }
        });

        mViewModel.getMEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombreActualizar.setEnabled(aBoolean);
                binding.etApellidoActualizar.setEnabled(aBoolean);
                binding.etDniActualizar.setEnabled(aBoolean);
                binding.etEmailActualizar.setEnabled(aBoolean);
                binding.etTelefonoActualizar.setEnabled(aBoolean);
            }
        });

        mViewModel.leerPropietario();

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = binding.etNombreActualizar.getText().toString();
                String ap = binding.etApellidoActualizar.getText().toString();
                String dni = binding.etDniActualizar.getText().toString();
                String email = binding.etEmailActualizar.getText().toString();
                String tel = binding.etTelefonoActualizar.getText().toString();

                mViewModel.guardar(binding.btEditar.getText().toString()    , nom, ap, dni, tel, email);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}