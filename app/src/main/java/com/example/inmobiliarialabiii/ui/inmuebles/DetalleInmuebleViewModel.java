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
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble(){
        return mInmueble;
    }

    public LiveData<String> getMMensaje(){
        return mMensaje;
    }

    public void obtenerInmueble(Bundle inmuebleB){
        Inmueble inmueble = (Inmueble) inmuebleB.getSerializable("inmueble");

        if(inmueble != null){
            this.mInmueble.setValue(inmueble);
        }
    }
    public void actualizarEstado(boolean disponible){
        mMensaje.setValue("");
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(disponible);
        inmueble.setIdInmueble(this.mInmueble.getValue().getIdInmueble());
        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> llamada = ApiClient.getApiInmobiliaria().actualizarEstadoInmueble("Bearer " + token, inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    mMensaje.postValue("Estado del inmueble Actualizado");
                }
                else{
                    mMensaje.postValue("Error al actualizar estado");
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                mMensaje.postValue("Error de servidor");
            }
        });
    }
}
