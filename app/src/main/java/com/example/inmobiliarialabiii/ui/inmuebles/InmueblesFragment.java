package com.example.inmobiliarialabiii.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.databinding.FragmentInmueblesBinding;
import com.example.inmobiliarialabiii.model.Inmueble;

import java.util.List;

public class InmueblesFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private InmueblesViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);

        mViewModel.getMImueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdapter adapter = new InmuebleAdapter(inmuebles,getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2 ); //le indico como quiero que se vea en la vista
                RecyclerView rv = binding.rvInmuebles;
                rv.setLayoutManager(glm);
                rv.setAdapter(adapter);
            }
        });

        mViewModel.leerInmueble();

        binding.fabAgregarInmueble.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_nav_inmuebles_to_cargarInmuebleFragment));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}