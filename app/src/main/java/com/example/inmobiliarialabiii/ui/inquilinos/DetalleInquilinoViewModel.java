package com.example.inmobiliarialabiii.ui.inquilinos;

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
import com.example.inmobiliarialabiii.model.Inquilino;
import com.example.inmobiliarialabiii.model.Propietario;
import com.example.inmobiliarialabiii.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> mInquilino = new MutableLiveData<>();
    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getMInquilino(){
        return mInquilino;
    }


    public void obtenerInquilino(Bundle bundle){
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
                    mInquilino.postValue(response.body().getInquilino());
                }
                else{
                    Toast.makeText(getApplication(), "No se pudo obtener el inquilino", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();

            }
        });
    }


}