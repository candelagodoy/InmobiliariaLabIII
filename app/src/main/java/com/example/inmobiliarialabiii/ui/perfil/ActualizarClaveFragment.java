package com.example.inmobiliarialabiii.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.databinding.FragmentActualizarClaveBinding;
import com.example.inmobiliarialabiii.databinding.FragmentPerfilBinding;

public class ActualizarClaveFragment extends Fragment {

    private ActualizarClaveViewModel mViewModel;
    private FragmentActualizarClaveBinding binding;


    public static ActualizarClaveFragment newInstance() {
        return new ActualizarClaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ActualizarClaveViewModel.class);
        binding = FragmentActualizarClaveBinding.inflate(inflater,container,false);

        mViewModel.getMMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensajeClave.setText(s);
            }
        });

        binding.btGuardarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cAntigua = binding.etClaveAntigua.getText().toString();
                String cNueva = binding.etNuevaClave.getText().toString();
                String cNuevaRepe = binding.etRepetirClave.getText().toString();

                mViewModel.cambiarClave(cAntigua, cNueva, cNuevaRepe);
            }
        });

        mViewModel.getMLimpiarCampos().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.etClaveAntigua.setText("");
                binding.etNuevaClave.setText("");
                binding.etRepetirClave.setText("");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ActualizarClaveViewModel.class);
        // TODO: Use the ViewModel
    }

}