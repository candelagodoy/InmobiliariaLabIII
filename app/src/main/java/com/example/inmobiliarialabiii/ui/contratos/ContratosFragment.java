package com.example.inmobiliarialabiii.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.databinding.FragmentContratosBinding;
import com.example.inmobiliarialabiii.databinding.FragmentInquilinosBinding;
import com.example.inmobiliarialabiii.model.Inmueble;
import com.example.inmobiliarialabiii.ui.inquilinos.InmuebleAdapter2;
import com.example.inmobiliarialabiii.ui.inquilinos.InquilinosViewModel;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel mViewModel;
    private FragmentContratosBinding binding;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);

        mViewModel.getMImuebleInquilinos().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdapterC adapter = new InmuebleAdapterC(inmuebles,getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2 );
                RecyclerView rv = binding.rvContratos;
                rv.setLayoutManager(glm);
                rv.setAdapter(adapter);
            }
        });

        mViewModel.leerInmuebleConContrato();


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        // TODO: Use the ViewModel
    }

}