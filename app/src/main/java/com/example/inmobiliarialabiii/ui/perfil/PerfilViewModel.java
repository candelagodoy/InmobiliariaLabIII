package com.example.inmobiliarialabiii.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliarialabiii.model.Propietario;
import com.example.inmobiliarialabiii.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mBoton = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getMPropietario (){
        return mPropietario;
    }

    public LiveData<Boolean> getMEstado(){
        return mEstado;
    }

    public LiveData<String> getMBoton(){
        return mBoton;
    }

    public void guardar(String boton, String nom, String ap, String dni, String tel, String email){

        if(boton.equalsIgnoreCase("Editar")){
            mEstado.setValue(true);
            mBoton.setValue("GUARDAR");
        }
        else{
            //validar capos, que no esten vacios y que el dni sea un numero
            Propietario p = new Propietario();
            p.setIdPropietario(getMPropietario().getValue().getIdPropietario());
            p.setNombre(nom);
            p.setApellido(ap);
            p.setDni(dni);
            p.setEmail(email);
            p.setTelefono(tel);
            p.setClave(null);

            mBoton.setValue("EDITAR");
            mEstado.setValue(false);

            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getApiInmobiliaria().actualizarPropietario("Bearer " + token, p);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplication(), "Propietario actualizado", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getApplication(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
                    Log.d("Error", t.getMessage());
                }
            });

        }
    }

    public void leerPropietario(){
        String token = ApiClient.leerToken(getApplication());
        Call<Propietario> llamada = ApiClient.getApiInmobiliaria().obtenerPropietario("Bearer " + token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    mPropietario.postValue(response.body());
                }
                else{
                    Toast.makeText(getApplication(), "No se pudo obtener el propietario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }
        });
    }


}