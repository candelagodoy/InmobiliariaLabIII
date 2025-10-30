package com.example.inmobiliarialabiii.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliarialabiii.model.Inmueble;
import com.example.inmobiliarialabiii.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble(){
        return mInmueble;
    }

    public void obtenerInmueble(Bundle inmuebleB){
        Inmueble inmueble = (Inmueble) inmuebleB.getSerializable("inmueble");

        if(inmueble != null){
            this.mInmueble.setValue(inmueble);
        }
    }
    public void actualizarEstado(boolean disponible){
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(disponible);
        inmueble.setIdInmueble(this.mInmueble.getValue().getIdInmueble());
        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> llamada = ApiClient.getApiInmobiliaria().actualizarEstadoInmueble("Bearer " + token, inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplication(), "Estado del inmueble Actualizado", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplication(), "Error al actualizar estado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
