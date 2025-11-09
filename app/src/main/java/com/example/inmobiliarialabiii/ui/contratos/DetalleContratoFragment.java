package com.example.inmobiliarialabiii.ui.contratos;

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
import com.example.inmobiliarialabiii.databinding.FragmentContratosBinding;
import com.example.inmobiliarialabiii.databinding.FragmentDetalleContratoBinding;
import com.example.inmobiliarialabiii.databinding.FragmentDetalleInquilinoBinding;
import com.example.inmobiliarialabiii.model.Contrato;
import com.example.inmobiliarialabiii.ui.inquilinos.DetalleInquilinoViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel mViewModel;
    private FragmentDetalleContratoBinding binding;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);

        mViewModel.getMContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                binding.tvCodContrato.setText(contrato.getIdContrato()+"");
                DateTimeFormatter inFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter outFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    binding.tvFechaInicio.setText(LocalDate.parse(contrato.getFechaInicio(), inFmt).format(outFmt));
                    binding.tvFechaFin.setText(LocalDate.parse(contrato.getFechaFinalizacion(), inFmt).format(outFmt));
                } catch (Exception e) {
                    binding.tvFechaInicio.setText(contrato.getFechaInicio());
                    binding.tvFechaFin.setText(contrato.getFechaFinalizacion());
                }

                binding.tvMontoAlquiler.setText((int) contrato.getMontoAlquiler()+"");
                binding.tvInquilino.setText(contrato.getInquilino().getNombre());
                binding.tvInmueble.setText(contrato.getInmueble().getDireccion());
            }
        });

        mViewModel.obtenerContrato(getArguments());

        binding.btPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idContrato = Integer.parseInt(binding.tvCodContrato.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putInt("idContrato", idContrato);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.pagosFragment, bundle);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}