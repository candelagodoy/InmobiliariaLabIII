package com.example.inmobiliarialabiii.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.databinding.FragmentInicioBinding;
import com.example.inmobiliarialabiii.ui.perfil.PerfilViewModel;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private InicioViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(InicioViewModel.class);
        binding = FragmentInicioBinding.inflate(inflater, container, false);

        mViewModel.getMMapaActual().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.MapaActual>() {
            @Override
            public void onChanged(InicioViewModel.MapaActual mapaActual) {
                //SupportMapFragment supportMapFragment =(SupportMapFragment)getParentFragmentManager().findFragmentById(R.id.maps);
                /*SupportMapFragment supportMapFragment =
                        (SupportMapFragment) requireActivity().getSupportFragmentManager()
                                .findFragmentById(R.id.maps);*/
                SupportMapFragment supportMapFragment =
                        (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);


                supportMapFragment.getMapAsync(mapaActual);

            }
        });

        mViewModel.cargarMapa();

        return binding.getRoot();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}