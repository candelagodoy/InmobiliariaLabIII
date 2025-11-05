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
import com.example.inmobiliarialabiii.databinding.FragmentInmueblesBinding;
import com.example.inmobiliarialabiii.databinding.FragmentPagosBinding;
import com.example.inmobiliarialabiii.model.Pago;
import com.example.inmobiliarialabiii.ui.inmuebles.InmuebleAdapter;
import com.example.inmobiliarialabiii.ui.inmuebles.InmueblesViewModel;
import com.google.android.gms.maps.MapView;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel mViewModel;
    private FragmentPagosBinding binding;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater, container, false);

        mViewModel.getMPago().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagoAdapter adapter = new PagoAdapter(pagos,getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1 );
                RecyclerView rv = binding.rvPagos;
                rv.setLayoutManager(glm);
                rv.setAdapter(adapter);
            }
        });

        mViewModel.obtenerPagos(getArguments());


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        // TODO: Use the ViewModel
    }

}