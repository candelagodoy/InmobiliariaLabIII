package com.example.inmobiliarialabiii.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliarialabiii.MainActivity;
import com.example.inmobiliarialabiii.R;
import com.example.inmobiliarialabiii.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel vModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());


        vModel.getMMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensaje.setText(s);
            }
        });


        binding.btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = binding.etUsuario.getText().toString();
                String clave = binding.etClave.getText().toString();
                vModel.login(usuario, clave);
            }
        });

        vModel.activarLecturas();

       setContentView(binding.getRoot());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vModel.desactivarLecturas();
    }
}