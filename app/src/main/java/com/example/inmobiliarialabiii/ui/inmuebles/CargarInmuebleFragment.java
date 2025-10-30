package com.example.inmobiliarialabiii.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.databinding.FragmentCargarInmuebleBinding;

public class CargarInmuebleFragment extends Fragment {

    private CargarInmuebleViewModel mViewModel;
    private FragmentCargarInmuebleBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static CargarInmuebleFragment newInstance() {
        return new CargarInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CargarInmuebleViewModel.class);
        binding = FragmentCargarInmuebleBinding.inflate(getLayoutInflater());

        abrirGaleria();

        binding.btAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });

        mViewModel.getMUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivInmueble.setImageURI(uri);//al image view le paso la uri, una vez elegida la foto, el vm se la envia al mutable y se ve en la vista
            }
        });

        binding.btAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.cargarInmueble(binding.etDireccion.getText().toString(),
                        binding.etPrecio.getText().toString(),
                        binding.etTipo.getText().toString(),
                        binding.etUso.getText().toString(),
                        binding.etAmbientes.getText().toString(),
                        binding.etSuperficie.getText().toString(),
                        binding.cbDisponible.isChecked()
                        );
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CargarInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

    private void abrirGaleria(){
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//es para abrir la galeria
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Log.d("agregar inmueble", "resultado");
                mViewModel.recibirFoto(o);
            }
        });

    }
}