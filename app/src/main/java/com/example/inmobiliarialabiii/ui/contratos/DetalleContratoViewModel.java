package com.example.inmobiliarialabiii.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliarialabiii.model.Contrato;
import com.example.inmobiliarialabiii.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> mContrato = new MutableLiveData<>();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getMContrato(){
        return mContrato;
    }

    public void obtenerContrato(Bundle bundle){
        int idInmueble = bundle.getInt("idInmueble");
        if (idInmueble == 0){
            return;
        }
        String token = ApiClient.leerToken(getApplication());
        Call<Contrato> llamada = ApiClient.getApiInmobiliaria().contratoPorInmueble("Bearer " + token, idInmueble);
        llamada.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){
                    mContrato.postValue(response.body());
                }
                else {
                    Toast.makeText(getApplication(), "No se pudo obtener el contrato", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();

            }
        });
    }

}