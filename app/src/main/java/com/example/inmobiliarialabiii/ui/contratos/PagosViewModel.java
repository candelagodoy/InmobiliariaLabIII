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
import com.example.inmobiliarialabiii.model.Inmueble;
import com.example.inmobiliarialabiii.model.Pago;
import com.example.inmobiliarialabiii.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> mPago = new MutableLiveData<>();
    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getMPago() {
        return mPago;
    }

    public void obtenerPagos(Bundle bundle){
        int idContrato = bundle.getInt("idContrato");

        if(idContrato == 0){
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        Call<List<Pago>> llamada = ApiClient.getApiInmobiliaria().obtenerPagos("Bearer " + token, idContrato);
        llamada.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if(response.isSuccessful()){
                    mPago.postValue(response.body());
                }
                else{
                    Toast.makeText(getApplication(), "No hay Pagos disponibles", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}